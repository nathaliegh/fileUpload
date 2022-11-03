# Problem description

You should develop a small application that receive files as parameter, extract its content, outputting the results in a different file.

## Input file

There are three different possible type of information for input file:

1. Sellers data - Format: (001, name)

2. Clients data - Format: (002, name, profession)

3. Sales data - Format: (003, sales_id, [item_id-amount-price], seller_name)

### Example:

001,Jeferson<br />
001,Daniel <br />
002,Barbara,Designer <br />
002,Alex,Developer <br />
003,01,[1-10-100;2-30-2.50;3-40-3.10],Jeferson <br />
003,02,[1-34-10;2-33-1.50;3-40-0.10],Daniel <br />

## Output file

After the input file is being processed, one output file should be created with the following information:

· Number of Clients found in the file

· Number of Sellers found in the file

· Sales id of the biggest sale

· Name of the Seller that earned less money

### Example:

· Number of Clients found in the file: 2

· Number of Sellers found in the file: 2

· Sales id of the biggest sale: 1

· Name of the Seller that earned less money: Daniel

### *Constraints*

· The application should be developed in Java programming language.

· The input file should have .txt extension.

· The input file should be removed after its processing is done.

· The output file should have .done.txt.

· After started, the application should be running forever.

· The application should only process files that have the correct format, excluding the ones that have invalid format.

· The application should be able to read from 0 to 10000 files in parallel

· The application should be created in a way that it could run with a single command.

· The application should run in any machine, doesn’t matter the operational system.