package DataFreme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFreme {
    public List<String> col_names;
    public List<ArrayList> data;
    private int data_lenth = 0;
    private DataFreme_tool tool = new DataFreme_tool();

    public DataFreme(){
        this.col_names = null;
        this.data = null;
        this.data_lenth = 0;
    }

    public DataFreme(List<ArrayList> data, List<String> col_names){
        if(data.size() != col_names.size()){
            System.err.println("DataFreme初始化失败！");
        }else {
            this.data = data;
            this.col_names = col_names;
            this.data_lenth = data.get(0).size();
        }
    }

    public ArrayList get_column(String col_name){
        return tool.get_column(this, col_name);
    }

    public void del_column(String col_name){
        tool.del_column(this, col_name);
    }

    public String toString(){
        System.out.print("\t");
        for (String col_name : col_names) {
            System.out.print("\t" + col_name);
        }
        System.out.println();
        for (int i=0; i<data_lenth; i++){
            System.out.print(i);
            for (int j=0; j<col_names.size(); j++){
                System.out.print("\t\t\t" + data.get(j).get(i));
            }
            System.out.println();
        }
        return "\n";
    }

    public void read_table(String path, List<String> col_names, String sep) {
        data = new ArrayList<ArrayList>();
        this.col_names = col_names;
        int len = col_names.size();
        for(int i = 0; i<len; i++){
            data.add(new ArrayList<>());
        }
        File csv = new File(path);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(csv));
            String line;
            String[] lineList;
            while ((line = br.readLine()) != null) {
                lineList = line.split(sep);
                for(int i = 0; i<len; i++){
                    data.get(i).add(lineList[i]);
                }
            }
            this.data_lenth = data.get(0).size();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}
