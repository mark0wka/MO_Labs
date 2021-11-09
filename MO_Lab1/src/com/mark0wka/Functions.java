package com.mark0wka;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Functions {

    public static void resultPrinter(ArrayList<Integer> result) {
        for (int i = 0; i < result.size(); i++) {
            if (i < result.size() - 2)
                System.out.print(result.get(i) + " -> ");
            if (i == result.size() - 2)
                System.out.println(result.get(i));
            if (i == result.size() - 1)
                System.out.println("Path weight: " + result.get(i));
        }
    }

    public static int randomizer(int min, int max) {
        return (int)(Math.random() * (max - min) + min);
    }

    public static boolean isGraphValid(int numberOfVert, ArrayList<Graph> graph) {
        int result = 0;
        if (graph.get(0).getBegin() == 0) { result++; }
        result++;
        for (int i = 1; i < graph.size(); i++) {
            boolean flag = true;
            for (int j = i - 1; j >= 0 ; j--) {
                if (graph.get(i).getEnd() == graph.get(j).getEnd()) {
                    flag = false;
                    break;
                }
            }
            if (flag) { result++; }
        }
        return result == numberOfVert;
    }

    public static ArrayList<Integer> bellmanAlgorithm(int numOfPoint, ArrayList<Graph> graph, int start, int finish) {
        ArrayList<Integer> optWeight = new ArrayList<>(numOfPoint);
        ArrayList<Integer> path = new ArrayList<>(numOfPoint);
        for (int i = 0; i < numOfPoint; i++) {
            optWeight.add(i, 10000);
            path.add(i, -1);
        }
        optWeight.set(start,0);
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int j = 0; j < graph.size(); j++) {
                if (optWeight.get(graph.get(j).getBegin()) < 10000) {
                    if (optWeight.get(graph.get(j).getEnd()) > (optWeight.get(graph.get(j).getBegin()) + graph.get(j).getWeight())) {
                        optWeight.set(graph.get(j).getEnd(),(optWeight.get(graph.get(j).getBegin()) + graph.get(j).getWeight()));
                        path.set(graph.get(j).getEnd(), graph.get(j).getBegin());
                        flag = true;
                    }
                }
            }
        }

        ArrayList<Integer> result = new ArrayList<>();
        int resWeight = 0;
        result.add(finish);
        int i = finish;
        while (path.get(i) != -1) {
            result.add(path.get(i));
            for (int j = 0; j < graph.size(); j++) {
                if (graph.get(j).getBegin() == path.get(i) && graph.get(j).getEnd() == i) {
                    resWeight += graph.get(j).getWeight();
                    break;
                }
            }
            i = path.get(i);
        }
        Collections.reverse(result);
        result.add(resWeight);
        return result;
    }

    public static ArrayList<Integer> dijkstraAlgorithm(int numberOfVert, ArrayList<Graph> graph, int start, int finish) {
        ArrayList<Integer> dist = new ArrayList<>(numberOfVert);
        ArrayList<Boolean> isVisited = new ArrayList<>(numberOfVert);
        ArrayList<Integer> path = new ArrayList<>(numberOfVert);
        for (int i = 0; i < numberOfVert; i++) {
            dist.add(i, 10000);
            isVisited.add(i, false);
            path.add(i, -1);
        }
        int index = 0;
        dist.set(start, 0);

        for (int i = 0; i < numberOfVert - 1; i++) {
            int min = 10000;
            for (int j = 0; j < numberOfVert; j++) {
                if (!isVisited.get(j) && dist.get(j) <= min) {
                    min = dist.get(j);
                    index = j;
                }
            }

            isVisited.set(index,true);
            int counter = 0;
            for (int l = 0; l < graph.size(); l++) {
                if (graph.get(l).getBegin() == index) {
                    counter = l;
                    break;
                }
            }
            while (counter < graph.size() && graph.get(counter).getBegin() == index) {
                if (!isVisited.get(graph.get(counter).getEnd()) && dist.get(index) != 10000 && dist.get(index) + graph.get(counter).getWeight() < dist.get(graph.get(counter).getEnd())) {
                    dist.set(graph.get(counter).getEnd(), dist.get(index) + graph.get(counter).getWeight());
                    path.set(graph.get(counter).getEnd(), graph.get(counter).getBegin());
                }
                counter++;
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(dist.get(finish));
        result.add(finish);
        while (path.get(finish) != -1) {
            result.add(path.get(finish));
            finish = path.get(finish);
        }
        Collections.reverse(result);
        return result;
    }
}
