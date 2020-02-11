import java.util.Scanner;
import java.lang.Object.*;
import java.util.*;

class Astar_manhattan {
	
	public static nodes nothing=new nodes();
	public static void Astar_manhattan(PriorityQueue<nodes> priority, int[] inicial, int[] goal) {
		long start = System.currentTimeMillis();
		int gerados=1;
		nodes ler = new nodes(inicial, nothing, "", 0, 0, heuristica_manhattan(inicial, goal));
		priority.offer(ler);
		nodes atual = priority.peek();
		while (priority.peek() != null) {	
	    	atual = priority.peek(); 
	    	priority.remove();
	    	if (puzzle.testa_igual(atual.vetor, goal)) {
	    		long tempoFinal= (long)(System.currentTimeMillis());
	    		System.out.printf("Tempo decorrido: %.3f \nSolucao encontrada para profundidade: %d \n", (tempoFinal - start) / 1000d, atual.profundidade);
				System.out.printf("Caminho para a solucao:");
				System.out.println(atual.path);
				System.out.printf("gerados: %d\narmazenados:%d\n", gerados,priority.size());
				return;
	    	}
	    	gerados=cria_filhos_priority(priority, atual, goal,gerados);
		}
	}
	public static void cima_star(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos-4];
		copia1[blankpos-4] = 0;
		nodes aux=new nodes(copia1,atual,atual.path.concat(" C"),atual.profundidade+1, 0, 0);
		aux.cost=heuristica_manhattan(aux.vetor, goal)+aux.profundidade;
		priority.offer(aux);
	}

	public static void baixo_star(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos+4];
		copia1[blankpos+4] = 0;
		nodes aux=new nodes(copia1,atual,atual.path.concat(" B"),atual.profundidade+1, 0, 0);
		aux.cost=heuristica_manhattan(aux.vetor, goal)+aux.profundidade;
		priority.offer(aux);
	}

	public static void direita_star(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos+1];
		copia1[blankpos+1] = 0;
		nodes aux=new nodes(copia1,atual,atual.path.concat(" D"),atual.profundidade+1, 0, 0);
		aux.cost=heuristica_manhattan(aux.vetor, goal)+aux.profundidade;
		priority.offer(aux);
	}

	public static void esquerda_star(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos-1];
		copia1[blankpos-1] = 0;
		nodes aux=new nodes(copia1,atual,atual.path.concat(" E"),atual.profundidade+1, 0, 0);
		aux.cost=heuristica_manhattan(aux.vetor, goal)+aux.profundidade;
		priority.offer(aux);
	}

	public static int cria_filhos_priority(PriorityQueue<nodes> priority, nodes atual, int[] goal,int gerados) {
		int blankpos=-1;
		nodes aux=new nodes(atual.vetor,nothing,"",0, 0, atual.cost);
		for(int i=0;i<16;i++){
				if(atual.vetor[i]==0)
					blankpos=i;
		}

		if (blankpos == 0) {
			baixo_star(blankpos, priority, atual, goal);
			direita_star(blankpos, priority, atual, goal);
			return gerados+=2;
		}

		else if (blankpos == 1 || blankpos == 2) {
			baixo_star(blankpos, priority, atual, goal);
			direita_star(blankpos, priority, atual, goal);
			esquerda_star(blankpos, priority, atual, goal);
			return gerados+=3;
		}

		else if (blankpos == 3) {
			baixo_star(blankpos, priority, atual, goal);
			esquerda_star(blankpos, priority, atual, goal);
			return	gerados+=2;
		}

		else if (blankpos == 4 || blankpos == 8) {
			cima_star(blankpos, priority, atual, goal);
			baixo_star(blankpos, priority, atual, goal);
			direita_star(blankpos, priority, atual, goal);
			return gerados+=3;
			
		}

		else if (blankpos == 5 || blankpos == 6 || blankpos == 9 || blankpos == 10) {
			cima_star(blankpos, priority, atual, goal);
			baixo_star(blankpos, priority, atual, goal);
			direita_star(blankpos, priority, atual, goal);
			esquerda_star(blankpos, priority, atual, goal);
			return gerados+=4;
		}

		else if (blankpos == 7 || blankpos == 11) {
			cima_star(blankpos, priority, atual, goal);
			baixo_star(blankpos, priority, atual, goal);
			esquerda_star(blankpos, priority, atual, goal);
			return gerados+=3;
		}

		else if (blankpos == 12) {
			cima_star(blankpos, priority, atual, goal);
			direita_star(blankpos, priority, atual, goal);
			return gerados+=2;
		}

		else if (blankpos == 13 || blankpos == 14) {
			cima_star(blankpos, priority, atual, goal);
			direita_star(blankpos, priority, atual, goal);
			esquerda_star(blankpos, priority, atual, goal);
			return gerados+=3;
		}

		else if (blankpos == 15) {
			cima_star(blankpos, priority, atual, goal);
			esquerda_star(blankpos, priority, atual, goal);
			return gerados+=2;
		}
		return 0;

	}

	public static int heuristica_manhattan(int[] inicial, int[] goal) {
		int[] distanceX = new int[16];
		int[] distanceY = new int[16];	
		int[] iniRow = new int[16];
		int[] goalRow = new int[16];
		int distance = 0;
		for (int i=0; i<16; i++) {
			iniRow[i] = (15-i)/4;
			for (int j=0; j<16; j++) {
				if (inicial[i] == goal[j]) {
					distanceX[i] = (i%4) - (j%4);
					if (distanceX[i] < 0) {
						distanceX[i] = -distanceX[i];
					}
					goalRow[j] = (15-j)/4;
					distanceY[i] = iniRow[i] - goalRow[j];
					if (distanceY[i] < 0) {
						distanceY[i] = -distanceY[i];
					}
					break;

				}
			
			}
			distance += (distanceX[i] + distanceY[i]);
		}

		return distance;
	}
}