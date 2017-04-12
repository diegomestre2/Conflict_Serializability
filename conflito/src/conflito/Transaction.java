package conflito;

import java.util.ArrayList;
import java.util.Collection;
/**Classe do objeto transaction, onde se armazena as informações da transação.
 * @author Diego G Tome
 * @version 1.0
 * @since 24/03/2016
 */
public class Transaction {

	Integer timestamp = 0;
	Integer identifier = 0;
	boolean isCommited = false;
	/**Método que retorna o identificador da transação
     * @author Diego G Tomé
     * @return identifier Integer
     */
	public Integer getIdentifier() {
		return this.identifier;
	}
	/**Método para setar o identificador da transação
     * @author Diego G Tomé
     * @param identifier Integer
     * @return void
     */
	public void setIdentifier(Integer identifier) {
		this.identifier = identifier;
	}
	/**Variável do tipo collection contendo uma coleção de operações concatenadas a seu recurso para a transação
     * @author Diego G Tomé
     * @value operations Collection<String>
     */
	Collection<String> operations = new ArrayList<String>();

	/**Método construtor
     * @author Diego G Tomé
     * @param timeStamp String : sequência de chegada
     * @param identifier String : identificador da transação
     * @param operation String : tipo de operação
     * @param resource String : recurso utilizado
     * @return void
     */
	public Transaction(String timeStamp, String identifier, String operatorion, String resource) {
		this.timestamp = Integer.parseInt(timeStamp);
		this.identifier = Integer.parseInt(identifier);
		this.operations.add(operatorion+ resource);

	}

}
