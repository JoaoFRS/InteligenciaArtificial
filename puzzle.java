import java.util.Scanner;
import java.lang.Object.*;
import java.util.*;


public class puzzle {

	//Verifica o numero de inversoes e onde esta o espaco em branco
	//para ver se ha caminho de uma configuracao inicial para uma final
	public static boolean testa_paridade(int[] v){
		int inversion=0;
		int blankRow=0;

		for (int i=0; i<16;i++){
			if (v[i]!=0){
				for (int j=i+1; j<16;j++){
					if(v[j]<v[i] && v[j]!=0)
						inversion++;
				}

			}
			else
				blankRow=(15-i)/4;


		}
		if ((inversion%2==0)==(blankRow%2==1)) return true;
		else return false;

	}
	//testa se dois vetores são iguais
	public static boolean testa_igual(int[] v, int[] z){
		for (int i=0;i<16;i++){
			if(v[i]!=z[i]) return false;
		}
		return true;

	}

	public static void main(String[] args)  {
		List<nodes> lista = new LinkedList<nodes>();
		Stack<nodes> stack = new Stack<nodes>();
		PriorityQueue<nodes> pstack =new  PriorityQueue<nodes>();
		int gerados=1;
		int[] inicial = new int[16];
		int[] goal = new int[16];
		Scanner sc = new Scanner(System.in);
		System.out.printf("Bem vindo ao jogo dos 15!\nInsira a configuracao inicial:\n");
		for (int i=0;i<16; i++) {
	 		inicial[i]=sc.nextInt();
		}
		System.out.printf("Insira a configuracao final:\n");
		for (int i=0;i<16; i++) {
			goal[i]=sc.nextInt();
		}
		if(testa_paridade(inicial) != testa_paridade(goal)) {
			System.out.println("Impossivel de chegar a solucao ");
			System.exit(1);
		}
		if(testa_igual(inicial,goal)){
			System.out.println("Configuracao da matriz inicial correspondente a matriz final");
			return;
		}
		System.out.printf("Escolha o numero correspondente ao tipo de pesquisa a ser utilizada\n \n 1-Pesquisa em largura\n 2-Pesquisa em profundidade\n 3-Pesquisa em profundidade iterativa\n 4-Pesquisa gulosa por contagem de pecas fora do lugar\n 5-Pesquisa gulosa por calculo da manhattan distance\n 6- Pesquisa A* por contagem de pecas fora do lugar\n 7- Pesquisa A* por calculo da manhattan distance\n\nA sua escolha :\n");
		int sw;
		sw=sc.nextInt();
		switch(sw){
		case 1:{
					Largura.bfs(lista, inicial, goal);
					break;
				}
		case 2:{
					Profundidade.dfs(stack, inicial, goal);
					break;
				}
		case 3:{
					System.out.println("Insira a profundidade pretendida");
					int prof=sc.nextInt();
					long start = System.currentTimeMillis();
					for (int i=0;i<prof;i++){
						boolean aux=ProfundidadeIterativa.dfsi(stack,inicial, goal, i, gerados);
						if (aux==true){
							long tempoFinal= (long)(System.currentTimeMillis());
	    						System.out.printf("Tempo decorrido: %.3f \n", (tempoFinal - start) / 1000d);
						        break;}
		
					}
					break;
				}
		case 4:{
					Gulosa_fora.greedy_fora(pstack,inicial,goal);
					break;

				}
		case 5:{	
					Gulosa_manhattan.greedy_manhattan(pstack,inicial,goal);
					break;
				}
		case 6:{
					Astar_fora.Astar_fora(pstack, inicial, goal);
					break;
				}
		case 7:{
					Astar_manhattan.Astar_manhattan(pstack, inicial, goal);
					break;
				}
		default:{
				 break;
				}
		}

	}

}
