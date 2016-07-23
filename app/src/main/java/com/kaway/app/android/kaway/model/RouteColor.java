package com.kaway.app.android.kaway.model;

public class RouteColor {
    int r;
    int g;
    int b;

    public RouteColor() {
    }

    public RouteColor(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int rgb() {
        return (0xFF << 24) | (r << 16) | (g << 8) | b;
    }
}
