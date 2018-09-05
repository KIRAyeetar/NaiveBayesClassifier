package DataFreme;

import java.util.ArrayList;

public class DataFreme_tool {
    public ArrayList get_column(DataFreme dataFreme, String col_name){
        int count = 0;
        for (String name : dataFreme.col_names) {
            if (name.equals(col_name)){
                break;
            }else count ++;
        }
        if(count == dataFreme.col_names.size()){
            System.err.println("column name <"+ col_name +"> error! return null.");
            return null;
        }else {
            return dataFreme.data.get(count);
        }
    }

    public void del_column(DataFreme dataFreme, String col_name){
        int count = 0;
        for (String name : dataFreme.col_names) {
            if (name.equals(col_name)){
                break;
            }else count ++;
        }
        if(count == dataFreme.col_names.size()){
            System.err.println("column name error! return null.");
        }else {
            dataFreme.data.remove(count);
            dataFreme.col_names.remove(count);
        }
    }
}
