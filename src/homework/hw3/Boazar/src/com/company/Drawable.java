package com.company;

public interface Drawable {
    double getPerimeter();
    double getSurface();
    int getSide() throws SideNotDefinedException;
}
