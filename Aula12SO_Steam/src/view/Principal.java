package view;

import java.io.IOException;

import controller.SteamController;

public class Principal {

	public static void main(String[] args) {
		String caminho = "F:\\ADS FATEC\\3º Semestre\\Sistemas Operacionais I\\Exercicios\\Aula 12 16-11";
		String arquivo = "SteamCharts.csv";
		
		SteamController steam = new SteamController(caminho, arquivo);
		
		try {
			//steam.relatorioSteam(2, 2021, 300000);
			steam.gerarRelatorioSteam(1, 2020, caminho, "relatorio.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
