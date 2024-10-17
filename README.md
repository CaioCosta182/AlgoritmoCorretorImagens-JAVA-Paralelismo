# Processamento de Imagens com Identificação e Correção de Falhas

Este projeto em Java realiza o processamento de imagens para identificar e corrigir falhas, aplicando um filtro de suavização que melhora a qualidade da imagem. O código utiliza multithreading para processar várias imagens simultaneamente, maximizando o uso dos núcleos de processamento do sistema.

## Tecnologias Utilizadas

- **Java**
- **Biblioteca AWT**: para manipulação de imagens (leitura e escrita)
- **Multithreading**: usa a API de `ExecutorService` para processar imagens em paralelo
- **BufferedImage**: para representar e modificar imagens

## Funcionalidades

- **Identificação de Falhas**: Analisa as imagens para identificar imperfeições ou ruídos que comprometem sua qualidade.
- **Correção de Imagem**: Aplica um filtro de suavização (filtro de média) para corrigir áreas problemáticas da imagem, suavizando as falhas.
- **Processamento Paralelo**: Utiliza threads para processar múltiplas imagens simultaneamente, tornando o processo mais eficiente em sistemas com múltiplos núcleos.
- **Leitura e Escrita de Imagens**: Lê imagens de um diretório, processa-as e salva as versões corrigidas com novos nomes de arquivo.

## Como Funciona

1. **Leitura de Imagens**: A função `lerPixels` carrega uma imagem do caminho especificado e converte seus pixels em uma matriz.
2. **Correção da Imagem**: A função `corrigirImagem` aplica um filtro de média em cada pixel da matriz para suavizar e corrigir áreas com falhas.
3. **Gravação de Imagens**: A função `gravarPixels` salva a imagem corrigida com um novo nome de arquivo, preservando o formato original.
4. **Multithreading**: No método `main`, o código cria um pool de threads baseado no número de processadores disponíveis para processar várias imagens ao mesmo tempo.

## Estrutura do Código

- `lerPixels(String caminho)`: Lê uma imagem e converte seus pixels em uma matriz.
- `corrigirImagem(int[][] imgMat)`: Aplica um filtro de suavização para corrigir falhas na imagem.
- `gravarPixels(String caminhoGravar, int[][] pixels)`: Grava a matriz de pixels corrigida como uma nova imagem.
- `processImage(File img)`: Combina as etapas de leitura, correção e gravação para processar uma única imagem.
- `main(String[] args)`: O ponto de entrada do programa, que processa todas as imagens em um diretório.

## Como Executar

1. Coloque as imagens que você deseja processar no diretório especificado no código (atualmente `/Users/Imagens/modificadas`).
   
2. Compile e execute o código Java. Exemplo de como compilar:
   ```bash
   javac ProcessImageBalckWhite.java
   java ProcessImageBalckWhite
  

3. O programa processará as imagens no diretório, identificará falhas e aplicará correções, salvando as versões corrigidas com o sufixo _modificado.
   
## Observações

Certifique-se de ajustar o caminho das imagens no código, se necessário.

O código suporta formatos de imagem .png e .jpg.

## Requisitos

Java 8 ou superior

AWT e Swing: para manipulação de imagens

## Referências
Documentação do Java AWT
