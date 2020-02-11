import java.util.Scanner;
import java.lang.Object.*;
import java.util.*;

class Gulosa_manhattan {

	public static nodes nothing=new nodes();
	public static void greedy_manhattan(PriorityQueue<nodes> priority, int[] inicial, int[] goal) {
		long start = System.currentTimeMillis();
		int gerados=0;
		ArrayList<String> visitados = new ArrayList<String>(); 
		nodes ler = new nodes(inicial, nothing, "", 0, 0, heuristica_manhattan(inicial, goal));
		priority.offer(ler);
		visitados.add(Arrays.toString(ler.vetor));
		nodes atual = ler;
		while (priority.peek() != null) {	
	    	atual = priority.peek(); 
	    	visitados.add(Arrays.toString(atual.vetor));
	    	priority.poll();
	    	if (puzzle.testa_igual(atual.vetor, goal)) {
	    		long tempoFinal= (long)(System.currentTimeMillis());
	    		System.out.printf("Tempo decorrido: %.3f \nSolucao encontrada para profundidade: %d \n", (tempoFinal - start) / 1000d, atual.profundidade);
	    		System.out.printf ("profundidade:%d \n",atual.profundidade);
				System.out.printf("Caminho para a solucao:");
				System.out.println(atual.path);
				System.out.printf("gerados: %d\narmazenados:%d\n", gerados,priority.size());
				return;
	    	}
	    	gerados=cria_filhos_priority(priority, atual, goal,gerados,visitados);

		}
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
					distanceX[i] = Math.abs((i%4) - (j%4));
					goalRow[j] = (15-j)/4;
					distanceY[i] =Math.abs(iniRow[i] - goalRow[j]);
					break;

				}
			
			}
			distance += (distanceX[i] + distanceY[i]);
		}

		return distance;
	}

	public static int cima_greedy(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal,int gerados, ArrayList<String> visitados) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos-4];
		copia1[blankpos-4] = 0;
		if (visitados.contains(Arrays.toString(copia1))==false) {
			nodes aux=new nodes(copia1,atual,atual.path.concat(" C"),atual.profundidade+1, 0, 0);
			aux.cost=heuristica_manhattan(aux.vetor, goal);
			priority.offer(aux);
			return gerados+=1;
		}
		return gerados;
	}

	public static int baixo_greedy(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal, int gerados, ArrayList<String> visitados) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos+4];
		copia1[blankpos+4] = 0;
		if (visitados.contains(Arrays.toString(copia1))==false) {
			nodes aux=new nodes(copia1,atual,atual.path.concat(" B"),atual.profundidade+1, 0, 0);
			aux.cost=heuristica_manhattan(aux.vetor, goal);
			priority.offer(aux);
			return gerados+=1;
		}
		return gerados;
	}

	public static int direita_greedy(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal,int gerados, ArrayList<String> visitados) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos+1];
		copia1[blankpos+1] = 0;
		if (visitados.contains(Arrays.toString(copia1))==false) {
			nodes aux=new nodes(copia1,atual,atual.path.concat(" D"),atual.profundidade+1, 0, 0);
			aux.cost=heuristica_manhattan(aux.vetor, goal);
			priority.offer(aux);
			return gerados+=1;
		}
		return gerados;
	}

	public static int esquerda_greedy(int blankpos, PriorityQueue<nodes> priority, nodes atual, int[] goal,int gerados, ArrayList<String> visitados) {
		int[] copia1 = new int[16];
		for (int i=0; i<16; i++) {
			copia1[i] = atual.vetor[i];
		}
		copia1[blankpos] = copia1[blankpos-1];
		copia1[blankpos-1] = 0;
		if (visitados.contains(Arrays.toString(copia1))==false) {
			nodes aux=new nodes(copia1,atual,atual.path.concat(" E"),atual.profundidade+1, 0, 0);
			aux.cost=heuristica_manhattan(aux.vetor, goal);
			priority.offer(aux);
			return gerados+=1;
		}
		return gerados;
	}	

	public static int cria_filhos_priority(PriorityQueue<nodes> priority, nodes atual, int[] goal,int gerados, ArrayList<String> visitados) {
		int blankpos=-1;
		nodes aux=new nodes(atual.vetor,nothing,"",0, 0, atual.cost);
		for(int i=0;i<16;i++){
			if(atual.vetor[i]==0)
				blankpos=i;
		}

		if (blankpos == 0) {
			gerados=baixo_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=direita_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 1 || blankpos == 2) {
			gerados=baixo_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=direita_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=esquerda_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 3) {
			gerados=baixo_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=esquerda_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 4 || blankpos == 8) {
			gerados=cima_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=baixo_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=direita_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 5 || blankpos == 6 || blankpos == 9 || blankpos == 10) {
			gerados=cima_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=baixo_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=direita_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=esquerda_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 7 || blankpos == 11) {
			gerados=cima_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=baixo_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=esquerda_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 12) {
			gerados=cima_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=direita_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 13 || blankpos == 14) {
			gerados=cima_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=direita_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=esquerda_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}

		else if (blankpos == 15) {
			gerados=cima_greedy(blankpos, priority, atual, goal,gerados,visitados);
			gerados=esquerda_greedy(blankpos, priority, atual, goal,gerados,visitados);
			return gerados;
		}
		return 0;
	}
}