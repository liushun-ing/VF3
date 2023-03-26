package algorithm;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import utils.ArrayUtils;
import utils.LabelUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * VF3匹配算法逻辑类
 */
public class GraphMatch {
  private static ArrayList<Integer> NG1; // 模式图的节点处理顺序
  private static HashMap<Integer, Integer> Parent; // 节点的父节点
  private static ArrayList<Solution> Solutions; // 结果,可能有多个

  /**
   * 初始化
   */
  public static void initVF3() {
    NG1 = new ArrayList<>();
    Parent = new HashMap<>();
    Solutions = new ArrayList<>();
  }

  /**
   * VF3算法
   *
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 节点映射结果数组, 失败返回null
   */
  public static ArrayList<Solution> VF3(Graph patternGraph, Graph targetGraph) {
    initVF3();
    computeProbabilities(patternGraph, targetGraph);
    generateNodeSequence(patternGraph);
    if (NG1.size() != patternGraph.getVertices().size()) {
      return Solutions;
    }
    preprocessPatternGraph(patternGraph);
    if (Parent.size() != patternGraph.getVertices().size() - 1) {
      return Solutions;
    }
    ArrayList<MatchCouple> s0 = new ArrayList<>();
    int result = match(s0, patternGraph, targetGraph);
    return Solutions;
  }

  /**
   * 匹配算法
   *
   * @param sc           上一个匹配的状态
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 是否匹配,1匹配，-1未匹配，继续找其他可能，0超时，直接返回结束
   */
  public static int match(ArrayList<MatchCouple> sc, Graph patternGraph, Graph targetGraph) {
    if (isGoal(sc)) {
      Solutions.add(new Solution(new ArrayList<>(sc)));
      return 1;
    }
    if (isDead(sc, targetGraph)) {
      return -1;
    }
    MatchCouple coupleC = new MatchCouple(null, null);
    MatchCouple coupleN = getNextCandidate(sc, coupleC, patternGraph, targetGraph);
    int result = -1;
    while (coupleN.getU() != null && coupleN.getV() != null) {
      if (isFeasible(sc, coupleN, patternGraph, targetGraph)) {
        ArrayList<MatchCouple> sn = new ArrayList<>(sc);
        // 替换新的节点对
        sn.add(new MatchCouple(coupleN.getU(), coupleN.getV()));
        int match = match(sn, patternGraph, targetGraph);
        if (match == 1) {
          result = 1;
        } else if (match == 0) {
          return 0;
        }
      }
      coupleN = getNextCandidate(sc, coupleN, patternGraph, targetGraph);
    }
    return result;
  }

  /**
   * 判断当前状态是否是匹配成功状态
   *
   * @param sc 当前状态
   * @return 是否成功
   */
  public static boolean isGoal(ArrayList<MatchCouple> sc) {
    // 匹配的节点对数量一致
    if (sc.size() != NG1.size()) {
      return false;
    }
    // 匹配的节点对节点要符合节点顺序
    for (int i = 0; i < sc.size(); i++) {
      if (sc.get(i).getU().getId() != NG1.get(i)) {
        return false;
      }
    }
    return true;
  }

  /**
   * 判断当前状态是否是死状态，也就是不一致状态
   *
   * @param sc          当前状态
   * @param targetGraph 目标图
   * @return 是否死状态
   */
  public static boolean isDead(ArrayList<MatchCouple> sc, Graph targetGraph) {
    if (sc.size() == 0) {
      return false;
    }
    // 应该是匹配出的目标图的子图与其他节点没有联系了，是个无法扩展的图了
    for (Edge e : targetGraph.getEdges()) {
      // 只有一条边与外界有联系就行
      if ((ArrayUtils.containsTargetGraphVertex(sc, e.getStartV().getId())
          && !ArrayUtils.containsTargetGraphVertex(sc, e.getEndV().getId()))
          || (ArrayUtils.containsTargetGraphVertex(sc, e.getEndV().getId())
          && !ArrayUtils.containsTargetGraphVertex(sc, e.getStartV().getId()))) {
        // 并且还要已经匹配的数量小于模式图节点个数
        if (sc.size() < NG1.size()) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 计算模式图的节点的 pf，为节点排序做好准备 pf = pl * pin * pout pl指的是该节点的类型在大图中的出现的概率
   * pin,pout是小图中的节点出入度小于等于大图节点出入度的概率和
   *
   * @param patternGraph 模式图
   * @param targetGraph  大图，也就是目标图
   */
  public static void computeProbabilities(Graph patternGraph, Graph targetGraph) {
    for (Vertex v : patternGraph.getVertices()) {
      double pl, pin, pout;
      pl = ArrayUtils.getPl(targetGraph.getVertexLabels(), v.getLabel()) / targetGraph.getVertices().size();
      if (pl == 0) {
        pl = 0.0;
        v.setPf(0);
        continue;
      }
      int inCount = 0, outCount = 0;
      for (Vertex targetV : targetGraph.getVertices()) {
        if (v.getInDegree() <= targetV.getInDegree()) {
          inCount++;
        }
        if (v.getOutDegree() <= targetV.getOutDegree()) {
          outCount++;
        }
      }
      pin = (inCount + 0.0) / targetGraph.getVertices().size();
      pout = (outCount + 0.0) / targetGraph.getVertices().size();
      v.setPf(pl * pin * pout);
    }
  }

  /**
   * 生成模式图节点处理顺序 第一条件：dm，第二条件：Pf，第三条件deg：节点入度和出度之和，第四条件：随机（按照节点在图中顺序即可）
   * dm节点映射度，该系数表示为模式图中剩余的节点与已经进入节点顺序队列节点之间的所有入边和出边之和 所以，每定义好一个节点的顺序之后，就要更新剩余节点的dm系数
   *
   * @param patternGraph 模式图
   */
  public static void generateNodeSequence(Graph patternGraph) {
    while (NG1.size() < patternGraph.getVertices().size()) {
      int maxDm = 0;
      for (Vertex v : patternGraph.getVertices()) {
        if (v.getDm() > maxDm) {
          maxDm = v.getDm();
        }
      }
      // dm相等的节点列表
      ArrayList<Integer> dmEqList = new ArrayList<>();
      for (Vertex v : patternGraph.getVertices()) {
        if (v.getDm() == maxDm) {
          dmEqList.add(v.getId());
        }
      }
      if (dmEqList.size() == 1) {
        // dm最大的节点唯一
        NG1.add(dmEqList.get(0));
      } else {
        // 不唯一，需要比较 pf
        double maxPf = 0.0;
        for (Integer id : dmEqList) {
          if (patternGraph.getVertexById(id).getPf() > maxPf) {
            maxPf = patternGraph.getVertexById(id).getPf();
          }
        }
        // pf相等的列表
        ArrayList<Integer> pfEqList = new ArrayList<>();
        for (Integer id : dmEqList) {
          if (patternGraph.getVertexById(id).getPf() == maxPf) {
            pfEqList.add(id);
          }
        }
        if (pfEqList.size() == 1) {
          // pf唯一
          NG1.add(pfEqList.get(0));
        } else {
          // pf不唯一，需要比较出入度之和
          int maxDegree = 0;
          for (Integer id : pfEqList) {
            if (patternGraph.getVertexById(id).getTotalDegree() > maxDegree) {
              maxDegree = patternGraph.getVertexById(id).getTotalDegree();
            }
          }
          // 节点出入度相同的列表
          ArrayList<Integer> degreeEqList = new ArrayList<>();
          for (Integer id : pfEqList) {
            if (patternGraph.getVertexById(id).getTotalDegree() == maxDegree) {
              degreeEqList.add(id);
            }
          }
          // 不管degree是否唯一，都选择取第一个就好了
          NG1.add(degreeEqList.get(0));
        }
      }
      // 到这里一定添加了一个节点进去，需要更新剩余节点的dm了
      // 先将剩下节点的dm清除
      for (Vertex v : patternGraph.getVertices()) {
        if (!ArrayUtils.contains(NG1, v.getId())) {
          v.setDm(0);
        } else {
          // 为了防止重复处理，将已经进入序列的dm置-1
          v.setDm(-1);
        }
      }
      // 一个在一个不在，就可以添加dm
      for (Edge e : patternGraph.getEdges()) {
        if (ArrayUtils.contains(NG1, e.getStartV().getId())) {
          if (!ArrayUtils.contains(NG1, e.getEndV().getId())) {
            Vertex vById = patternGraph.getVertexById(e.getEndV().getId());
            vById.setDm(vById.getDm() + 1);
          }
        } else {
          if (ArrayUtils.contains(NG1, e.getEndV().getId())) {
            Vertex vById = patternGraph.getVertexById(e.getStartV().getId());
            vById.setDm(vById.getDm() + 1);
          }
        }
      }
      // 然后进行下一次循环
    }
  }

  /**
   * 预处理模式图，为节点绑定父节点
   *
   * @param patternGraph 模式图
   */
  public static void preprocessPatternGraph(Graph patternGraph) {
    // 已经处理好的节点
    ArrayList<Integer> processedNodes = new ArrayList<>();
    processedNodes.add(NG1.get(0));
    for (Integer integer : NG1) {
      Vertex cur = patternGraph.getVertexById(integer);
      for (Edge e : patternGraph.getEdges()) {
        if (e.getStartV().getId() == cur.getId()
            && !ArrayUtils.contains(processedNodes, e.getEndV().getId())) {
          Parent.put(e.getEndV().getId(), cur.getId());
          processedNodes.add(e.getEndV().getId());
        }
        if (e.getEndV().getId() == cur.getId()
            && !ArrayUtils.contains(processedNodes, e.getStartV().getId())) {
          Parent.put(e.getStartV().getId(), cur.getId());
          processedNodes.add(e.getStartV().getId());
        }
      }
    }
  }

  /**
   * 获取下一对候选节点，还有三个参数S,NG1,Parent
   *
   * @param coupleC     上一个插入的节点对
   * @param patterGraph 模式图，小图
   * @param targetGraph 目标图，大图
   * @return 下一对候选节点对
   */
  public static MatchCouple getNextCandidate(
      ArrayList<MatchCouple> sc, MatchCouple coupleC, Graph patterGraph, Graph targetGraph) {
    Vertex un = null, vn = null;
    if (coupleC.getU() == null) {
      un = getNextInSequence(sc, patterGraph);
      if (un == null) {
        // the sequence is finished
        return new MatchCouple(null, null);
      }
    } else {
      un = coupleC.getU();
    }
    Vertex parentOfUn = null;
    Integer parentId = Parent.get(un.getId());
    if (parentId != null) {
      parentOfUn = patterGraph.getVertexById(parentId);
    }
    if (parentOfUn == null) {
      // un has not a parent node
      vn = getNextNode(sc, coupleC, targetGraph, un);
      return new MatchCouple(un, vn);
    } else {
      // get the node matched to Parent(un)
      Vertex _v = ArrayUtils.getVFromScByU(sc, parentOfUn.getId());
      for (int i = 0; i < patterGraph.getEdges().size(); i++) {
        Edge e = patterGraph.getEdges().get(i);
        // un is predecessor of Parent(un)
        if (e.getEndV().getId() == parentOfUn.getId() && e.getStartV().getId() == un.getId()) {
          vn = getNextNode(sc, coupleC, _v, 0, targetGraph, un);
          return new MatchCouple(un, vn);
        }
        // un is successor of Parent(un)
        if (e.getStartV().getId() == parentOfUn.getId() && e.getEndV().getId() == un.getId()) {
          vn = getNextNode(sc, coupleC, _v, 1, targetGraph, un);
          return new MatchCouple(un, vn);
        }
      }
    }
    // there is not a pair for un
    return new MatchCouple(null, null);
  }

  /**
   * 根据当前的状态，获取下一个需要匹配的节点
   *
   * @param sc           上一个匹配的状态
   * @param patternGraph 模式图
   * @return 匹配到的节点，否则null
   */
  public static Vertex getNextInSequence(ArrayList<MatchCouple> sc, Graph patternGraph) {
    // 如果没有返回第一个
    if (sc.size() == 0) {
      return patternGraph.getVertexById(NG1.get(0));
    }
    int lastId = sc.get(sc.size() - 1).getU().getId();
    for (int i = 0; i < NG1.size(); i++) {
      if (NG1.get(i) == lastId) {
        if (i == NG1.size() - 1) {
          // 已经是最后一个了
          return null;
        } else {
          return patternGraph.getVertexById(NG1.get(i + 1));
        }
      }
    }
    return null;
  }

  /**
   * 获取目标图中可选的下一个候选节点
   *
   * @param sc          上一个匹配的状态sc
   * @param coupleC     上一个匹配的节点对（uc, vc）
   * @param targetGraph 目标图
   * @param un          un节点
   * @return 下一个节点/没有则为null
   */
  public static Vertex getNextNode(
      ArrayList<MatchCouple> sc, MatchCouple coupleC, Graph targetGraph, Vertex un) {
    if (un == null) {
      return null;
    }
    // 目标图中和un同类型并且没有被匹配走的节点
    ArrayList<Vertex> R2 = new ArrayList<>();
    for (Vertex v : targetGraph.getVertices()) {
      if (LabelUtils.vertexLabelEqual(v.getLabel(), un.getLabel())
          && !ArrayUtils.containsTargetGraphVertex(sc, v.getId())) {
        R2.add(v);
      }
    }
    // 没有返回null
    if (R2.size() == 0) {
      return null;
    }
    // 上一个为 null,返回第一个
    if (coupleC.getV() == null) {
      return R2.get(0);
    } else {
      for (int i = 0; i < R2.size(); i++) {
        if (R2.get(i).getId() == coupleC.getV().getId()) {
          // 已经是最后一个了
          if (i == R2.size() - 1) {
            return null;
          } else {
            // 返回下一个
            return R2.get(i + 1);
          }
        }
      }
    }
    return null;
  }

  /**
   * 获取目标图中可选的下一个候选节点，添加了关于_v节点的限制
   *
   * @param sc          上一个匹配的状态
   * @param coupleC     上一个匹配的节点对
   * @param _v          限制节点
   * @param type        类型，是寻找前置节点 0 还是寻找后置节点 1
   * @param targetGraph 目标图
   * @param un          un节点
   * @return 下一个候选v节点，否则为null
   */
  public static Vertex getNextNode(
      ArrayList<MatchCouple> sc,
      MatchCouple coupleC,
      Vertex _v,
      int type,
      Graph targetGraph,
      Vertex un) {
    if (un == null) {
      return null;
    }
    // 目标图中和 un 同类型并且没有被匹配走并且与 _v 做限制之后的节点
    ArrayList<Vertex> R2_v = new ArrayList<>();
    for (Vertex v : targetGraph.getVertices()) {
      if (type == 0) { // 前置节点
        if (LabelUtils.vertexLabelEqual(v.getLabel(), un.getLabel())
            && targetGraph.containsEdge(v.getId(), _v.getId())
            && !ArrayUtils.containsTargetGraphVertex(sc, v.getId())) {
          R2_v.add(v);
        }
      } else {
        // 后置节点
        if (LabelUtils.vertexLabelEqual(v.getLabel(), un.getLabel())
            && targetGraph.containsEdge(_v.getId(), v.getId())
            && !ArrayUtils.containsTargetGraphVertex(sc, v.getId())) {
          R2_v.add(v);
        }
      }
    }
    // 没有返回null
    if (R2_v.size() == 0) {
      return null;
    }
    // 上一个为 null,返回第一个
    if (coupleC.getV() == null) {
      return R2_v.get(0);
    } else {
      for (int i = 0; i < R2_v.size(); i++) {
        if (R2_v.get(i).getId() == coupleC.getV().getId()) {
          // 已经是最后一个了
          if (i == R2_v.size() - 1) {
            return null;
          } else {
            // 返回下一个
            return R2_v.get(i + 1);
          }
        }
      }
    }
    return null;
  }

  /**
   * 判断sc加入coupleN之后是否符合一致性，判断规则分为语义和结构一致性 IsFeasible(sc, un, vn) = Fs(sc, un, vn) ^ Ft(sc, un, vn)
   * Ft(sc, un, vn) = Fc(sc, un, vn) ^ Fla1(sc, un, vn) ^ Fla2(sc, un, vn)
   *
   * @param sc           当前状态
   * @param coupleN      选出来的一对候选节点
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 是否符合boolean
   */
  public static boolean isFeasible(
      ArrayList<MatchCouple> sc, MatchCouple coupleN, Graph patternGraph, Graph targetGraph) {
    return fs(sc, coupleN, patternGraph, targetGraph) && ft(sc, coupleN, patternGraph, targetGraph);
  }

  /**
   * 判断语义一致性,Fs只需要判断新增的这两个节点是否类型相同，并且加入这两个节点后，添加的边的类型是否也是一致的，注意这里只需要针对模式图中的节点和边进行验证
   *
   * @param sc           状态sc
   * @param coupleN      新的候选节点对（un,vn）
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 是否语义一致boolean
   */
  public static boolean fs(
      ArrayList<MatchCouple> sc, MatchCouple coupleN, Graph patternGraph, Graph targetGraph) {
    if (coupleN.getU() == null || coupleN.getV() == null) {
      return false;
    }
    if (!LabelUtils.vertexLabelEqual(coupleN.getU().getLabel(), coupleN.getV().getLabel())) {
      return false;
    }
    for (MatchCouple mc : sc) {
      Vertex uVertex = mc.getU();
      Vertex _uVertex = coupleN.getU();
      Vertex vVertex = ArrayUtils.getVFromScByU(sc, uVertex.getId()); // 拿到映射的 v 节点
      if (vVertex == null) {
        return false;
      }
      Vertex _vVertex = coupleN.getV();
      // u -> _u
      Edge uEdge = patternGraph.getSpecificEdge(uVertex.getId(), _uVertex.getId());
      if (uEdge != null) {
        // 说明存在一条边，就需要往下判断
        Edge vEdge = targetGraph.getSpecificEdge(vVertex.getId(), _vVertex.getId());
        if (vEdge != null) {
          // 边label要一致
          if (!uEdge.getLabel().getRelation().equals(vEdge.getLabel().getRelation())) {
            return false;
          }
        } else {
          // 如果模式图中存在边，目标图却没有，就返回false
          return false;
        }
      }
      // _u -> u
      Edge uEdge2 = patternGraph.getSpecificEdge(_uVertex.getId(), uVertex.getId());
      if (uEdge2 != null) {
        // 说明存在一条边，就需要往下判断
        Edge vEdge = targetGraph.getSpecificEdge(_vVertex.getId(), vVertex.getId());
        if (vEdge != null) {
          // 边label要一致
          if (!uEdge2.getLabel().getRelation().equals(vEdge.getLabel().getRelation())) {
            return false;
          }
        } else {
          // 如果模式图中存在边，目标图却没有，就返回false
          return false;
        }
      }
    }
    // 如果上面没有找到边，或者找到的边都符合一致性，就返回 true
    return true;
  }

  /**
   * 判断加入新节点后结构是否一致，包括本身，1-lookahead,2-lookahead
   *
   * @param sc           状态sc
   * @param coupleN      新的候选节点对（un,vn）
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 是否结构一致boolean
   */
  public static boolean ft(
      ArrayList<MatchCouple> sc, MatchCouple coupleN, Graph patternGraph, Graph targetGraph) {
    return fc(sc, coupleN, patternGraph, targetGraph)
        && fla1(sc, coupleN, patternGraph, targetGraph)
        && fla2(sc, coupleN, patternGraph, targetGraph);
  }

  /**
   * 判断新增的这一对节点与已经匹配的节点之间的联系是不是一一对应的，这个规则和上面fs规则差不多，但是这个地方两边都要判断不能出现一个图有一个图没有的情况
   *
   * @param sc           状态sc
   * @param coupleN      新的候选节点对（un,vn）
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 是否一致boolean
   */
  public static boolean fc(
      ArrayList<MatchCouple> sc, MatchCouple coupleN, Graph patternGraph, Graph targetGraph) {
    Vertex _u = coupleN.getU();
    Vertex _v = coupleN.getV();
    for (MatchCouple mc : sc) {
      Vertex u = mc.getU();
      Vertex v = mc.getV();
      // u -> _u的边
      Edge uEdge = patternGraph.getSpecificEdge(u.getId(), _u.getId());
      Edge vEdge = targetGraph.getSpecificEdge(v.getId(), _v.getId());
      // 一个有一个没有直接错误
      if ((uEdge == null && vEdge != null) || (uEdge != null && vEdge == null)) {
        return false;
      } else if (uEdge != null) {
        // 两个都有则判断label
        if (!uEdge.getLabel().getRelation().equals(vEdge.getLabel().getRelation())) {
          return false;
        }
      }
      // _u -> u的边
      Edge uEdge1 = patternGraph.getSpecificEdge(_u.getId(), u.getId());
      Edge vEdge1 = targetGraph.getSpecificEdge(_v.getId(), v.getId());
      // 一个有一个没有直接错误
      if ((uEdge1 == null && vEdge1 != null) || (uEdge1 != null && vEdge1 == null)) {
        return false;
      } else if (uEdge1 != null) {
        // 两个都有则判断label
        if (!uEdge1.getLabel().getRelation().equals(vEdge1.getLabel().getRelation())) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * 1-lookahead
   * 一步往前看，实际是看，新增的节点对（un，vn）的后继节点和前继节点是不是符合要求，un的后继（前继）节点必须小于等于vn的后继（前继）节点数，也就是模式图要小于等于大图
   *
   * @param sc           状态sc
   * @param coupleN      新的候选节点对（un,vn）
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 是否一致boolean
   */
  public static boolean fla1(
      ArrayList<MatchCouple> sc, MatchCouple coupleN, Graph patternGraph, Graph targetGraph) {
    Vertex _u = coupleN.getU();
    Vertex _v = coupleN.getV();
    // 先统计出模式图和目标图的sc和coupleN共同的关联节点，共有四种情况
    ArrayList<Vertex> patternNodes = new ArrayList<>();
    ArrayList<Vertex> targetNodes = new ArrayList<>();
    // 第一种:都为后继
    for (Vertex pv : patternGraph.getVertices()) {
      if (pv.getId() != _u.getId() && !ArrayUtils.containsPatternGraphVertex(sc, pv.getId())) {
        if (patternGraph.containsEdge(_u.getId(), pv.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 1)) {
          patternNodes.add(pv);
        }
      }
    }
    for (Vertex pv : targetGraph.getVertices()) {
      if (pv.getId() != _v.getId() && !ArrayUtils.containsTargetGraphVertex(sc, pv.getId())) {
        if (targetGraph.containsEdge(_v.getId(), pv.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 1)) {
          targetNodes.add(pv);
        }
      }
    }
    if (!compareLabelNodeSize(patternNodes, targetNodes)) {
      return false;
    }

    // 第二种:都为前继
    patternNodes.clear();
    targetNodes.clear();
    for (Vertex pv : patternGraph.getVertices()) {
      if (pv.getId() != _u.getId() && !ArrayUtils.containsPatternGraphVertex(sc, pv.getId())) {
        if (patternGraph.containsEdge(pv.getId(), _u.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 0)) {
          patternNodes.add(pv);
        }
      }
    }
    for (Vertex pv : targetGraph.getVertices()) {
      if (pv.getId() != _v.getId() && !ArrayUtils.containsTargetGraphVertex(sc, pv.getId())) {
        if (targetGraph.containsEdge(pv.getId(), _v.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 0)) {
          targetNodes.add(pv);
        }
      }
    }
    if (!compareLabelNodeSize(patternNodes, targetNodes)) {
      return false;
    }

    // 第三种：sc前继，coupleN后继
    patternNodes.clear();
    targetNodes.clear();
    for (Vertex pv : patternGraph.getVertices()) {
      if (pv.getId() != _u.getId() && !ArrayUtils.containsPatternGraphVertex(sc, pv.getId())) {
        if (patternGraph.containsEdge(_u.getId(), pv.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 0)) {
          patternNodes.add(pv);
        }
      }
    }
    for (Vertex pv : targetGraph.getVertices()) {
      if (pv.getId() != _v.getId() && !ArrayUtils.containsTargetGraphVertex(sc, pv.getId())) {
        if (targetGraph.containsEdge(_v.getId(), pv.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 0)) {
          targetNodes.add(pv);
        }
      }
    }
    if (!compareLabelNodeSize(patternNodes, targetNodes)) {
      return false;
    }

    // 第四种：sc后继，coupleN前继
    patternNodes.clear();
    targetNodes.clear();
    for (Vertex pv : patternGraph.getVertices()) {
      if (pv.getId() != _u.getId() && !ArrayUtils.containsPatternGraphVertex(sc, pv.getId())) {
        if (patternGraph.containsEdge(pv.getId(), _u.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 1)) {
          patternNodes.add(pv);
        }
      }
    }
    for (Vertex pv : targetGraph.getVertices()) {
      if (pv.getId() != _v.getId() && !ArrayUtils.containsTargetGraphVertex(sc, pv.getId())) {
        if (targetGraph.containsEdge(pv.getId(), _v.getId())
            && ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 1)) {
          targetNodes.add(pv);
        }
      }
    }
    if (!compareLabelNodeSize(patternNodes, targetNodes)) {
      return false;
    }
    // 没有出现不满足的情况则返回真
    return true;
  }

  /**
   * 2-lookahead 两步向前预测，这里就是看V~,实际就是剩下的未匹配的也不是已匹配节点的前继后继节点的节点，这些节点需要满足小于等于的关系
   *
   * @param sc           状态sc
   * @param coupleN      新的候选节点对（un,vn）
   * @param patternGraph 模式图
   * @param targetGraph  目标图
   * @return 是否一致boolean
   */
  public static boolean fla2(
      ArrayList<MatchCouple> sc, MatchCouple coupleN, Graph patternGraph, Graph targetGraph) {
    // 需要先找出所有剩下的并且与sc没关联与coupleN有关系的节点
    Vertex _u = coupleN.getU();
    Vertex _v = coupleN.getV();
    // 先统计出模式图和目标图的sc和coupleN共同的关联节点，共有四种情况
    ArrayList<Vertex> patternNodes = new ArrayList<>();
    ArrayList<Vertex> targetNodes = new ArrayList<>();
    // 第一种:coupleN的前继
    for (Vertex pv : patternGraph.getVertices()) {
      if (pv.getId() != _u.getId() && !ArrayUtils.containsPatternGraphVertex(sc, pv.getId())) {
        if (patternGraph.containsEdge(pv.getId(), _u.getId())
            && (!ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 1)
            && !ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 0))) {
          patternNodes.add(pv);
        }
      }
    }
    for (Vertex pv : targetGraph.getVertices()) {
      if (pv.getId() != _v.getId() && !ArrayUtils.containsTargetGraphVertex(sc, pv.getId())) {
        if (targetGraph.containsEdge(pv.getId(), _v.getId())
            && (!ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 1)
            && !ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 0))) {
          targetNodes.add(pv);
        }
      }
    }
    if (!compareLabelNodeSize(patternNodes, targetNodes)) {
      return false;
    }

    // 第一种:coupleN的后继
    patternNodes.clear();
    targetNodes.clear();
    for (Vertex pv : patternGraph.getVertices()) {
      if (pv.getId() != _u.getId() && !ArrayUtils.containsPatternGraphVertex(sc, pv.getId())) {
        if (patternGraph.containsEdge(_u.getId(), pv.getId())
            && (!ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 1)
            && !ArrayUtils.containsEdgeOnScList(sc, pv.getId(), patternGraph, 0, 0))) {
          patternNodes.add(pv);
        }
      }
    }
    for (Vertex pv : targetGraph.getVertices()) {
      if (pv.getId() != _v.getId() && !ArrayUtils.containsTargetGraphVertex(sc, pv.getId())) {
        if (targetGraph.containsEdge(_v.getId(), pv.getId())
            && (!ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 1)
            && !ArrayUtils.containsEdgeOnScList(sc, pv.getId(), targetGraph, 1, 0))) {
          targetNodes.add(pv);
        }
      }
    }
    if (!compareLabelNodeSize(patternNodes, targetNodes)) {
      return false;
    }
    return true;
  }

  /**
   * 比较模式图和目标图节点列表中各个类型的节点是否是小于关系
   *
   * @param patternNodes 模式图节点列表
   * @param targetNodes  目标图节点列表
   * @return 是否满足关系
   */
  public static boolean compareLabelNodeSize(
      ArrayList<Vertex> patternNodes, ArrayList<Vertex> targetNodes) {
    if (patternNodes.size() > targetNodes.size()) {
      return false;
    } else {
      // 不断删减patternNodes，直到没有节点了
      while (patternNodes.size() > 0) {
        int countP = 0;
        String label = "";
        for (int i = 0; i < patternNodes.size(); i++) {
          if (countP == 0) {
            label = patternNodes.get(i).getLabel();
            countP++;
            patternNodes.remove(i);
            i--;
          } else {
            if (LabelUtils.vertexLabelEqual(patternNodes.get(i).getLabel(), label)) {
              countP++;
              patternNodes.remove(i);
              i--;
            }
          }
        }
        // 统计targetGraph中该类型节点的数量
        int countT = 0;
        for (Vertex v : targetNodes) {
          if (LabelUtils.vertexLabelEqual(v.getLabel(), label)) {
            countT++;
          }
        }
        if (countP > countT) {
          return false;
        }
      }
    }
    return true;
  }
}