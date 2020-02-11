import java.util.Scanner;
import java.lang.Object.*;
import java.util.*;


class Largura{
	public static nodes nothing=new nodes();
	public static void bfs(List<nodes> lista, int[] inicial, int[] goal) {
		long start = System.currentTimeMillis();
		int gerados=1;
		nodes ler=new nodes(inicial,nothing,"",0,0,0);
		lista.add(ler);
		nodes atual=new nodes(inicial,nothing,"",0,0,0);
		while(lista.size()!=0) {
			atual=lista.remove(0);
			if(puzzle.testa_igual(atual.vetor,goal)) {
				long tempoFinal= (long)(System.currentTimeMillis());
	    		System.out.printf("Tempo decorrido: %.3f \nSolucao encontrada para profundidade: %d \n", (tempoFinal - start) / 1000d, atual.profundidade);
				System.out.printf("Caminho para a solucao:");
				System.out.println(atual.path);
				System.out.printf("gerados: %d\narmazenados:%d\n", gerados,lista.size());
				return;
			}
			gerados=cria_filhos_list(lista,atual,gerados);
		}
	}
	public static int cria_filhos_list(List<nodes> list, nodes atual, int gerados){
		int blankpos=-1;
		nodes aux=new nodes(atual.vetor,nothing,"",0,0,0);
		for(int i=0;i<16;i++){
				if(atual.vetor[i]==0)
					blankpos=i;
		}
		if(blankpos==0){
			baixo_bfs(blankpos, list, atual);
			direita_bfs(blankpos, list, atual);
			return gerados +=2;

		}

		else if(blankpos==1 || blankpos==2){
			baixo_bfs(blankpos, list, atual);
			direita_bfs(blankpos, list, atual);
			esquerda_bfs(blankpos, list, atual);
			return gerados +=3;

		}
		else if(blankpos==3){
			baixo_bfs(blankpos, list, atual);
			esquerda_bfs(blankpos, list, atual);
			return gerados +=2;			

		}
		else if(blankpos==4 || blankpos==8){
			cima_bfs(blankpos, list, atual);
			baixo_bfs(blankpos, list, atual);
			direita_bfs(blankpos, list, atual);
			return gerados +=3;		
		}
		else if(blankpos==5 || blankpos==6 || blankpos==9 || blankpos==10) {
			cima_bfs(blankpos, list, atual);
			baixo_bfs(blankpos, list, atual);
			direita_bfs(blankpos, list, atual);
			esquerda_bfs(blankpos, list, atual);
			return gerados +=4;	
		}

		else if(blankpos==7 || blankpos==11){
			cima_bfs(blankpos, list, atual);
			baixo_bfs(blankpos, list, atual);
			esquerda_bfs(blankpos, list, atual);
			return gerados +=3;
		}

		else if(blankpos==12){
			cima_bfs(blankpos, list, atual);
			direita_bfs(blankpos, list, atual);
			return gerados +=2;
		}

		else if(blankpos==13 || blankpos==14){
			cima_bfs(blankpos, list, atual);
			direita_bfs(blankpos, list, atual);
			esquerda_bfs(blankpos, list, atual);
			return gerados +=3;
		}

		else if(blankpos==15){
			cima_bfs(blankpos, list, atual);
			esquerda_bfs(blankpos, list, atual);
			return gerados +=2;
		}
		return 0;
	}


	public static void cima_bfs(int blankpos, List<nodes> list, nodes atual) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos-4];
		copia[blankpos-4] = 0;
		nodes aux=new nodes(copia,atual,atual.path.concat(" C"),atual.profundidade+1,0,0);
		list.add(aux);
		atual.nfilhos++;
 	}


	public static void baixo_bfs(int blankpos, List<nodes> list, nodes atual) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos+4];
		copia[blankpos+4] = 0;
		nodes aux=new nodes(copia,atual,atual.path.concat(" B"), atual.profundidade+1,0,0);
		list.add(aux);
		atual.nfilhos++;
	}

	public static void direita_bfs(int blankpos, List<nodes> list, nodes atual) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos+1];
		copia[blankpos+1] = 0;
		nodes aux=new nodes(copia,atual,atual.path.concat(" D"), atual.profundidade+1,0,0);
		list.add(aux);
		atual.nfilhos++;
	}


	public static void esquerda_bfs(int blankpos, List<nodes> list, nodes atual) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos-1];
		copia[blankpos-1] = 0;
		nodes aux=new nodes(copia,atual,atual.path.concat(" E"), atual.profundidade+1,0,0);
		list.add(aux);
		atual.nfilhos++;
	}

}