package com.epam.training;

public class Main {
    public static final String hash = "4fd0101ea3d0f5abbe296ef97f47afec";

    public static void main(String[] args) {
       Application app = new Application(4, hash);
       app.start();
    }

}
