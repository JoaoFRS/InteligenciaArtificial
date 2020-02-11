import java.util.Scanner;
import java.lang.Object.*;
import java.util.*;

class nodes implements Comparable<nodes>{
		int[] vetor =new int[16];
		nodes pai;
		String path= new String();
		int profundidade;
		int nfilhos;
		int cost;
	public nodes(){

	}

	public nodes(int[] vetor, nodes pai, String path, int profundidade,int nfilhos,int cost) {
		this.vetor=vetor;
		this.pai=pai;
		this.path=path;
		this.profundidade=profundidade;
		this.nfilhos=nfilhos;
	    this.cost=cost;
	}
		public int compareTo(nodes b) {
            if (this.cost < b.cost)
                return -1;
            else if(this.cost == b.cost)
                return 0;
            return 1;
        }
}