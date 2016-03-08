/*
Self Building DFA Table
the DFA tables start empty and it will fill up as more test cases from the file are tested
theoretically as we compute faster as the table is completed
 */

package automata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author rub account
 */
public class MP2SelfBuildingDFA {
    public static void main(String[] args) throws UnsupportedEncodingException {
        try {
            Scanner sc = new Scanner(new File("MP2.in"));
            PrintWriter writer = new PrintWriter("MP2.out", "UTF-8");
            List<String[]> rawTable= new ArrayList();
            Map<String,Integer> map=new HashMap(); // maps the row label of the dfa table its index in the list
            Map<Character,Integer> dictionary=new HashMap(); // maps the column header to its index on the string array
            dictionary.put('N', 0);
            dictionary.put('L', 1);
            dictionary.put('R', 2);
            dictionary.put('C', 3);//populates dictionary
            rawTable.add(new String[4]);
            map.put("CFLR_",0);
            int ctr=0;
            while(sc.hasNext()){
                String procedure=sc.nextLine();
                //System.out.println(procedure);
                String currentState="CFLR_";
                for(int i=0;i<procedure.length();i++){
                    String s=currentState;
                    //System.out.println(s);
                    if(map.get(currentState)==null){
                        rawTable.add(new String[4]);
                        map.put(currentState,rawTable.size()-1);
                    }
                    if(currentState.equals("BADMV")||currentState.equals("EATEN")){
                        break;
                    }
                    if(rawTable.get(map.get(currentState))[dictionary.get(procedure.charAt(i))]==null){//rawTable.get(map.get(currentState))[dictionary.get(procedure.charAt(i))] represents transition function delta(q,l) (e.g. delta("CFLR_","L")="CR_FL") if its null then the cell in the dfa table is empty
                        if(eaten(currentState)){ //function eaten checks for moves where the carrot or rabbit is eaten
                            s="EATEN";
                        }
                        else if(procedure.charAt(i)=='N'){//performs delta(q,l) ,if l="N" (farmer crosses alone) where q=currentState and l= procedure.charAt(i)(the procedure indicated in the file)
                            s=currentState.replaceFirst("F","");
                            
                            //s=s.replaceAll("NN", "");
                            if(currentState.indexOf("F")<currentState.indexOf("_")){
                                s=s+"F";
                            }
                            else{
                                s="F"+s;
                            }
                        }
                        else if((currentState.indexOf(procedure.charAt(i))<currentState.indexOf("_")&&currentState.indexOf("F")>currentState.indexOf("_"))||(currentState.indexOf(procedure.charAt(i))>currentState.indexOf("_")&&currentState.indexOf("F")<currentState.indexOf("_"))){
                            s="BADMV"; //^ the above condition checks for illegal moves (e.g. delta("CF_RL","L")=illegalState)
                        }
                        else{//performs delta(q,l) ,if l!="N"(farmer brings something) where q=currentState and l= procedure.charAt(i)(the procedure indicated in the file
                            s=currentState.replaceFirst("F","");
                            s=s.replaceFirst(procedure.substring(i, i+1),"");
                            if(currentState.indexOf("F")<currentState.indexOf("_")){
                                s=s+"F"+procedure.charAt(i);
                            }
                            else{
                                s="F"+procedure.charAt(i)+s;
                            }
                       }
                        currentState=rawTable.get(map.get(currentState))[dictionary.get(procedure.charAt(i))]=processString(s);//change current state to the content of the cell
                    }
                    else{//if the cell in the dfa table exists then refer to it for next state
                        currentState=processString(rawTable.get(map.get(currentState))[dictionary.get(procedure.charAt(i))]);
                    }
                }
                
                if(processString(currentState).equals("_CFLR")){
                    writer.println("OK");
                    ctr++;
                    //writer.println(procedure);
                }
                else{
                    writer.println("NG");
                }
                
            }
            //prints the dfa table. uncomment to print 
            
            System.out.println("STATE|  N  |  L  |  R  |  C  |");
            for(String k:map.keySet()){
                System.out.print(k+"|");

                for(String str:rawTable.get(map.get(k))){
                    if(str!=null)
                        System.out.print(str+"|");
                    else
                        System.out.print("     |");
                }
                System.out.println("");
            }
                    
            System.out.println(ctr);
            writer.close();
        } catch (FileNotFoundException ex) {
            
        }
    }
    public static String processString(String s){
        if(s.equals("BADMV")||s.equals("EATEN"))
            return s;
        String[] sA=s.split("_");
        String WB="",EB="";
        List<Character> a=new ArrayList();
        for(char c:sA[0].toCharArray())
            a.add(c);
        Collections.sort(a);
        for(char c:a)
            WB+=c;
        List<Character> b=new ArrayList();
        if(sA.length>1){
            for(char c:sA[1].toCharArray())
                b.add(c);
            Collections.sort(b);
            for(char c:b)
                EB+=c;
            }
        return WB+"_"+EB;
    }
    public static boolean eaten(String s){
        String[] sA=s.split("_");
        
        if(sA[0].equals("CR")||sA[0].equals("LR")||sA[0].equals("FL")||sA[0].equals("CF")||sA[0].equals("F")||sA[0].equals("CLR")){//conditions for eating
            return true;
        }
        return false;
    }
}
