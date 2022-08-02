import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Clientes extends Thread {
	
	private Socket conexao;
	
	public Clientes(Socket s) {
		conexao = s;
	}
	
	public static void main(String[] args) throws IOException {
		String nome_cliente; // nome do cliente
		String assunto;
		String msg_digitada; // mensagem digitada
		System.out.println("Cliente Ativo!");
		// cria o stream do teclado
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		// solicita um nome para o cliente
		System.out.println("Informe o nome do cliente:");
		nome_cliente = teclado.readLine();
		// cria o socket de acesso ao server hostname na porta 8657
		Socket cliente = new Socket("localhost", 8657);
		
		// cria o stream de saida do servidor
		DataOutputStream saida_servidor = new DataOutputStream(cliente.getOutputStream());
		
		saida_servidor.writeBytes(nome_cliente + '\n');
		// solicita o assunto que o cliente deseja receber ou enviar noticias
				System.out.println("Informe qual assunto deseja Enviar ou receber notícias!");
				System.out.println("1-Economia");
				System.out.println("2-Entretenimento");
				System.out.println("3-Tecnologia");
				assunto = teclado.readLine();
				saida_servidor.writeBytes(assunto + '\n');
				
				//
				Socket cx = cliente;
				Thread t = new Clientes(cx);
				
				t.start();
				
		while (true) {
			// le uma linha do teclado
			msg_digitada = teclado.readLine();
			// testa se o chat deve ser finalizado
			if (msg_digitada.startsWith("fim") == true)
				break;
			// envia a linha para o servidor
			saida_servidor.writeBytes(msg_digitada + '\n');
		}
		
		
		// fecha o cliente
		cliente.close();
		System.out.println(nome_cliente + " saiu do chat!");
	}
	
	public void run() {
		
		String msg_recebida; // mensagem recebida
		
		try {
			// cria o stream de entrada do servidor
			BufferedReader entrada_servidor = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
			// Recebe a mensagem do servidor
			msg_recebida = entrada_servidor.readLine();
			// Escreve a mensagem
			System.out.println(msg_recebida);
			
			// Compara se a msg é igual a Sair senão fica rodando até ser igual
			while (msg_recebida != null && !(msg_recebida.trim().equals("")) && !(msg_recebida.equals("Sair"))) {
				// Recebe a mensagem do servidor
				msg_recebida = entrada_servidor.readLine();
				// Escreve a mensagem
				System.out.println(msg_recebida);
			}
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
