
import java.util.*;
public class StockTradingPlatform {

    // ================= STOCK CLASS =================
    static class Stock {
        private String symbol;
        private String name;
        private double price;

        public Stock(String symbol, String name, double price) {
            this.symbol = symbol;
            this.name = name;
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public void displayStock() {
            System.out.printf("%-10s %-20s %.2f%n",
                    symbol, name, price);
        }
    }

    // ================= PORTFOLIO HOLDING CLASS =================
    static class Holding {
        private Stock stock;
        private int quantity;
        private double totalInvested;

        public Holding(Stock stock, int quantity, double totalInvested) {
            this.stock = stock;
            this.quantity = quantity;
            this.totalInvested = totalInvested;
        }

        public Stock getStock() {
            return stock;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getTotalInvested() {
            return totalInvested;
        }

        public void addShares(int quantity, double amount) {
            this.quantity += quantity;
            this.totalInvested += amount;
        }

        public void removeShares(int quantity, double amount) {
            double averageCost = totalInvested / this.quantity;

            this.quantity -= quantity;
            this.totalInvested -= averageCost * quantity;

            if (this.quantity == 0) {
                this.totalInvested = 0;
            }
        }

        public double getCurrentValue() {
            return quantity * stock.getPrice();
        }

        public double getProfitLoss() {
            return getCurrentValue() - totalInvested;
        }
    }

    // ================= USER CLASS =================
    static class User {
        private String name;
        private double balance;
        private double initialBalance;
        private HashMap<String, Holding> portfolio;

        public User(String name, double balance) {
            this.name = name;
            this.balance = balance;
            this.initialBalance = balance;
            this.portfolio = new HashMap<>();
        }

        public void buyStock(Stock stock, int quantity) {

            if (quantity <= 0) {
                System.out.println("Invalid quantity.");
                return;
            }

            double totalCost = stock.getPrice() * quantity;

            if (totalCost > balance) {
                System.out.println("Insufficient balance.");
                return;
            }

            balance -= totalCost;

            if (portfolio.containsKey(stock.getSymbol())) {

                Holding holding = portfolio.get(stock.getSymbol());

                holding.addShares(quantity, totalCost);

            } else {

                Holding holding = new Holding(
                        stock,
                        quantity,
                        totalCost
                );

                portfolio.put(stock.getSymbol(), holding);
            }

            System.out.println("Stock purchased successfully!");
            System.out.printf("Total cost: %.2f%n", totalCost);
        }

        public void sellStock(Stock stock, int quantity) {

            if (quantity <= 0) {
                System.out.println("Invalid quantity.");
                return;
            }

            if (!portfolio.containsKey(stock.getSymbol())) {
                System.out.println("You do not own this stock.");
                return;
            }

            Holding holding = portfolio.get(stock.getSymbol());

            if (quantity > holding.getQuantity()) {
                System.out.println("Not enough shares to sell.");
                return;
            }

            double totalValue = stock.getPrice() * quantity;

            balance += totalValue;

            holding.removeShares(quantity, totalValue);

            if (holding.getQuantity() == 0) {
                portfolio.remove(stock.getSymbol());
            }

            System.out.println("Stock sold successfully!");
            System.out.printf("Total received: %.2f%n", totalValue);
        }

        public void displayPortfolio() {

            System.out.println("\n========== YOUR PORTFOLIO ==========");

            if (portfolio.isEmpty()) {
                System.out.println("No stocks in portfolio.");
                System.out.printf("Cash Balance: %.2f%n", balance);
                return;
            }

            System.out.printf("%-10s %-10s %-15s %-15s%n",
                    "Symbol", "Quantity", "Invested", "Current Value");

            double totalInvested = 0;
            double totalCurrentValue = 0;

            for (Holding holding : portfolio.values()) {

                double invested = holding.getTotalInvested();
                double currentValue = holding.getCurrentValue();

                System.out.printf("%-10s %-10d %-15.2f %-15.2f%n",
                        holding.getStock().getSymbol(),
                        holding.getQuantity(),
                        invested,
                        currentValue);

                totalInvested += invested;
                totalCurrentValue += currentValue;
            }

            double profitLoss = totalCurrentValue - totalInvested;

            System.out.println("------------------------------------");

            System.out.printf("Cash Balance       : %.2f%n", balance);
            System.out.printf("Total Invested     : %.2f%n", totalInvested);
            System.out.printf("Current Value      : %.2f%n", totalCurrentValue);
            System.out.printf("Profit/Loss        : %.2f%n", profitLoss);

            System.out.println("====================================");
        }

        public void displayPerformance() {

            double portfolioValue = 0;

            for (Holding holding : portfolio.values()) {
                portfolioValue += holding.getCurrentValue();
            }

            double totalValue = balance + portfolioValue;
            double profitLoss = totalValue - initialBalance;

            double percentage = (profitLoss / initialBalance) * 100;

            System.out.println("\n========== PERFORMANCE ==========");

            System.out.printf("Initial Balance : %.2f%n", initialBalance);
            System.out.printf("Cash Balance    : %.2f%n", balance);
            System.out.printf("Stock Value     : %.2f%n", portfolioValue);
            System.out.printf("Total Value     : %.2f%n", totalValue);
            System.out.printf("Profit/Loss     : %.2f%n", profitLoss);
            System.out.printf("Return          : %.2f%%%n", percentage);

            System.out.println("=================================");
        }
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create stocks
        ArrayList<Stock> stocks = new ArrayList<>();

        stocks.add(new Stock("AAPL", "Apple", 180.00));
        stocks.add(new Stock("GOOG", "Google", 140.00));
        stocks.add(new Stock("TSLA", "Tesla", 250.00));
        stocks.add(new Stock("MSFT", "Microsoft", 400.00));
        stocks.add(new Stock("AMZN", "Amazon", 180.00));

        // Create user
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        User user = new User(name, 10000.00);

        int choice;

        do {

            System.out.println("\n======================================");
            System.out.println("       STOCK TRADING PLATFORM");
            System.out.println("======================================");

            System.out.println("Welcome, " + name);
            System.out.println("1. Display Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. View Performance");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println("\n========== MARKET DATA ==========");

                    System.out.printf("%-10s %-20s %s%n",
                            "Symbol", "Company", "Price");

                    for (Stock stock : stocks) {
                        stock.displayStock();
                    }

                    System.out.println("=================================");

                    break;

                case 2:

                    System.out.print("Enter stock symbol: ");
                    String buySymbol = sc.next().toUpperCase();

                    Stock buyStock = findStock(stocks, buySymbol);

                    if (buyStock == null) {
                        System.out.println("Stock not found.");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int buyQuantity = sc.nextInt();

                    user.buyStock(buyStock, buyQuantity);

                    break;

                case 3:

                    System.out.print("Enter stock symbol: ");
                    String sellSymbol = sc.next().toUpperCase();

                    Stock sellStock = findStock(stocks, sellSymbol);

                    if (sellStock == null) {
                        System.out.println("Stock not found.");
                        break;
                    }

                    System.out.print("Enter quantity: ");
                    int sellQuantity = sc.nextInt();

                    user.sellStock(sellStock, sellQuantity);

                    break;

                case 4:

                    user.displayPortfolio();

                    break;

                case 5:

                    user.displayPerformance();

                    break;

                case 6:

                    System.out.println("Thank you for using Stock Trading Platform!");

                    break;

                default:

                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        sc.close();
    }

    // ================= FIND STOCK METHOD =================
    public static Stock findStock(ArrayList<Stock> stocks, String symbol) {

        for (Stock stock : stocks) {

            if (stock.getSymbol().equalsIgnoreCase(symbol)) {
                return stock;
            }
        }

        return null;
    }
}