package test.generator;

import java.io.*;

public class TextGenerator {
  public static void main(String[] args) throws IOException {
//    new TextGenerator().generate();
  }

  public void generate() throws IOException {
    String baseUrl = this.getClass().getClassLoader().getResource("").getPath().replace("/out/production/VF3", "/src") + "test/";
    System.out.println(baseUrl);
    int[] fileIndex = {5, 10, 20, 40};
    for (int testIndex = 1; testIndex <= 41; testIndex++) {
      for (int j = 0; j < 4; j++) {
        BufferedWriter writer = new BufferedWriter(new FileWriter(baseUrl + testIndex + "/" + fileIndex[j] + ".txt"));
        if (j == 0) {
          generate5(writer);
        } else if (j ==1) {
          generate10(writer);
        } else if (j == 2) {
          generate20(writer);
        } else {
          generate40(writer);
        }

        writer.flush();
        writer.close();
      }
    }
  }

  public void generate5(BufferedWriter writer) throws IOException {
    String[] s1 = {"t-0-0", "t-0-1", "t-1-0", "t-1-1"};
    int j = 0;
    while (j < 4) {
      writer.write(s1[j]);
      writer.newLine();
      // 写点
      for (int i = 0; i < 1; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 1; i < 4; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      for (int i = 4; i < 5; i++) {
        writer.write("v " + i + " " + Constant.getFieldRole());
        writer.newLine();
      }
      // 写边
      for (int i = 1; i < 5; i++) {
        writer.write("e 0 " + i + " " + Constant.getLabel());
        writer.newLine();
      }
      writer.write("1");
      writer.newLine();
      j++;
    }
  }

  public void generate10(BufferedWriter writer) throws IOException {
    String[] s1 = {"t-0-0", "t-0-1", "t-1-0", "t-1-1", "t-2-0", "t-2-1"};
    int j = 0;
    while (j < 6) {
      writer.write(s1[j]);
      writer.newLine();
      // 写点
      for (int i = 0; i < 1; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 1; i < 6; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      for (int i = 6; i < 7; i++) {
        writer.write("v " + i + " " + Constant.getFieldRole());
        writer.newLine();
      }
      for (int i = 7; i < 9; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 9; i < 10; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      // 写边
      for (int i = 1; i < 6; i++) {
        writer.write("e 0 " + i + " " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 6; i < 7; i++) {
        writer.write("e 0 " + i + " declare");
        writer.newLine();
      }
      for (int i = 7; i < 8; i++) {
        writer.write("e " + i + " 3 " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 8; i < 9; i++) {
        writer.write("e " + i + " 4 " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 9; i < 10; i++) {
        writer.write("e 5 " + i + " call");
        writer.newLine();
      }
      writer.write("1");
      writer.newLine();
      j++;
    }
  }

  public void generate20(BufferedWriter writer) throws IOException {
    String[] s1 = {"t-0-0", "t-0-1", "t-1-0", "t-1-1", "t-2-0", "t-2-1", "t-4-0", "t-4-1"};
    int j = 0;
    while (j < 8) {
      writer.write(s1[j]);
      writer.newLine();
      // 写点
      for (int i = 0; i < 1; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 1; i < 8; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      for (int i = 8; i < 10; i++) {
        writer.write("v " + i + " " + Constant.getFieldRole());
        writer.newLine();
      }
      // 第二层开始
      for (int i = 10; i < 13; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 13; i < 17; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      // 第三层开始
      for (int i = 17; i < 18; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 18; i < 20; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      // 写边
      for (int i = 1; i < 8; i++) {
        writer.write("e 0 " + i + " " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 8; i < 10; i++) {
        writer.write("e 0 " + i + " declare");
        writer.newLine();
      }
      // 第二层
      for (int i = 10; i < 11; i++) {
        writer.write("e " + i + " 2 " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 11; i < 13; i++) {
        writer.write("e " + i + " 4 " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 13; i < 15; i++) {
        writer.write("e 5 " + i + " call");
        writer.newLine();
      }
      for (int i = 15; i < 17; i++) {
        writer.write("e 6 " + i + " call");
        writer.newLine();
      }
      // 第三层
      for (int i = 17; i < 18; i++) {
        writer.write("e " + i + " 15 " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 18; i < 20; i++) {
        writer.write("e 16 " + i + " call");
        writer.newLine();
      }
      writer.write("1");
      writer.newLine();
      j++;
    }
  }

  public void generate40(BufferedWriter writer) throws IOException {
    String[] s1 = {"t-0-0", "t-0-1", "t-1-0", "t-1-1", "t-2-0", "t-2-1", "t-4-0", "t-4-1"};
    int j = 0;
    while (j < 8) {
      writer.write(s1[j]);
      writer.newLine();
      // 写点
      for (int i = 0; i < 1; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 1; i < 12; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      for (int i = 12; i < 16; i++) {
        writer.write("v " + i + " " + Constant.getFieldRole());
        writer.newLine();
      }
      // 第二层开始
      for (int i = 16; i < 20; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 20; i < 30; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      // 第三层开始
      for (int i = 30; i < 33; i++) {
        writer.write("v " + i + " " + Constant.getClassRole());
        writer.newLine();
      }
      for (int i = 33; i < 38; i++) {
        writer.write("v " + i + " " + Constant.getMethodRole());
        writer.newLine();
      }
      for (int i = 38; i < 40; i++) {
        writer.write("v " + i + " " + Constant.getFieldRole());
        writer.newLine();
      }
      // 写边
      for (int i = 1; i < 12; i++) {
        writer.write("e 0 " + i + " " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 12; i < 16; i++) {
        writer.write("e 0 " + i + " declare");
        writer.newLine();
      }
      // 第二层
      for (int i = 16; i < 20; i++) {
        writer.write("e " + i + " " + (i - 12) + " " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 20; i < 28; i++) {
        writer.write("e " + (i - 16) + " " + i + " call");
        writer.newLine();
      }
      for (int i = 28; i < 30; i++) {
        writer.write("e " + i + " " + (i - 26) + " call");
        writer.newLine();
      }
      // 第三层
      for (int i = 30; i < 32; i++) {
        writer.write("e " + i + " 17 " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 32; i< 33; i++) {
        writer.write("e " + i + " 18 " + Constant.getLabel());
        writer.newLine();
      }
      for (int i = 33; i < 35; i++) {
        writer.write("e " + i + " 22 call");
        writer.newLine();
      }
      for (int i = 35; i < 38; i++) {
        writer.write("e 26 " + i + " call");
        writer.newLine();
      }
      for (int i = 38; i < 40; i++) {
        writer.write("e 18 " + i + " declare");
        writer.newLine();
      }
      writer.write("1");
      writer.newLine();
      j++;
    }
  }


}
