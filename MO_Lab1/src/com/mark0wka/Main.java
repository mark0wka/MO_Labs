package com.mark0wka;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        while (true) {

            int start;
            int finish;
            int numberOfVert;
            boolean isNegativeEdge = false;
            Scanner scanner = new Scanner(System.in);
            ArrayList<Graph> graph = new ArrayList<>();

            System.out.println("=====================================================");
            System.out.println("Enter number of vertex: ");
            numberOfVert = scanner.nextInt();
            System.out.println("Do you want to enter parameters manually? (y/n):");
            String input = scanner.next();
            if ("y".equals(input)) {
                for (int i = 0; i < numberOfVert; i++) {
                    for (int j = 0; j < numberOfVert; j++) {
                        if (i < j) {
                            int x;
                            System.out.println("Enter the weight of the edge between vertices (0 means no edge): " + i + " - " + j + ":");
                            x = scanner.nextInt();
                            if (x != 0 && x < 10000) {
                                graph.add(new Graph(i, j, x));
                                if (x < 0) {
                                    isNegativeEdge = true;
                                }
                            }
                        }
                    }
                }
            } else if ("n".equals(input)) {
                for (int i = 0; i < numberOfVert; i++) {
                    for (int j = 0; j < numberOfVert; j++) {
                        if (i < j) {
                            graph.add(new Graph(i, j, Functions.randomizer(0, 15)));
                        }
                    }
                }
            } else {
                System.out.println("Invalid input parameter.");
                continue;
            }

            System.out.println("P.Start|P.End|Weight");
            for (int i = 0; i < graph.size(); i++) {
                System.out.println("  " + graph.get(i).getBegin() + "    |  " + graph.get(i).getEnd() + "  |  " + graph.get(i).getWeight());
            }

            if (!Functions.isGraphValid(numberOfVert, graph)) {
                System.out.println("Graph is no valid.");
                continue;
            }

            do {
                System.out.println("Start point is: ");
                start = scanner.nextInt();
                System.out.println("Finish point is: ");
                finish = scanner.nextInt();
            } while (start < 0 || start >= numberOfVert || finish < 0 || finish >= numberOfVert);

            ArrayList<Integer> resultBellman = Functions.bellmanAlgorithm(numberOfVert, graph, start, finish);
            System.out.println("Bellman algorithm:");
            Functions.resultPrinter(resultBellman);
            System.out.println();

            if (isNegativeEdge) {
                System.out.println("Dijkstra's algorithm is not available, because there are negative edge weights.");
            } else {
                ArrayList<Integer> resultDijkstra = Functions.dijkstraAlgorithm(numberOfVert, graph, start, finish);
                System.out.println("Dijkstra algorithm:");
                Functions.resultPrinter(resultDijkstra);
            }

            System.out.println();
            System.out.println("Do you want to exit? (y/n):");
            input = scanner.next();
            if ("y".equals(input)) {
                break;
            }
        }
    }
}
