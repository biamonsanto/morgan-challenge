# Matching Engine

## Introdução e Contextualização

Este projeto implementa uma Matching Engine simples, que é um sistema responsável por cruzar ordens de compra e venda de ativos financeiros em uma exchange. Em termos de funcionalidade, a Matching Engine precisa ser capaz de:

1. Receber ordens.
2. Determinar se elas podem ser casadas (matched) com ordens do lado oposto do livro de ofertas (order book).
3. Registrar o resultado como transações.

### Tipos de Ordens Implementadas

Para simplificação, implementamos dois tipos de ordens:

- **Ordem de Mercado (Market Order):** Executada ao melhor preço disponível imediatamente.
- **Ordem Limite (Limit Order):** Colocada com um preço fixo, permanecendo no livro de ordens até que uma contra-ordem correspondente esteja disponível.

### Funcionalidades Adicionais

Além das ordens básicas, o sistema também suporta:

- **Cancelamento de Ordens:** Permite cancelar uma ordem específica com base em seu identificador único.
- **Modificação de Ordens:** Permite ajustar a quantidade ou o preço de uma ordem no livro.
- **Ordens Pegged:** Um tipo de ordem dinâmica que segue o preço do melhor bid (compra) ou offer (venda) disponível.
- **Visualização do Livro de Ordens:** Permite visualizar o estado atual do livro de ordens com todas as ordens de compra e venda.

Este projeto foi desenvolvido em **Java**, utilizando classes para encapsular cada ordem e a lógica da engine, de forma modular e extensível.

## Estrutura do Projeto

A estrutura de diretórios do projeto é a seguinte:

```
/src
    ├── Main.java             // Ponto de entrada do programa, leitura de entradas
    ├── MatchingEngine.java   // Lógica de matching de ordens
    └── Order.java            // Estrutura de dados para as ordens

```
## Como Rodar o Projeto

### Pré-requisitos

* JDK instalado e configurado na máquina.
* Terminal ou IDE (como IntelliJ, Eclipse) para executar o código.

### Instruções Básicas para Execução

1. **Compilar os arquivos Java:**

   Abra o terminal, navegue até o diretório `/src` e execute o comando:

```
   javac Main.java MatchingEngine.java Order.java
```

2. **Executar o Programa:**

   Abra o terminal, navegue até o diretório `/src` e execute o comando:

```
   java Main
```

3. **Operações Básicas no Terminal:**

  O programa solicitará que você insira ordens. Utilize o seguinte formato para testar as funcionalidades básicas de inserção e matching de ordens.

```
>> limit buy 100 10
>> limit sell 100 20
>> limit sell 200 20
>> market buy 150
Trade, price: 20.0, qty: 150
>> market buy 200
Trade, price: 20.0, qty: 150
>> market sell 200
Trade, price: 10.0, qty: 100
>> exit
```

Neste exemplo, ordens de compra e venda são colocadas e imediatamente cruzadas conforme possível. As ordens de mercado executam ao melhor preço disponível, com o sistema exibindo cada transação realizada.

## Funcionalidades Adicionais (Bônus)

Além das operações básicas, o projeto inclui funcionalidades adicionais, como visualização, cancelamento e modificação de ordens.

### Visualização do Livro de Ordens

O comando `print` exibe o estado atual do livro de ordens.

### Cancelamento de Ordens

O comando `cancel order <ID>` permite cancelar uma ordem específica pelo identificador gerado para cada ordem.

### Modificação de Ordens

O comando `modify order <ID> <quantidade> <preço>` permite modificar uma ordem existente no livro, atualizando a quantidade e/ou preço.

### Exemplo com Funcionalidades de Bônus

Após compilar e executar o programa como descrito acima, insira as seguintes entradas no terminal para testar as funcionalidades adicionais:

```

>> limit buy 100 10
Order created: buy 100 @ 10 (ID: 12345)

>> limit sell 100 20
Order created: sell 100 @ 20 (ID: 67890)

>> market buy 50
Trade, price: 20.0, qty: 50

>> print
Order Book:
Buy Orders:
buy 100 @ 10 (ID: 12345)
Sell Orders:
sell 50 @ 20 (ID: 67890)

>> cancel order 12345
Order cancelled: 12345

>> print
Order Book:
Buy Orders:
Sell Orders:
sell 50 @ 20 (ID: 67890)

```
Neste exemplo, vemos uma ordem limite de compra e uma de venda inseridas no sistema, seguidas por uma ordem de mercado que executa uma transação parcial. Em seguida, a função **print** exibe o livro de ordens atual, antes e depois do cancelamento de uma ordem de compra.




