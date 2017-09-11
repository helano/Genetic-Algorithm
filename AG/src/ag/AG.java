/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ag;

import java.util.Random;

/**
 *
 * @author Mr Robot
 */
public class AG {

   static int limSup = 10;
   static int limInf = -10;
   static int tamanhoPop = 50;
   static int precisao =3;
   static int tamanhoCromossomo;
   static  Populacao populacao;
  
    public static void main(String[] args) {
        tamanhoCromossomo= (int)calculaTamanho(limSup, limInf);
        
        populacao = new Populacao( tamanhoCromossomo, tamanhoPop);
        
        populacao.gerarIndividuos(limSup, limInf);
        
        populacao.showCromossomos();
        
        int[] Integerlist = listaInteiros(populacao);
        
        double[] fitnessList = fitness (getDouble(Integerlist));
       
        System.out.println("Lista de Fitness:");
        
        for (int i=0;i<tamanhoPop;i++  ){
            System.out.println(i+"=("+fitnessList[i]+")");
        }  
        
       int [] selecao = selecao(fitnessList);

       
       System.out.println("Lista de individuos selecionados:");
       for (int i=0;i<(tamanhoPop/2);i++  ){
           int x =selecao[i];
           System.out.print(i+"=(");
           for (int j= 0; j<tamanhoCromossomo; j++){
                System.out.print(populacao.cromossomos[x][j]);
           } 
           System.out.println(")");
        }
       System.out.println("nova geração");
        Populacao populacao2 = cruzamento(selecao, populacao);
       
        populacao2.showCromossomos();
    }
    
    //calcula tamanho do cromossomo de acordo com o limite inferior e superior
    static double calculaTamanho(int limS, int limI){
        int tamanho;
        int  tamanhoBit=0;
        tamanho  = (limS - limI) ;
        //calculando potencias de 2 para encontrar quantos bits precisam ser usados para 
        //representar o intervalo
            for (int i = 0; tamanho * precisao> Math.pow(2, i) ; ++i ){
                tamanhoBit = i;
            }
            System.out.print("Tamanho dos cromossomos:'"+tamanhoBit+"'");  
        return tamanhoBit;
    }
    
    static int[] listaInteiros (Populacao pop){
        
     System.out.println("calculando inteiros:"); 
     int [] fitnessList = new int[tamanhoPop];
     
     int [] listaInteiros = new int[tamanhoPop];
     
     for (int i=0; i<tamanhoPop; i++){
         for (int j = (tamanhoCromossomo-1); j>=0;j--){
                 if (pop.cromossomos[i][j] ==1){
                    listaInteiros[i]+=Math.pow(2,j) ;
                 }
            }    
         System.out.println(listaInteiros[i]);
     }   
    return listaInteiros;
  }
    
    //tranforma a lista correpondente de inteiros do array para o verdadeiro valor double que o cromossomo deve representar
    static double[] getDouble ( int[] listaInteiros){
        double[] legendaReal = new double[tamanhoPop];
    
        for (int i =0 ; i<tamanhoPop; i++){
            legendaReal[i] =  + limInf + listaInteiros[i]*(limSup-limInf)/(Math.pow(2,tamanhoCromossomo)-1);
            System.out.println("Correspondente:"+legendaReal[i]);
        }
    
    return legendaReal;
    }
    
   static double[] fitness ( double[] doubleList  ){
       double[] fitnessList = new double[tamanhoPop];
            for (int i = 0; i< tamanhoPop; i++){
            fitnessList[i] = Math.pow(doubleList[i], 2);
            }
       
   return fitnessList;
   }
   
   static int[] selecao( double[] fitnessList){
       int individuo1=0, individuo2=0;
       int[] retornoIndicesSelecionados = new int[((int)tamanhoPop/2)];
        
       
        int numero;
        
        for (int i = 0; i<(tamanhoPop/2);i++){
            Random rand = new Random();
             Random rand2 = new Random();
            numero = rand.nextInt(tamanhoPop);
                if((0<numero)&& (numero<tamanhoCromossomo)){
                    individuo1=numero;
                }
             numero = rand2.nextInt(tamanhoPop);
                if((0<numero)&& (numero<tamanhoCromossomo)){
                    individuo2=numero;   
                }
            if (fitnessList[individuo1]>fitnessList[individuo2])
                retornoIndicesSelecionados[i] = individuo2 ;
            if (fitnessList[individuo1]==fitnessList[individuo2])
                retornoIndicesSelecionados[i] = individuo1;
            if (fitnessList[individuo1]<fitnessList[individuo2])
                retornoIndicesSelecionados[i] = individuo1;          
        }    
   
   return retornoIndicesSelecionados;
   } 
   
   
   //Revisar NÃO ESTA FUNCIONANDO 
   static Populacao cruzamento (int[] selecao, Populacao pop ){
               Populacao novaPopulacao  = new Populacao(tamanhoCromossomo, tamanhoPop);
              for (int i = 0; i <(tamanhoPop/2); i++){
                  if ((i+1)<(tamanhoPop/2))
                    novaPopulacao.cromossomos[i] = reproduce (pop.cromossomos[selecao[i]], pop.cromossomos[selecao[i+1]]);
              }
               for (int i = ((tamanhoPop/2)-1); i < tamanhoPop; i++){
                   for (int j = 0; j < (tamanhoPop/2); j++ )
                    novaPopulacao.cromossomos[i] = pop.cromossomos[selecao[j]];
              }
           
    return novaPopulacao;
   }
   
   
   
                            static public Integer[] reproduce(Integer[] father, Integer[] mother) {
                                Integer[] child=new Integer[father.length];
                                int crossPoint = (int) (Math.random()*father.length);//make a crossover point
                                for (int i=0;i<father.length;++i)
                                {
                                  if (i<crossPoint)
                                    child[i]=father[i];
                                  else
                                    child[i]=mother[i];
                                }
                                return child;
                         }
       
   }
   
   
   
   
   

