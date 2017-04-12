package conflito;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**Classe do objeto scheduler, que controla os agendamentos e verifica a seriabilidade
 * @author Diego G Tome
 * @version 1.0
 * @since 24/03/2016
 * @attribute transactionsMap HashMap<Integer, Transaction> : HashMap contendo uma 
 * chave com o identificador da transação para cada transação do agendamento
 * @attribute percArv List<Integer> : Lista de inteiros contendo o percurso do grafo
 * @attribute visitados boolean[] : vetor que contém os vétices visitados do grafo
 * @attribute grafo Graph : objeto da classe Graph
 */
public class Scheduler {
	HashMap<Integer, Transaction> transactionsMap = new HashMap<Integer, Transaction>();
	List<Integer> percArv = new ArrayList<Integer>();
	boolean visitados[] ;
	public Graph grafo = new Graph();
	
	/**Método para verificar se todas as transações do agendamento estão comitadas
     * @author Diego G Tomé
     * @return boolean
     */
	public boolean isCommited() {
		boolean result = false;
		int numberOfCommits = 0;
		Collection<Transaction> transactions = transactionsMap.values();
		for (Transaction transaction : transactions) {
			if (transaction.isCommited) {
				numberOfCommits++;
			}
		}
		if (transactions.size() == numberOfCommits && numberOfCommits > 0) {
			result = true;
		}
		return result;
	}


	/**Método que retorna uma String com as transações do agendamento
     * @author Diego G Tomé
     * @return String
     */
	public String getTransactionsList() {
		String list = "";
		Collection<Transaction> transactions = this.transactionsMap.values();
		for (Transaction transaction : transactions) {
			list = list.concat(transaction.getIdentifier() + ",");
		}
		list = list.substring(0, list.length()-1);
		return list;
	}

	/**Método para solicitar a verificação da seriabilidade do agendamento
     * @author Diego G Tomé
     * @return String com a seriabilidade SIM ou NAO
     */
	public String getSeriability() {
		if (hasCicle(this.grafo, 0)) {
			return "NAO";
		}
		return "SIM";
	}

	/**Método para verificar a seriabilidade do agendamento através de ciclos no grafo
	 * percorre a matriz de adjacência verificando todos os percursos por vétice
	 * em seguida adiciona true no vetor visitados para cada nó visitado no percurso
	 * caso se repita, este tem ciclo
     * @author Diego G Tomé
     * @param identifier Integer
     * @return void
     */
	public boolean hasCicle(Graph grafo, int raiz) {
		percArv.add(raiz);
		visitados = new boolean[grafo.listAdj.size() +1];
		visitados[raiz] = true;
		Integer[][] matrizAdj = grafo.getMatriz();
		for (int i = 0; i < grafo.listAdj.size(); i++) {
			if (matrizAdj[raiz][i] != 0 && visitados[i] == false) {
				hasCicle(grafo, i);
				percArv.add(raiz);
			} else if (visitados[i] == true) {
				return true;
			}
		}
		return false;
	}

	/**Método para processar as operações de uma transação
     * @author Diego G Tomé
     * @param timeStamp String 
     * @param transactionIdentifier String
     * @param operation String
     * @param resource String
     * @return void
     */
	public void processNewOperation(String timeStamp,
			String transactionIdentifier, String operation, String resource) {
		Integer transactionIdentifierI = Integer
				.parseInt(transactionIdentifier);

		if (operation.equals("C")
				&& transactionsMap.containsKey(transactionIdentifierI)) {
			transactionsMap.get(transactionIdentifierI).isCommited = true;

		} else if (transactionsMap.isEmpty()) {
			transactionsMap.put(transactionIdentifierI, new Transaction(
					timeStamp, transactionIdentifier, operation, resource));
		} else {
			Collection<Transaction> transactions = this.transactionsMap
					.values();
			for (Transaction transaction : transactions) {
				Iterator<String> listOfOperations = transaction.operations
						.iterator();
				while (listOfOperations.hasNext()) {
					String operationTest = listOfOperations.next()
							+ (operation + resource);
					if (!transaction.isCommited && !transaction.getIdentifier().equals(
							transactionIdentifierI)
							&& (operationTest.equals("R" + resource + "W"
									+ resource)
									|| operationTest.equals("W" + resource
											+ "R" + resource) || operationTest
										.equals("W" + resource + "W" + resource))) {
						
						grafo.insertEdge(transaction.getIdentifier(),
								transactionIdentifierI);
						
						break;

					}
				}

			}
			if (transactionsMap.containsKey(transactionIdentifierI)) {
				transactionsMap.get(transactionIdentifierI).operations
						.add(operation + resource);
			} else {
				transactionsMap.put(transactionIdentifierI, new Transaction(
						timeStamp, transactionIdentifier, operation, resource));
			}
		}
	}
}
