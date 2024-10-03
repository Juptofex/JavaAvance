
package main;

import domaine.Trader;
import domaine.Transaction;

import java.util.*;
import java.util.stream.Collectors;

public class ExercicesPanaches {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        ExercicesPanaches main = new ExercicesPanaches(transactions);
        main.run();
    }

    private List<Transaction> transactions;

    public ExercicesPanaches(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void run() {
        // Complete the methods below based on the exercise descriptions
        exercice1();
        exercice2();
        exercice3();
        exercice4();
        exercice5();
        exercice6();
    }

    private void exercice1() {
        // TODO: Filter transactions of Cambridge, map to their values, and find max.
        var s = transactions
                .stream()
                .filter(e -> e.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(Integer.MIN_VALUE, Integer::max);
        System.out.println(s);
    }

    private void exercice2() {
        // TODO: Filter transactions for traders in Cambridge, group by trader, and count their transactions.
        var s = transactions
                .stream()
                .filter(e -> e.getTrader().getCity().equals("Cambridge"))
                .collect(Collectors.groupingBy(Transaction::getTrader, Collectors.counting()));
        System.out.println(s);
    }

    private void exercice3() {
        // TODO: Filter transactions over 500, map trader names, sort by name length, find the longest.
        var s = transactions
                .stream()
                .filter(e -> e.getValue() > 500)
                .map(e -> e.getTrader().getName())
                .sorted(Comparator.comparingInt(String::length))
                .reduce("", (e1, e2) -> e1.length() > e2.length() ? e1 : e2);
        System.out.println(s);
    }

    private void exercice4() {
        // TODO: Group transactions by city, map to transaction values, and compute the average.
        var s = transactions
                .stream()
                .collect(Collectors.groupingBy(e -> e.getTrader().getCity(), Collectors.averagingInt(Transaction::getValue)));
        System.out.println(s);
    }

    private void exercice5() {
        // TODO: Filter transactions in Milan, map to values, find the min, and handle empty results.
        var s = transactions
                .stream()
                .filter(e -> e.getTrader().getCity().equals("Milan"))
                .map(Transaction::getValue)
                .reduce(Integer.MAX_VALUE, Integer::min);
        System.out.println(s);
    }
    private void exercice6() {
        // TODO: group transaction by year
        var s = transactions
                .stream()
                .collect(Collectors.groupingBy(Transaction::getYear));
        System.out.println(s);
    }
}
