package Models;

import DataFreme.DataFreme;

import java.util.*;

public class NaiveBayesClassifier {
    private Probability probability = new Probability();
    private ArrayList<Integer> target_list = null;
    private ArrayList<Double> target_P = new ArrayList<>();

    public void fit(List<ArrayList> data, List<Integer> target){
        target_list  = new ArrayList<Integer>(new HashSet<Integer>(target));
        for (int i=0; i<target_list.size(); i++){
            List<ArrayList> data_tmp = new ArrayList<>();
            for (int j=0; j<data.size(); j++){
                data_tmp.add(new ArrayList<>());
                for (int k=0; k<data.get(j).size(); k++){
                    if(target.get(k) == i){
                        data_tmp.get(j).add(data.get(j).get(k));
                    }
                }
                probability.memorizer.put("_"+i+"_", (data_tmp.get(0).size()*1.0/data.get(0).size()));
                probability.fit(data_tmp, i);
            }
            // fit结束
        }
    }

    public int predict(List<Double> data){
        int result = 0;
        double mul_tmp = 0;
        for (int i=0; i<target_list.size(); i++){
            double mul = probability.memorizer.get("_"+target_list.get(i)+"_");

            for (int j=0; j<data.size(); j++){
                double ave = probability.memorizer.get("_"+target_list.get(i)+"_"+j+"_ave_");
                double sigma = probability.memorizer.get("_"+target_list.get(i)+"_"+j+"_sigma_");
                mul*= probability.probability_compute(data.get(j), ave, sigma);
            }

            if (mul>=mul_tmp){
                mul_tmp = mul;
                result = target_list.get(i);
            }
        }
        return result;
    }
}

class Probability{
    private int attr;
    public Map<String, Double> memorizer = new HashMap<String, Double>();

    public double probability_compute(double x, double u, double o){
        double e = Math.E;
        double pi = Math.PI;
        double part_1 = 1 / Math.sqrt(2*pi)/o;
        double part_2 = Math.pow(e, -1*((x-u)*(x-u)/2/o/o));
        return part_1 * part_2;
    }

    public void fit(List<ArrayList> data, int target){
        for(int i=0; i<data.size(); i++){
            double ave = get_ave(data.get(i));
            double sigma = get_sigma(data.get(i));
            memorizer.put("_"+target+"_"+i+"_ave_",ave);
            memorizer.put("_"+target+"_"+i+"_sigma_",sigma);
        }
    }

    private double get_ave(List<Double> column){
        double sum = 0;
        int count = 1;
        for (;count <= column.size(); count++){
            sum += Double.parseDouble(String.valueOf(column.get(count-1)));
        }
        return sum/count;
    }

    private double get_sigma(List<Double> column){
        double ave = get_ave(column);
        double sum = 0;
        int count = 1;
        for (;count <= column.size(); count++){
            double tmp = (Double.parseDouble(String.valueOf(column.get(count-1)))-ave);
            sum += tmp*tmp;
        }
        return sum/count;
    }
}