package automata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


/*
simulates river crossing directly
 */

/**
 *
 * @author rub account
 */
public class MP2Simulation {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            Scanner sc = new Scanner(new File("mp2.in"));
            int ctr=0;
            PrintWriter writer = new PrintWriter("a.out", "UTF-8");
            while(sc.hasNext()){
                String procedure=sc.nextLine();
                
                String westBank="FRLCN"; //represents west bank
                String eastBank="NNNNN"; //represents east bank
                for(int i=0;i<procedure.length();i++){
                    if(((westBank.contains("R")&&westBank.contains("C")&&!westBank.contains("F"))||(westBank.contains("L")&&westBank.contains("R")&&!westBank.contains("F")))||((eastBank.contains("R")&&eastBank.contains("C")&&!eastBank.contains("F"))||(eastBank.contains("L")&&eastBank.contains("R")&&!eastBank.contains("F")))){
                        //^ represents the constraints of the problems
                        //System.out.println("break at "+westBank+"_"+eastBank+" "+procedure.charAt(i));
                        break;
                    }
                    if(i%2==0){//whether the index is even or odd represents which bank did bb compsci cross
                        //System.out.println("w:"+westBank);
                        if(!westBank.contains(procedure.substring(i, i+1))){
                            //System.out.println(procedure.charAt(i)+" "+westBank);
                            break;
                            
                        }
                        westBank=westBank.replaceFirst(procedure.substring(i, i+1), "N"); //replacing procedure.substring(i,i+1)(the current possesion crossing) with 'N' means removing the possesion. 
                        eastBank=eastBank.replaceFirst("N",procedure.substring(i, i+1));  //The reverse is done on the other bank to simulate placing the possesion in the bank
                        westBank=westBank.replaceFirst("F", "N"); //F (representing bb compsci) also crosses the river so the same is done with him
                        eastBank=eastBank.replaceFirst("N","F");
                    }
                    
                    else{
                        //System.out.println("e:"+eastBank);
                        if(!eastBank.contains(procedure.substring(i, i+1))){
                            //System.out.println(procedure.charAt(i)+" "+eastBank);
                            break;
                        }
                        eastBank=eastBank.replaceFirst(procedure.substring(i, i+1), "N"); 
                        westBank=westBank.replaceFirst("N",procedure.substring(i, i+1));
                        eastBank=eastBank.replaceFirst("F", "N");
                        westBank=westBank.replaceFirst("N","F");
                    }
                    //System.out.println(procedure.charAt(i)+"|"+westBank+" "+eastBank);
                }
                if(westBank.equals("NNNNN")){//if west bank is empty then the bb compsci has successfully crossed all possessions
                    writer.println("OK");
                    ctr++;
                    //writer.println(procedure);
                }
                else
                    writer.println("NG");
            }
            System.out.println(ctr);
            writer.close();
        } catch (FileNotFoundException ex) {
            
        }
    }
}
