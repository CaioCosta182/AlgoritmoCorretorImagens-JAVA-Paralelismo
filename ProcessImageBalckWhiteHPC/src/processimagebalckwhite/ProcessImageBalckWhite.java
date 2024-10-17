package processimagebalckwhite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;

public class ProcessImageBalckWhite {

    public static int[][] lerPixels(String caminho) {

        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File(caminho));
            if (bufferedImage == null) {
                throw new IOException("A leitura da imagem falhou. A imagem pode estar corrompida ou o caminho está incorreto.");
            }
            int largura = bufferedImage.getWidth();
            int altura = bufferedImage.getHeight();

            int[][] pixels = new int[largura][altura];
            for (int i = 0; i < largura; i++) {
                for (int j = 0; j < altura; j++) {
                    // Normalizando de forma simplificada para imagem em escala de cinza
                    float vermelho = new Color(bufferedImage.getRGB(i, j)).getRed();
                    float verde = new Color(bufferedImage.getRGB(i, j)).getGreen();
                    float azul = new Color(bufferedImage.getRGB(i, j)).getBlue();
                    int escalaCinza = (int) (vermelho + verde + azul) / 3;

                    pixels[i][j] = escalaCinza;
                }
            }

            return pixels;
        } catch (IOException ex) {
            System.err.println("Erro ao ler a imagem: " + ex.getMessage());
        }

        return null;
    }

    public static void gravarPixels(String caminhoGravar, int pixels[][]) {
        
        caminhoGravar = caminhoGravar
                .replace(".png", "_modificado.png")
                .replace(".jpg", "_modificado.jpg");
        
        int largura = pixels.length;
        int altura = pixels[0].length;

        BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_BYTE_GRAY);

        // Transformando a matriz em um vetor de bytes
        byte bytesPixels[] = new byte[largura * altura];
        for (int x = 0; x < largura; x++) {
            for (int y = 0; y < altura; y++) {
                bytesPixels[y * largura + x] = (byte) pixels[x][y];
            }
        }

        // Copiando todos os bytes para a nova imagem
        imagem.getRaster().setDataElements(0, 0, largura, altura, bytesPixels);

        // Criamos o arquivo e gravamos os bytes da imagem nele
        File ImageFile = new File(caminhoGravar);
        try {
            ImageIO.write(imagem, "png", ImageFile);
            System.out.println("Nova Imagem disponível em: " + caminhoGravar);
        } catch (IOException e) {
            System.err.println("Erro ao gravar a imagem: " + e.getMessage());
        }
    }

    public static int[][] corrigirImagem(int imgMat[][]) {
        int largura = imgMat.length;
        int altura = imgMat[0].length;
        int[][] novaImgMat = new int[largura][altura];

        // Aplicando um filtro de média para correção
        for (int i = 1; i < largura - 1; i++) {
            for (int j = 1; j < altura - 1; j++) {
                int soma = 0;
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        soma += imgMat[i + k][j + l];
                    }
                }
                novaImgMat[i][j] = soma / 9;
            }
        }

        // Preenchendo as bordas sem filtro (copiando valores originais)
        for (int i = 0; i < largura; i++) {
            novaImgMat[i][0] = imgMat[i][0];
            novaImgMat[i][altura - 1] = imgMat[i][altura - 1];
        }

        for (int j = 0; j < altura; j++) {
            novaImgMat[0][j] = imgMat[0][j];
            novaImgMat[largura - 1][j] = imgMat[largura - 1][j];
        }

        return novaImgMat;
    }

    public static void processImage(File img) {
        int imgMat[][] = lerPixels(img.getAbsolutePath());
        if (imgMat != null) {
            imgMat = corrigirImagem(imgMat);
            gravarPixels(img.getAbsolutePath(), imgMat);
        }
    }

    public static void main(String[] args) {

        File directory = new File("/Users/caiocosta/NetBeansProjects/Arquetetura e Organização de Computadores/Imagens/modificadas");
        if (!directory.exists()) {
            System.err.println("Diretório não encontrado: " + directory.getAbsolutePath());
            return;
        }

        File imagesFile[] = directory.listFiles();
        if (imagesFile == null) {
            System.err.println("Nenhuma imagem encontrada no diretório: " + directory.getAbsolutePath());
            return;
        }

        // Criação de um pool de threads
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        // Submetendo tarefas ao pool
        for (File img : imagesFile) {
            executor.submit(() -> processImage(img));
        }

        // Desligando o executor
        executor.shutdown();
    }
}
