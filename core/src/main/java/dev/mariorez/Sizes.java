package dev.mariorez;

public enum Sizes {

    WINDOW(800, 600);

    private final int width;
    private final int height;

    Sizes(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
