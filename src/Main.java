import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;
import java.time.Duration;
import java.time.Instant;
public class Main {

	public static void main(String[] args) {
		/*
		 * O algoritmo irá gerar numeros aleatorios conforme o tamanho que o usuario passar 
		 * e com quanto de desordenação o mesmo escolher e então ira salvar esses numeros desordenados
		 * em um txt no formato csv para então ler deste arquivo os números
		 * e usar os três tipos de estrutura de dados para ordenação
		 * e ver a diferença de tempo entre eles
		 * no final irá salvar estes números já ordenados em um outro txt no formato csv também
		 */
		String tamanhoArray = JOptionPane.showInputDialog("Digite quantos números deseja gerar").replaceAll("\\D+","");
		long numerosAleatoriosAGerar = tamanhoArray.isEmpty() ? ThreadLocalRandom.current().nextLong(0,500): Long.parseLong(tamanhoArray);
		
		long[] numerosAleatorios = new long[(int) numerosAleatoriosAGerar];
		long[] numerosAleatoriosLendoArquivo = new long[(int) numerosAleatoriosAGerar];
		String pathArquivo = "numerosDesordenados.txt";
		String path = System.getProperty("user.home") + "/Desktop/";
		try
		{
            // CRIAR O ARQUIVO
			FileWriter arq = new FileWriter(path + pathArquivo);
			PrintWriter inserirArquivo = new PrintWriter(arq);
			for (int i = 0; i < numerosAleatoriosAGerar; i++) {
				numerosAleatorios[i] = ThreadLocalRandom.current().nextLong(((numerosAleatoriosAGerar - 1) + 1));
				inserirArquivo.printf(numerosAleatorios[i] + ",");
			}
			arq.close();
			
			
			// LER O ARQUIVO
			BufferedReader csvReader = new BufferedReader(new FileReader(path + pathArquivo));
			String linha;
			while ((linha = csvReader.readLine()) != null) {
				String[] numeros = linha.split(",");
				for(int i =0;i< numeros.length;i++)
				{
					numerosAleatoriosLendoArquivo[i] = Long.parseLong(numeros[i]);
				}
			}
			csvReader.close();
			
		}
		catch(Exception e)
		{
			System.out.println("catch");
			System.out.println(e);
		}
		
	
		
		String valorPorcentagemNaoFormatado = JOptionPane.showInputDialog("Digite uma porcentagem de ordenacao").replaceAll("\\D+","");
		double valorPorcentagem = valorPorcentagemNaoFormatado.isEmpty() ? 0 : Double.parseDouble(valorPorcentagemNaoFormatado);
		String[] logs = new String[3];
		Instant Inicio = Instant.now();
		long[] numerosAleatoriosFinal = InsertionSort(numerosAleatoriosLendoArquivo,valorPorcentagem);
		Instant Fim = Instant.now();
		Duration tempo = Duration.between(Inicio, Fim);
		long tempoFormatado = tempo.toMillis();
		int segundos = (int) ((tempoFormatado / 1000) % 60);
		int minutos = (int) ((tempoFormatado / 1000) / 60);
		logs[0] =  "\n InsertionSort tempo em milissegundos: " + tempo.toMillis() + "\n Minutos: " + tempo.toMinutes()
		+ "\n Segundos: " + segundos + "\n Valores: " + Arrays.toString(numerosAleatoriosFinal);
		System.out.println("\n InsertionSort tempo em milissegundos: " + tempo.toMillis() + "\n Minutos: " + tempo.toMinutes()
				+ "\n Segundos: " + segundos + "\n Valores: " + Arrays.toString(numerosAleatoriosFinal));

		
		Inicio = Instant.now();
		numerosAleatoriosFinal =  quickSort(numerosAleatoriosLendoArquivo, 0, (int) CalcularPorcentagem(numerosAleatoriosLendoArquivo, valorPorcentagem) - 1);
		Fim = Instant.now();
		tempo = Duration.between(Inicio, Fim);
		tempoFormatado = tempo.toMillis();
		segundos = (int) ((tempoFormatado / 1000) % 60);
		minutos = (int) ((tempoFormatado / 1000) / 60);
		logs[1] = "\n QuickSort tempo em milissegundos: " + tempo.toMillis() + "\n Minutos: " + tempo.toMinutes()
		+ "\n Segundos: " + segundos + "\n Valores: " + Arrays.toString(numerosAleatoriosFinal);
		System.out.println("\n QuickSort tempo em milissegundos: " + tempo.toMillis() + "\n Minutos: " + tempo.toMinutes()
				+ "\n Segundos: " + segundos + "\n Valores: " + Arrays.toString(numerosAleatoriosFinal));
		
		Inicio = Instant.now();
		numerosAleatoriosFinal = Bubble_Sort(numerosAleatoriosLendoArquivo,valorPorcentagem);
		Fim = Instant.now();
		tempo = Duration.between(Inicio, Fim);
		tempoFormatado = tempo.toMillis();
		segundos = (int) ((tempoFormatado / 1000) % 60);
		minutos = (int) ((tempoFormatado / 1000) / 60);
		logs[2] ="\n Bubble sort tempo em milissegundos: " + tempo.toMillis() + "\n Minutos: " + tempo.toMinutes()
		+ "\n Segundos: " + segundos + "\n Valores: " + Arrays.toString(numerosAleatoriosFinal);
		
		System.out.println("\n Bubble sort tempo em milissegundos: " + tempo.toMillis() + "\n Minutos: " + tempo.toMinutes()
				+ "\n Segundos: " + segundos + "\n Valores: " + Arrays.toString(numerosAleatoriosFinal));
		
		
		//GERAR ARQUIVO LOG
		try
		{
		pathArquivo = "Logs.txt";
		FileWriter arq3 = new FileWriter(path + pathArquivo);
		PrintWriter inserirArquivo2 = new PrintWriter(arq3);
		for (int i = 0; i < logs.length; i++) {
			inserirArquivo2.printf(logs[i] + ",");
		}
		arq3.close();
		}
		catch (Exception e) {
		}
		// GERAR ARQUIVO ORDENADO
		pathArquivo = "numerosOrdenados.txt";
		try
		{
			FileWriter arq2 = new FileWriter(path + pathArquivo);
			PrintWriter inserirArquivo = new PrintWriter(arq2);
			for (int i = 0; i < numerosAleatoriosLendoArquivo.length; i++) {
				inserirArquivo.printf(numerosAleatoriosLendoArquivo[i] + ",");
			}
			arq2.close();	
		}
		catch(Exception e)
		{
			
		}
		
	}

	public static double CalcularPorcentagem(long[] array,double porcentagemOrdenacao)
	{
		long valor = array.length;
		double porcentagem = porcentagemOrdenacao / 100;
		double valorComPorcentagem = valor * porcentagem;
	    return valor - valorComPorcentagem;
	}
	public static long[] InsertionSort(long[] array,double porcentagemOrdenacao) {
		long n = (long) CalcularPorcentagem(array,porcentagemOrdenacao);
		for (int i = 1; i < n; ++i) {
			long key = array[i];
			int j = i - 1;

			while (j >= 0 && array[j] > key) {
				array[j + 1] = array[j];
				j = j - 1;
			}
			array[j + 1] = key;
		}
		return array;
	}

	public static long[] Bubble_Sort(long array[],double porcentagemOrdenacao) {
		boolean trocou = true;
		long valor;
		long valorPorcentagem = (long) CalcularPorcentagem(array, porcentagemOrdenacao) - 1;;
		do {
			trocou = false;
			for (int i = 0; i < array.length - 1; i++) {
				if(i > valorPorcentagem)
				{
					if (array[i] > array[i + 1]) {
						valor = array[i];
						array[i] = array[i + 1];
						array[i + 1] = valor;
						trocou = true;
					}	
				}
			}
		} while (trocou);
		return array;
	}
	
	private static long[] quickSort(long[] array, int indexInicio, int indexFim) {
		if (indexInicio < indexFim) {
			int posicaoPivo = separar(array, indexInicio, indexFim);
			for(int i = 0;i<2;i++)
			{
			    if(i == 1)
			    {
			    	chamarQuickSort(array, indexInicio, posicaoPivo - 1,i);
			    }
			    else
			    {
			    	chamarQuickSort(array, posicaoPivo + 1, indexFim,i);
			    }
			}
		}
		return array;
	}
	
	public static void chamarQuickSort(long[] array, int indexInicio, int indexFim,int pose)
	{
		quickSort(array, indexInicio, indexFim);
	}

	private static int separar(long[] array, int indexInicio, int indexFim) {
		int i = indexInicio + 1, f = indexFim;
		long valorStart = array[indexInicio];
		while (i <= f) {
			if (array[i] <= valorStart)
				i++;
			else if (valorStart < array[f])
				f--;
			else {
				long troca = array[i];
				array[i] = array[f];
				array[f] = troca;
				i++;
				f--;
			}
		}
		array[indexInicio] = array[f];
		array[f] = valorStart;
		return f;
	}

}
