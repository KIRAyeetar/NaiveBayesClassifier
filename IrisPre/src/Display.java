import DataFreme.DataFreme;
import Models.NaiveBayesClassifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Display {
    String path_train = "Data/iris.csv";
    String path_val = "Data/iris_validation.csv";
    private DataFreme dataFreme = new DataFreme();

    public void show_main_page(){
        System.out.println("欢迎来到鸢尾花种类预测系统！\n");
        Scanner scan = new Scanner(System.in);

        int flag = 1;
        while (true){
            read(path_train);
            System.out.println("请输入序号选择接下来的操作：");
            System.out.println("1.简介");
            System.out.println("2.展示训练数据集");
            System.out.println("3.展示验证数据集");
            System.out.println("4.开始预测");
            System.out.println("0.离开系统");
            System.out.print("输入序号：");
            flag = scan.nextInt();
            System.out.println();
            if (flag == 1) {
                show_introduce();
            }else if (flag == 2){
                System.out.println(dataFreme);
            }else if (flag == 3){
                read(path_val);
                System.out.println(dataFreme);
            }else if (flag == 4){
                show_predict();
            }else if (flag == 0){
                System.out.println("bye bye~~~");
                break;
            }

            System.out.println();
            System.out.print("请按回车键按键继续... ...\n");
            try {
                System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void read(String path){
        List<String> col_name = new ArrayList<String>();
        col_name.add("sepal_wid");
        col_name.add("sepal_len");
        col_name.add("petal_wid");
        col_name.add("petal_len");
        col_name.add("target");
        dataFreme.read_table(path, col_name, ",");
    }

    private void show_predict(){
        NaiveBayesClassifier naiveBayesClassifier = new NaiveBayesClassifier();
        List list = dataFreme.get_column("target");
        List<Integer> list_tmp = new ArrayList<>();
        for (Object aList : list) {
            list_tmp.add(Integer.parseInt(aList.toString()));
        }
        dataFreme.del_column("target");
        naiveBayesClassifier.fit(dataFreme.data, list_tmp);
        List<Double> iris_data;
        do {
            iris_data = new ArrayList<Double>();
            Scanner scan = new Scanner(System.in);
            System.out.println("请输入鸢尾花数据(正常情况数据为(0-7cm)");
            System.out.print("请输入花萼宽度：");
            iris_data.add(scan.nextDouble());
            System.out.print("请输入花萼长度：");
            iris_data.add(scan.nextDouble());
            System.out.print("请输入花瓣宽度：");
            iris_data.add(scan.nextDouble());
            System.out.print("请输入花瓣长度：");
            iris_data.add(scan.nextDouble());
        }while (check_input(iris_data));
        String iris_name;
        int iris_cat = naiveBayesClassifier.predict(iris_data);
        if(iris_cat == 0) iris_name = "山鸢尾";
        else if(iris_cat == 1) iris_name = "杂色鸢尾";
        else iris_name = "维吉尼亚鸢尾";
        System.out.println("鸢尾花种类为："+iris_cat+"-"+iris_name);
    }

    private void show_introduce(){
        System.out.println(
                "鸢尾花数据集是由杰出的统计学家R.A.Fisher在20世纪30年代中期创建的，它被公认为用于数据挖掘的最著名的数据集。\n" +
                "它包含3种植物种类（Iris setosa（0-山鸢尾）、Iris versicolor（1-杂色鸢尾）和Iris virginica（2-维吉尼亚鸢尾），每种各有50个样本。\n" +
                "它由4个属性组成：sepal length（花萼长度）、sepal width（花萼宽度）、petal length（花瓣长度）和petal width（花瓣宽度）（单位是cm）。\n" +
                "本系统将通过用户输入的四个属性值，来预测属于哪一种鸢尾花。");
    }

    private boolean check_input(List<Double> list){
        for(double mlist : list){
            if (mlist<=0 || mlist>= 10){
                System.out.println("\n数据有误，请重新输入！");
                return true;
            }
        }
        return false;
    }
}
