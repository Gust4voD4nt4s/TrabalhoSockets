import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class Servidor extends Thread {

	private Socket conexao;
	//Cria os Vetores de cada assunto
	private static Vector<DataOutputStream> vetEconomia = new Vector<DataOutputStream>();
	private static Vector<DataOutputStream> vetEntretenimento = new Vector<DataOutputStream>();
	private static Vector<DataOutputStream> vetTecnologia = new Vector<DataOutputStream>();

	public Servidor(Socket s) {
		conexao = s;
	}

	public void run() {
		String nome;
		String msg_recebida; // lida do cliente
		String msg_enviada; // enviada ao cliente
		String assunto;

		try {
			
			// cria os streams de entrada e saida dos clientes
			BufferedReader entrada_cliente = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			DataOutputStream saida_cliente = new DataOutputStream(conexao.getOutputStream());

			// Recebe nome de cliente
			nome = entrada_cliente.readLine();
			// Recebe assunto de cliente
			assunto = entrada_cliente.readLine();

			int i;
			Vector<DataOutputStream> vet = null;
			
			// Comprar se assunto é igual a 1
			if (assunto.equals("1")) {
				// Add o cliente no vetor
				vetEconomia.add(saida_cliente);
				vet = vetEconomia;
				assunto = "Economia";

			// Comprar se assunto é igual a 2
			} else if (assunto.equals("2")) {
				// Add o cliente no vetor
				vetEntretenimento.add(saida_cliente);
				vet = vetEntretenimento;
				assunto = "Entretenimento";
			// Comprar se assunto é igual a 3
			} else if (assunto.equals("3")) {
				// Add o cliente no vetor
				vetTecnologia.add(saida_cliente);
				vet = vetTecnologia;
				assunto = "Tecnologia";
			}

			
			i = 0;
			// Manda menssagem de que cliente entrou em tal assunto para os clientes especificos de cada assunto
			while (i < vet.size()) {
				vet.get(i).writeBytes(nome + " entrou no chat de " + assunto + "\n");
				i++;
			}

			//Recebe a menssagem
			msg_recebida = entrada_cliente.readLine();

			i = 0;
			//Compara se menssagem é diferente de sair
			while (msg_recebida != null && !(msg_recebida.trim().equals("")) && !(msg_recebida.equals("Sair"))) {

				System.out.println(nome + ": " + msg_recebida + "\n");

				msg_enviada = nome + " Data e Hora: " + getDataTime() + " diz --> " + msg_recebida + "\n";

				i = 0;
				// Compara se i é menor que o tamanho do vetor
				while (i < vet.size()) {
					// Compara se o cliente que ta no vetor na posição i é diferente de saida_cliente
					if (vet.get(i) != saida_cliente) {
						// Manda menssagem para os clientes especificos de cada assunto
						vet.get(i).writeBytes(msg_enviada);
					}
					i++;
				}
				
				// Recebe menssagem de novo
				msg_recebida = entrada_cliente.readLine();

			}
			i = 0;
			// Compara se i é menor que o tamanho do vetor
			while (i < vet.size()) {
				// Manda menssagem para os clientes especificos de cada assunto que tal cliente saiu do chat
				vet.get(i).writeBytes(nome + " saiu do chat de" + assunto + " Data e hora: " + getDataTime() + "\n");
				i++;
			}
			
			i = 0;
			// Compara se i é menor que o tamanho do vetor
			while (i < vet.size()) {
				// Compara se o cliente que ta no vetor na posição i é igual de saida_cliente
				if (vet.get(i) == saida_cliente) {
					// Remove Cliente do vetor
					vet.remove(vet.get(i));
					System.out.println(nome + " desconectou");
				}
			i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Cria a data e sua formatação
	private String getDataTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date date = new Date(0);
		return dateFormat.format(date);
	}

	public static void main(String[] args) throws IOException {

		// cria socket de comunicação com os clientes na porta 8657
		ServerSocket servidor = new ServerSocket(8657);
		// espera msg de algum cliente e trata
		while (true) {
			// espera conexão de algum cliente
			System.out.println("Esperando cliente se conectar...");
			Socket cx = servidor.accept();
			Thread t = new Servidor(cx);
			t.start();
			System.out.println("Cliente conectado!");
		}
	}
}