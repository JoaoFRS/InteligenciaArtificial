import java.util.Scanner;
import java.lang.Object.*;
import java.util.*;

class ProfundidadeIterativa {

	public static nodes nothing=new nodes();
	public static boolean dfsi(Stack<nodes> stack, int[] inicial, int[] goal,int profundidadepedida, int gerados) {
		ArrayList<String> visitados = new ArrayList<String>(); //array que contém todos as configuracoes que ja foram criadas
		nodes ler = new nodes(inicial,nothing, "",0,0,0);
		stack.push(ler);
		visitados.add(Arrays.toString(ler.vetor));
		nodes atual = new nodes(inicial,nothing, "",0,0,0);
		while (!stack.empty()) {
			atual=stack.pop();
			visitados.add(Arrays.toString(atual.vetor));
			if(puzzle.testa_igual(atual.vetor,goal)) {
				System.out.printf("Solucao encontrada para profundidade: %d \n",atual.profundidade);
				System.out.printf("Caminho para a solucao:");
				System.out.println(atual.path);
				System.out.printf("gerados: %d\narmazenados:%d\n", gerados,stack.size());
				return true;
			}
			gerados=cria_filhos_stack(stack, atual, visitados,profundidadepedida, gerados);
			if(atual.nfilhos==0) { 
				visitados.remove(Arrays.toString(atual.vetor));
				atual.pai.nfilhos--;
			}

			while(atual.pai!=nothing) {
				atual=atual.pai;
				if(atual.nfilhos==0) {
					visitados.remove(Arrays.toString(atual.vetor));
					atual.pai.nfilhos--;
				}
			}
		}
		return false;
	}

	public static int cria_filhos_stack(Stack<nodes> stack, nodes atual, ArrayList<String> visitados,int profundidadepedida, int gerados) {
       	if(atual.profundidade>profundidadepedida) return gerados;
		int blankpos=-1;
		nodes aux=new nodes(atual.vetor,atual,"",0,0,0);
		for(int i=0;i<16;i++) {
			if(atual.vetor[i]==0)
				blankpos=i;
		}
		
		//canto superior esquerdo
		if(blankpos==0){
			gerados=baixo_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=direita_dfs(blankpos, stack, atual, visitados, gerados);
			return gerados;	

		}

		else if(blankpos==1 || blankpos==2){
			gerados=baixo_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=direita_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=esquerda_dfs(blankpos, stack, atual, visitados, gerados);
			return gerados;			

		}
		else if(blankpos==3){
			gerados=baixo_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=esquerda_dfs(blankpos, stack, atual, visitados, gerados);
			return gerados;			

		}
		else if(blankpos==4 || blankpos==8){
			gerados=cima_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=baixo_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=direita_dfs(blankpos, stack, atual, visitados, gerados);		
			return gerados;
		}
		else if(blankpos==5 || blankpos==6 || blankpos==9 || blankpos==10) {
			gerados=cima_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=baixo_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=direita_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=esquerda_dfs(blankpos, stack, atual, visitados, gerados);	
			return gerados;
		}

		else if(blankpos==7 || blankpos==11){
			gerados=cima_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=baixo_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=esquerda_dfs(blankpos, stack, atual, visitados, gerados);
			return gerados;
		}

		else if(blankpos==12){
			gerados=cima_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=direita_dfs(blankpos, stack, atual, visitados, gerados);
			return gerados;
		}

		else if(blankpos==13 || blankpos==14){
			gerados=cima_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=direita_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=esquerda_dfs(blankpos, stack, atual, visitados, gerados);
			return gerados;
		}

		else if(blankpos==15){
			gerados=cima_dfs(blankpos, stack, atual, visitados, gerados);
			gerados=esquerda_dfs(blankpos, stack, atual, visitados, gerados);
			return gerados;
		}
		return 0;
	}

	public static int cima_dfs(int blankpos, Stack<nodes> stack, nodes atual, ArrayList<String> visitados, int gerados) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos-4];
		copia[blankpos-4] = 0;
		if (visitados.contains(Arrays.toString(copia))==false) {
			nodes aux=new nodes(copia,atual,atual.path.concat(" C"),atual.profundidade+1,0,0);
			stack.push(aux);
			atual.nfilhos++;
			return gerados+=1;
        }
        return gerados;
	}

	public static int baixo_dfs(int blankpos, Stack<nodes> stack, nodes atual, ArrayList<String> visitados, int gerados) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos+4];
		copia[blankpos+4] = 0;
		if (visitados.contains(Arrays.toString(copia))==false) {
			nodes aux=new nodes(copia,atual,atual.path.concat(" B"), atual.profundidade+1,0,0);
			stack.push(aux);
			atual.nfilhos++;
			return gerados+=1;
        }
        return gerados;
	}


	public static int direita_dfs(int blankpos, Stack<nodes> stack, nodes atual, ArrayList<String> visitados, int gerados) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos+1];
		copia[blankpos+1] = 0;
		if (visitados.contains(Arrays.toString(copia))==false) {
			nodes aux=new nodes(copia,atual,atual.path.concat(" D"), atual.profundidade+1,0,0);
			stack.push(aux);
			atual.nfilhos++;
			return gerados+=1;
        }
        return gerados;
	}



	public static int esquerda_dfs(int blankpos, Stack<nodes> stack, nodes atual, ArrayList<String> visitados, int gerados) {
		int[] copia = new int[16];
		for (int i=0; i<16; i++) {
			copia[i] = atual.vetor[i];;
		}
		copia[blankpos] = copia[blankpos-1];
		copia[blankpos-1] = 0;
		if (visitados.contains(Arrays.toString(copia))==false) {
			nodes aux=new nodes(copia,atual,atual.path.concat(" E"), atual.profundidade+1,0,0);
			stack.push(aux);
			atual.nfilhos++;
			return gerados+=1;
        }
        return gerados;
	}
}
