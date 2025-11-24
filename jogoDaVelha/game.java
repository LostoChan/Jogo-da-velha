package jogoDaVelha;

import java.util.Scanner;
import java.util.Random;

public class game {
	
	
	//Menu principal do jogo
	static void menu() {
		Scanner sc = new Scanner(System.in);
		boolean continuar = true;;
		
		while(continuar) {
			System.out.println("Escolha uma opção:\n"
					+ "[1] Jogar\n"
					+ "[2] Regras\n"
					+ "[3] Sair");
			int escolha = sc.nextInt();
			
			switch(escolha) {
				case 1:
					jogar();
					break;
				case 2:
					System.out.println("=====REGRAS=====");
					System.out.println("");
					regras();
					break;
				case 3:
					System.out.println("Saindo do programa...");
					continuar = false;
					break;
				
				default:
					System.out.println("Escolha uma opção válida");
					
			}
		}
	}
	
	//loop que solicita aos jogadores o preenchimento dos campos vazios até um dos dois vencer ou ocorrer um empate
	static void jogadorJogador() {
		System.out.println("===Jogador x Jogador===");
		System.out.println("X - Jogador 1\n"
				+ "O - Jogador 2\n");
		
		int jogadas = 0;
		boolean continuar = true;
		String[][] tabela = new String[3][3];
		
		imprimirTabela(tabela);
		
		while(continuar) {
			tabela = XouO(tabela, jogadas);
			jogadas++;
			imprimirTabela(tabela);
			if(vence(tabela, "X")) {
				System.out.println("Jogador 1 venceu!");
				continuar = false;
			}
			if(vence(tabela, "O")) {
				System.out.println("Jogador 2 venceu!");
				continuar = false;
			}
			if(jogadas == 9) {
				System.out.println("Deu velha!");
				continuar = false;
			}
		}
		if(jogarNovamente()) {
			jogadorJogador();
		}else {
			return;
		}
	}
	
	//loop onde o jogador enfrenta a máquina até um dos dois vencerem ou empatarem
	static void jogadorMaquina() {
		System.out.println("=== Jogador x Máquina ===");
		System.out.println("X - Jogador\n"
				+ "O - Máquina");
		
		int jogadas = 0;
		String[][] tabela = new String[3][3];
		boolean continuar = true;
		
		imprimirTabela(tabela);
		
		while(continuar) {
			if(jogadas%2 == 0) {
				tabela = XouO(tabela, jogadas);
				jogadas++;
			}else {
				int[] jogadaBot = maquinaInteligente(tabela);
				tabela[jogadaBot[0]][jogadaBot[1]] = "O";
				jogadas++;
			}
			imprimirTabela(tabela);
			
			if(vence(tabela,"X")) {
				System.out.println("O jogador venceu!");
				continuar = false;
			}
			if(vence(tabela,"O")) {
				System.out.println("A máquina venceu!");
				continuar = false;
			}
			if(jogadas == 9) {
				System.out.println("Deu velha!");
				continuar = false;
			}
		}
		if(jogarNovamente()) {
			jogadorMaquina();
		}else {
			return;
		}
	}
	
	//Valida se a matriz deve ser preenchida com X ou O, além de verificar se o campo já está preenchido ou é vazio.
	static String[][] XouO(String[][] matriz, int jogadas) {
		
		
		boolean continuar = true;
		
		
		while(continuar) {
			int[] posicao = pegarPosicao();
			if(jogadas%2 == 0) {
				 if(matriz[posicao[0]][posicao[1]] == null) {
					 matriz[posicao[0]][posicao[1]] = "X";
					 continuar = false;
				 }else {
					 System.out.println("A posição já está ocupada ou a posição é inválida!");
					 continue;
				 }
				
			}else {
				if(matriz[posicao[0]][posicao[1]] == null) {
					matriz[posicao[0]][posicao[1]] = "O";
					continuar = false;
				}else {
					System.out.println("A posição já está ocupada ou a posição é inválida!");
					continue;
				}
			}
		}
		 
		 
		 return matriz;
	}
	
	
	//Pede ao jogador uma posição vertical e horizontal válida(Menor que 3 e maior que 0)
	static int[] pegarPosicao() {
		Scanner sc = new Scanner(System.in);
		int posicaoV, posicaoH;
		
		do {
			
			System.out.println("Indique o número da posição VERTICAl:");
			posicaoV = sc.nextInt();
			
			System.out.println("Indique o número da posição HORIZONTAL:");
			posicaoH = sc.nextInt();
			
			if(posicaoV > 2 || posicaoH > 2 || posicaoV < 0 || posicaoH<0) {
				System.out.println("Insira um valor válido.\n");
			}
			
		}while(posicaoH > 2 || posicaoV > 2 || posicaoH < 0 || posicaoV < 0);
		int [] posicaoHV = {posicaoV, posicaoH};
		
		return posicaoHV;
	} 

	//Gera a tabela com o uso do for, e atualiza a tabela após cada jogada
	static void imprimirTabela(String[][] tabela) {
		for(int i = 0; i < 3; i++) {
			System.out.print(i + "| ");
	        for (int j = 0; j < 3; j++) {

	            String valor = tabela[i][j];
	            if (valor == null) valor = " ";

	            System.out.print(valor);

	            if (j < 2) System.out.print(" | ");
	        }
	        System.out.println("\n-------------");
	    }
	}
	
	
	static void regras() {
		System.out.println("1. O jogo é disputado em um tabuleiro de 3x3.\n"
							+ "\n"
							+ "2. Dois jogadores participam: \n"
							+ "  - Jogador 1 utiliza o símbolo X.\n"
							+ "  - Jogador 2 ou a Máquina utiliza o símbolo O.\n"
							+ "\n"
							+ "3. Os jogadores alternam os turnos, escolhendo uma posição no tabuleiro\n"
							+ "para colocar o seu símbolo.\n"
							+ "\n"
							+ "4. Para escolher uma posição, você deve informar:\n"
							+ "  - A linha ()\n"
							+ "  - A coluna()\n"
							+ "Em valor de 0 a 2.\n"
							+ "\n"
							+ "5. Uma posição só pode ser ocupada se estiver vazia. Se a posição já estiver\n"
							+ "preenchida, o jogador deve escolher outra.\n"
							+ "\n"
							+ "6. Vence o jogador que conseguir formar uma linha com três símbolos iguais, seja:\n"
							+ "  - Na horizontal (linhas)\n"
							+ "  - Na vertical (colunas)\n"
							+ "  - Na diagonal principal ou secundária\n"
							+ "\n"
							+ "7. Se todas as posições forem preenchidas e ninguém formar uma linha, o resultado\n"
							+ "é um empate (deu velha)\n"
							+ "\n"
							+ "8. No modo Jogador X Máquina:\n"
							+ "  - O jogador sempre começa jogando com X.\n"
							+ "  - A máquina joga com O e tenta bloquear e vencer conforme a lógica implementada."
							+ "\n");
	
	}
	
	
	//Percorre a matriz e verifica as suas posições para validar a vitória.
	static boolean vence(String[][] tabela, String jogador) {
		
		//VERIFICA POSIÇÕES HORIZONTAIS
	    for(int i = 0; i < 3; i++) {
	        if(jogador.equals(tabela[i][0]) && jogador.equals(tabela[i][1]) && jogador.equals(tabela[i][2])) {
	            return true;
	        }
	    }

	    //VERIFICA POSIÇÕES VERTICAIS
	    for(int j = 0; j < 3; j++) {
	        if(jogador.equals(tabela[0][j]) && jogador.equals(tabela[1][j]) && jogador.equals(tabela[2][j])) {
	            return true;
	        }
	    }

	    //VERIFICA POSIÇÕES DIAGONAIS
	    if(jogador.equals(tabela[0][0]) && jogador.equals(tabela[1][1]) && jogador.equals(tabela[2][2]))
	    	return true;
	    if(jogador.equals(tabela[0][2]) && jogador.equals(tabela[1][1]) && jogador.equals(tabela[2][0]))
	    	return true;

	    return false;
	}
	
	
	//Verificações para definir a jogada da máquina com base nas posições já preenchidas na tabela.
	//Primeiramente a máquina tenta pegar o centro da tabela;
	//Se não for possível ela fará uma jogada aleatória;
	//Caso ela possua a oportunidade de vencer o jogo, ela jogará no espaço em que resultará na sua vitória;
	//Caso o jogador possa vencer o jogo, ela jogará na posição onde impedirá o jogador de vencer.
	static int[] maquinaInteligente(String[][] tabela) {

		//TENTA PEGAR O CENTRO
		if (tabela[1][1] == null) { 
			
			return new int[]{1, 1};
			
		}
	    //ATACAR
		for(int i = 0; i<3; i++) {
			for(int j = 0; j<3; j++) {
				if(tabela[i][j] == null) {
					tabela[i][j] = "O";
					
					if(vence(tabela, "O")) {
						tabela[i][j] = null;
						return new int[] {i, j};
					}
					
					tabela[i][j] = null;
				}
			}
		}
		
		 // DEFENDER
	    for(int i = 0; i < 3; i++) {
	        for(int j = 0; j < 3; j++) {
	            if(tabela[i][j] == null) {

	                tabela[i][j] = "X";

	                if(vence(tabela, "X")) {
	                    tabela[i][j] = null;
	                    return new int[]{i, j};
	                }

	                tabela[i][j] = null;
	            }
	        }
	    }

	    // ALEATÓRIO
	    Random r = new Random();
	    int x, y;

	    do {
	        x = r.nextInt(3);
	        y = r.nextInt(3);
	    } while(tabela[x][y] != null);

	    return new int[]{x, y};
	}
	
	
	//Valida a escolha do usuario, se ele deseja jogar novamente ou se quer parar.
	static boolean jogarNovamente(){
		Scanner sc = new Scanner(System.in);
		
		String resposta;
		do {
			System.out.println("Deseja jogar novamente? (S/N)");
			
			resposta = sc.next().toUpperCase();
			
			switch(resposta) {
				case "S":
					return true;				
				case "N":
					return false;
				default:
					System.out.println("Digite uma opção válida");
					
			}
		}while(!resposta.equals("S")&&!resposta.equals("N"));
		return false;
	}
	
	
	//Subcategoria do menu, que leva o usuario a escolha do modo de jogo, sendo eles Jogador vs Jogador e Jogador vs Máquina.
	static void jogar() {
		
		Scanner sc = new Scanner(System.in); 
		
		int escolha;
		
		do {
			System.out.println("===JOGAR===");
			System.out.println("[1] Jogador x Jogador\n"
					+ "[2] Jogador x Máquina\n"
					+ "[3] Voltar");
			
			escolha = sc.nextInt();
			
			switch(escolha){
				case 1:
					System.out.println("Iniciando partida em modo Jogador vs Jogador.\n");
					jogadorJogador();
					break;
				case 2:
					System.out.println("Iniciando partida em modo Jogador vs Máquina.\n");
					jogadorMaquina();
					break;
				case 3:
					System.out.println("Voltando ao menu principal.\n");
					return;
				default:
					System.out.println("Digite uma opção válida.\n");
			}
			
		}while(escolha != 1 && escolha !=2);
	}
	
	public static void main(String args[]){
		menu();
		
	}
	
}
