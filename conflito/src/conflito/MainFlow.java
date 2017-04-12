package conflito;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe principal para a execução do ocntrole de conflitos de seriabilidade
 * 
 * @author Diego G Tome
 * @version 1.0
 * @since 24/03/2016
 */
public class MainFlow {
	/**
	 * Método principal do programa, faz a leitura das operação linha a linha e
	 * verifica se o agendamento já se encontra totalmente em commit
	 * 
	 * @author Diego G Tomé
	 * @return void
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner;

		scanner = new Scanner(new File("/Users/diegogomestome/Desktop/dgtome/teste.txt")).useDelimiter("\\ |\\n");

		Scheduler scheduler = new Scheduler();
		int count = 1;
		while (scanner.hasNext()) {
			String timeStamp = scanner.next();
			String transaction = scanner.next();
			String operation = scanner.next();
			String resource = scanner.next();

			if (scheduler.isCommited()) {
				System.out.println(count + " "
						+ scheduler.getTransactionsList() + " "
						+ scheduler.getSeriability());
				scheduler = new Scheduler();
				scheduler.grafo = new Graph();
				count++;
			}
			scheduler.processNewOperation(timeStamp, transaction, operation,
					resource);

		}
		scanner.close();
		if (scheduler.isCommited()) {
			System.out.println(count + " " + scheduler.getTransactionsList()
					+ " " + scheduler.getSeriability());
			scheduler = new Scheduler();
			scheduler.grafo = new Graph();
			count++;
		}
	}
}
