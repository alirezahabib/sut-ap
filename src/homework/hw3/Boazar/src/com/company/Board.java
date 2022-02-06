package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Board<T extends Drawable> {
    ArrayList<T> shapes = new ArrayList<>();

    public void addNewShape(T shape) {
        shapes.add(shape);
    }

    public double allPerimeter() {
        return shapes.stream().mapToDouble(Drawable::getPerimeter).sum();
    }

    public double allSurface() {
        return shapes.stream().mapToDouble(Drawable::getSurface).sum();
    }

    public double allSide() {
        int sides = 0;
        for (T shape : shapes) {
            try {
                sides += shape.getSide();
            } catch (SideNotDefinedException ignored) {
            }
        }
        return sides;
    }

    public double allSideException() throws SideNotDefinedException {
        int sides = 0;
        for (T shape : shapes) sides += shape.getSide();
        return sides;
    }

    public T minimumSurface() {
        return shapes.stream().min(Comparator.comparingDouble(Drawable::getSurface)).orElse(null);
    }

    public ArrayList<T> sortedList(double x) {
        return shapes.stream().filter(shape -> shape.getPerimeter() > x)
                .sorted(Comparator.comparingDouble(Drawable::getSurface))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
