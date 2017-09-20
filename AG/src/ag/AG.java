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

   static int limSup = 1;
   static int limInf = -1;
   static int tamanhoPop = 30;
   static int precisao =3;
   static int geracoes = 5;
   static int percentualDeMultacao = 5; //%
   static int tamanhoCromossomo;
   static  Populacao populacao;
   static int otimo =0;
   

  
    public static void main(String[] args) {
        int[] Integerlist;
        double[] fitnessList = new double[tamanhoPop];
        double fitnessMedio = 0;
  
        
        tamanhoCromossomo= (int)calculaTamanho(limSup, limInf);
        
        populacao = new Populacao( tamanhoCromossomo, tamanhoPop);
        
        populacao.gerarIndividuos(limSup, limInf);
        
        //populacao.showCromossomos();
        
        ////////////////////////////////////////////////////////////////////////////////////
        for (int g = 0; g <geracoes ; g++) {
            System.out.println(g);            
        /////////////////////////////////////////////////////////////////////////////////// 
         Integerlist = listaInteiros(populacao);
        
         fitnessList = fitness(getDouble(Integerlist));
       
        //System.out.println("Lista de Fitness:");
        fitnessMedio =0;
        if (g ==0){
        for (int i=0;i<tamanhoPop;i++  ){
           // System.out.println(i+"=("+fitnessList[i]+")");
            fitnessMedio+=fitnessList[i];    
        }  
         System.out.println("FITNESS MEDIO =============("+fitnessMedio/fitnessList.length+")");
        } 
       int [] selecao = selecao(fitnessList); 
       //System.out.println("Lista de individuos selecionados:");
       for (int i=0;i<(tamanhoPop/2);i++  ){
           int x =selecao[i];
           System.out.print(i+"=(");
           for (int j= 0; j<tamanhoCromossomo; j++){
                System.out.print(populacao.cromossomos[x][j]);
           } 
           System.out.println(")");
        }
      // System.out.println("nova geração");
         populacao = cruzamento(selecao, populacao);
         populacao = mutacao(populacao);
      
        }
       // System.out.println("Lista de Fitness 2ª geração:");
         
          fitnessMedio =0;
        for (int i=0;i<tamanhoPop;i++  ){
           System.out.println(i+"=("+fitnessList[i]+")");
            fitnessMedio+=fitnessList[i];
            if (fitnessList[otimo]< fitnessList[i]){
               otimo = i;
            }
            
        }  
         System.out.println("FITNESS MEDIO ª Populacao final =============("+fitnessMedio/tamanhoPop+")");
         
        // System.out.println("Fitness MELHOR INDIVIDUO("+fitnessList[otimo]);
         
         
         
         
        
         
    }
    
    //Calcula tamanho do cromossomo de acordo com o limite inferior e superior
    static double calculaTamanho(int limS, int limI){
        int tamanho;
        int  tamanhoBit=0;
        tamanho  = (limS - limI);
        //calculando potencias de 2 para encontrar quantos bits precisam ser usados para 
        //representar o intervalo
            for (int i = 0; ((Math.pow(10, precisao)) * tamanho) > Math.pow(2, i) ; ++i ){
                tamanhoBit = i;
            }
            System.out.print("Tamanho dos cromossomos:'"+tamanhoBit+"'");  
        return tamanhoBit;
    }
    //Relaciona o vetor de binários com um numero inteiro
    static int[] listaInteiros (Populacao pop){
        
     //System.out.println("calculando inteiros:"); 
     int [] fitnessList = new int[tamanhoPop];
     int [] listaInteiros = new int[tamanhoPop];
     
     for (int i=0; i<tamanhoPop; i++){
         for (int j = (tamanhoCromossomo-1); j>=0;j--){
                 if (pop.cromossomos[i][j] ==1){
                    listaInteiros[i]+=Math.pow(2,j) ;
                 }
            }    
         //System.out.println(listaInteiros[i]);
     }   
    return listaInteiros;
  }
    //Tranforma a lista correpondente de inteiros do array para o verdadeiro valor double que o cromossomo deve representar
    static double[] getDouble ( int[] listaInteiros){
        double[] legendaReal = new double[tamanhoPop];
    
        for (int i =0 ; i<tamanhoPop; i++){
            legendaReal[i] =  + limInf + listaInteiros[i]*(limSup-limInf)/(Math.pow(2,tamanhoCromossomo)-1);
            
            
            //System.out.println("Correspondente:"+legendaReal[i]);
        }
    
    return legendaReal;
    }
    //Recebe os valores reais dos individuos e os aplica na funcao de X^2, o resultado armazenado no array de fitness
    static double[] fitness ( double[] doubleList  ){
       double[] fitnessList = new double[tamanhoPop];
            for (int i = 0; i< tamanhoPop; i++){
            fitnessList[i] = Math.pow(doubleList[i], 2);
            }
       
   return fitnessList;
   }
    //Percorre a lista de fitness e seleciona os individuos por torneio, sendo os vencedores com menor fitness
    static int[] selecao( double[] fitnessList){
     
       int[] retornoIndicesSelecionados = new int[((int)tamanhoPop/2)];
            for (int i = 0; i<(tamanhoPop/2);i+=2){
                if (fitnessList[i]>fitnessList[i+1])
                    retornoIndicesSelecionados[i] = i+1 ;
                if (fitnessList[i]==fitnessList[i+1])
                    retornoIndicesSelecionados[i] = i;
                if (fitnessList[i]<fitnessList[i+1])
                    retornoIndicesSelecionados[i] = i;          
            }    
   
   return retornoIndicesSelecionados;
   }  
    //Percorre os individuos selecionados e envia os pares de individuos para o metodo de cruzamento
    static Populacao cruzamento (int[] selecao, Populacao pop ){
               Populacao novaPopulacao  = new Populacao(tamanhoCromossomo, tamanhoPop);
              for (int i = 0; i <(tamanhoPop/2); i++){
                  if ((i+1)<(tamanhoPop/2))
                        novaPopulacao.cromossomos[i] = reproduce (pop.cromossomos[selecao[i]], pop.cromossomos[selecao[i+1]]);
                }
               for (int i = ((tamanhoPop/2)-1); i < tamanhoPop; i++){
                   for (int j = 0; (j+1) < (tamanhoPop/2); j++ )
                        novaPopulacao.cromossomos[i] = reproduce (pop.cromossomos[selecao[j]], pop.cromossomos[selecao[j+1]]);
                }
           
    return novaPopulacao;
   }
    //Recebe dois individuos, ponto de crossover é a metade do individuo, retorna individuo resultante 
    static public Integer[] reproduce(Integer[] father, Integer[] mother) {
        Integer[] child=new Integer[father.length];
        int crossPoint = (father.length/2); //(int) (Math.random()*father.length);//make a crossover point
            for (int i=0;i<father.length;++i){
                   if (i<=crossPoint)
                           child[i]=mother[i];
                                 else
                                    child[i]=father[i];     
            }
     return child;
    }
    //Executa a mutação da primeira posicao de 4% da populacao
    static Populacao mutacao ( Populacao pop) {
        int [] individuosMutados;
        int percentualMutacao = (int) (tamanhoPop*percentualDeMultacao)/100;
        individuosMutados = new int[percentualMutacao];
        
       
            for (int i = 0; i < percentualMutacao; ){
                
                 if(i==0){
                     Random rand = new Random();
                     individuosMutados[i]=rand.nextInt(tamanhoPop);
                     pop.cromossomos[individuosMutados[i]][0]= 0;
                     i++;
                 }else{
                    if ( individuosMutados[i]!=individuosMutados[i-1]){
                        Random rand = new Random();
                        individuosMutados[i]=rand.nextInt(tamanhoPop);
                        pop.cromossomos[individuosMutados[i]][0]= 0;
                        i++;
                    }
              
             } 
            }
            
//            for (int i =0; i<percentualMutacao; i++){
//                System.out.print(i+":"); 
//                for (int j =0; j<tamanhoCromossomo; j++){
//                     System.out.print( pop.cromossomos[individuosMutados[i]][j]);
//                }
//              System.out.println();  
//            }
     
     return pop;
     }
       
}
   
   
   
   
   

