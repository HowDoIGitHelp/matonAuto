/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package automata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rub account
 */
public class MP1 {

    /**
     * @param args the command line arguments
     */
    ArrayList<Instruction> instructions;
    int[] arr;
    public static void main(String[] args) throws UnsupportedEncodingException{
        MP1 a=new MP1();
        a.instructions=new ArrayList<Instruction>();//all instructions are stored here
        try {
            PrintWriter writer = new PrintWriter("MP1.out", "UTF-8");
            Scanner sc = new Scanner(new File("MP1.in"));
            String[] rawArray=sc.nextLine().split(" ");
            a.arr=new int[rawArray.length];//array where values are stored
            for(int i=0;i<rawArray.length;i++)
                a.arr[i]=Integer.valueOf(rawArray[i]);
            while(sc.hasNext()){
                //all this for storing the instructions in the list
                
                String[] splitString = sc.nextLine().split(" ");
                
                if(splitString.length==4)
                    a.instructions.add(new Instruction(splitString[0].toCharArray()[0],Integer.valueOf(splitString[1]),Integer.valueOf(splitString[2]),Integer.valueOf(splitString[3]),a.instructions.size()+1));
                else if(splitString.length==3)
                    a.instructions.add(new Instruction(splitString[0].toCharArray()[0],Integer.valueOf(splitString[1]),Integer.valueOf(splitString[2]),0,a.instructions.size()+1));
                else if(splitString.length==2)
                    a.instructions.add(new Instruction(splitString[0].toCharArray()[0],Integer.valueOf(splitString[1]),0,0,a.instructions.size()+1));
            }
            
            int i=1;
            while(i<a.instructions.size()+1){
                //System.out.print(i+" | ");
                for(int num:a.arr)
                    writer.print(num+" ");
                writer.println("");
                i=a.solve(i);
                
            }
            for(int num:a.arr)
                    writer.print(num+" ");
            writer.close();
            } catch (FileNotFoundException ex) {
            Logger.getLogger(MP1.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    int solve(int c){//where the instructions procedure is executed. Changes in the array occur here. It returns the next instruction
        if(instructions.get(c-1).type=='S'){
            arr[instructions.get(c-1).operandA]++;
            //System.out.println("add one to index"+instructions.get(c-1).operandA);
            return c+1;//for all instructions other than 'J' it returns c+1 the next instruction in the list
        }
        else if(instructions.get(c-1).type=='Z'){
            arr[instructions.get(c-1).operandA]=0;
            //System.out.println("set index"+instructions.get(c-1).operandA+"to 0");
            return c+1;
        }
        else if(instructions.get(c-1).type=='C'){
            arr[instructions.get(c-1).operandB]=arr[instructions.get(c-1).operandA];
            //System.out.println("copy index"+instructions.get(c-1).operandA+" to index"+instructions.get(c-1).operandB);
            return c+1;
        }
        else{
            if(arr[instructions.get(c-1).operandB]==arr[instructions.get(c-1).operandA]){
                //System.out.println("index"+instructions.get(c-1).operandA+" and index"+instructions.get(c-1).operandB+" are equal jump to "+instructions.get(c-1).nextInstruction);
                return instructions.get(c-1).nextInstruction; //if equality holds true then then the next instruction to be executed will be the indicated by the 'J' instruction
            }
            else{
                //System.out.println("index"+instructions.get(c-1).operandA+" and index"+instructions.get(c-1).operandB+" are not equal");
                return c+1;
            }
        }
        
    }
}
class Instruction { //represents a line of code
    
    char type; //represented by the character leading the instruction, denotes the type
    int index; //the instructions index
    int operandA; //first operand
    int operandB; //second operand empty if the instruction uses only one operand
    int nextInstruction; //represents the next instruction. this is used in instruction 'J' by default the value is index+1
    Instruction(char t, int a,int b,int n,int i){
   
        type=t;
        operandA=a;
        operandB=b;
        index=i;
        if(t=='J')
            nextInstruction=n;
        else
            nextInstruction=i+1;
        //System.out.println(i+"|"+t+" "+a+" "+b+" "+n);
    }
    
}