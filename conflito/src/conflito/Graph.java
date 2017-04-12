package conflito;

import java.util.HashMap;
/**Classe classe do objeto graph que contém a matriz e lista de adjacência para verificação de ciclo
 * @author Diego G Tome
 * @version 1.0
 * @since 24/03/2016
 */
public class Graph {

	public int numVertex;

	Integer[][] matrixAdj = null;
	HashMap<Integer,Integer> listAdj = new HashMap<Integer, Integer>();
	/**Método para a inserção de uma aresta na lista de adjacência
     * @author Diego G Tomé
     * @param  vértice 1 int
     * @param  vértice 2 int
     * @return void
     */
	public void insertEdge(int v1, int v2) {
		this.listAdj.put(v1, v2);
	}
	/**Método para criação da matriz de adjacência desde sua lista de adjacência e a retorna para o algoritmo de verificação de ciclos
     * @author Diego G Tomé
     * @return matrizAdj Integer[][]
     */
	public Integer[][] getMatriz() {
		matrixAdj = new Integer[listAdj.size()][listAdj.size()];
		for (int i = 0; i < listAdj.size(); i++) {
			for (int j = 0; j < listAdj.size(); j++) {
				if (listAdj.containsKey(i) && listAdj.get(i).equals(j)) {
					matrixAdj[i][j] = 1;
				} else {
					matrixAdj[i][j] = 0;
				}
			}
		}
		return matrixAdj;
	}

}
