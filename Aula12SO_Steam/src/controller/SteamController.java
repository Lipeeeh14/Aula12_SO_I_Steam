package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;

public class SteamController {
	
	private String caminho;
	private String arquivo;
	
	public SteamController(String caminho, String arquivo) {
		this.caminho = caminho;
		this.arquivo = arquivo;
	}
	
	public void relatorioSteam(int mes, int ano, double media) throws IOException {
		if (!validaMesAno(mes, ano))
			System.out.println("Data informada é inválida!");
		else {
			String mesExtenso = converterMes(mes);
			File arq = new File(caminho, arquivo);
			if (arq.exists() && arq.isFile()) {
				FileInputStream fluxo = new FileInputStream(arq);
				InputStreamReader leFluxo = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leFluxo);
				String linha = buffer.readLine();
				while (linha != null) {
					if (!linha.contains("gamename")) {
						String[] vetLinha = linha.split(",");
						if (Integer.parseInt(vetLinha[1]) == ano && vetLinha[2].equals(mesExtenso)
								&& Double.parseDouble(vetLinha[3]) >= media)
							System.out.println(vetLinha[0] + " | " + vetLinha[3]);
					}
					linha = buffer.readLine();
				}
				
				buffer.close();
				leFluxo.close();
				fluxo.close();
			} else {
				throw new IOException("Arquivo inválido!");
			}
		}
		
	}
	
	public void gerarRelatorioSteam(int mes, int ano, String diretorio, String nomeArquivo) throws IOException {
		if (!validarDiretorio(diretorio))
			System.out.println("Diretório inválido!");
		else if (!validaMesAno(mes, ano))
			System.out.println("Data informada é inválida!");
		else {
			String mesExtenso = converterMes(mes);
			File arq = new File(caminho, arquivo);
			if (arq.exists() && arq.isFile()) {
				File novoArquivo = new File(diretorio);
				StringBuffer bufferConteudo = new StringBuffer();
				String linhaBuffer = "";
				
				FileInputStream fluxo = new FileInputStream(arq);
				InputStreamReader leFluxo = new InputStreamReader(fluxo);
				BufferedReader buffer = new BufferedReader(leFluxo);
				String linha = buffer.readLine();
				while (linha != null) {
					if (!linha.contains("gamename")) {
						String[] vetLinha = linha.split(",");
						if (Integer.parseInt(vetLinha[1]) == ano && vetLinha[2].equals(mesExtenso)) {
							linhaBuffer = vetLinha[0] + ";" + vetLinha[3];
							bufferConteudo.append(linhaBuffer + "\r\n");
						}						
					}
					
					linha = buffer.readLine();
				}
				
				buffer.close();
				leFluxo.close();
				fluxo.close();
				
				boolean existe = novoArquivo.exists();
				escreverNoArquivo(novoArquivo, bufferConteudo.toString(), existe);
			}
		}
	}
	
	private void escreverNoArquivo(File novoArquivo, String conteudo, boolean existe) throws IOException {
		FileWriter abreArquivo = new FileWriter(novoArquivo, existe);
		PrintWriter escreveArq = new PrintWriter(abreArquivo);
		escreveArq.write(conteudo);
		escreveArq.flush();
		escreveArq.close();
		abreArquivo.close();
	}

	private boolean validarDiretorio(String diretorio) {
		File dir = new File(diretorio);
		
		return dir.exists() && dir.isDirectory();
	}

	private String converterMes(int mes) {
		String mesExtenso = "";
		
		switch(mes) {
			case 1:
				mesExtenso = "January";
				break;
			case 2:
				mesExtenso = "February";
				break;
			case 3:
				mesExtenso = "March";
				break;
			case 4:
				mesExtenso = "April";
				break;
			case 5:
				mesExtenso = "May";
				break;
			case 6:
				mesExtenso = "June";
				break;
			case 7:
				mesExtenso = "July";
				break;
			case 8:
				mesExtenso = "August";
				break;
			case 9:
				mesExtenso = "September";
				break;
			case 10:
				mesExtenso = "October";
				break;
			case 11:
				mesExtenso = "November";
				break;
			case 12:
				mesExtenso = "December";
				break;
		}
		
		return mesExtenso;
	}

	private boolean validaMesAno(int mes, int ano) {
		return (mes > 0 && mes <= 12) && (ano <= Calendar.getInstance().get(Calendar.YEAR) && ano > 0);
	}
}
