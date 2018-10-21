package com.epam.training.concurrent;

public class DataGenerator implements Runnable, Stoppable {
    private String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private int currentIndex = 0;
    private boolean working = true;
    private String[] dictionary;

    public DataGenerator(int size) {
        dictionary = new String[size];
    }

    @Override
    public void run() {
        int wordLength = 1;
        while (working) {
            addAllStringsToDictionary(ALPHABET.toCharArray(), wordLength);
            System.out.println("Added all words with length " + wordLength);
            wordLength++;
        }
    }

    public String[] getDictionary() {
        return dictionary;
    }

    private void nextIndex() {
        if (currentIndex++ < dictionary.length - 1) {
            return;
        }
        currentIndex = currentIndex - dictionary.length;
    }

    private void addAllStringsToDictionary(char[] alphabet, int length) {
        addAllStringsHelper(alphabet, "", length);
    }

    private void addAllStringsHelper(char[] alphabet, String prefix, int length) {
        if (length == 0) {
            add(prefix);
            nextIndex();
            return;
        }

        for (int i = 0; i < alphabet.length; ++i) {
            String newPrefix = prefix + alphabet[i];
            addAllStringsHelper(alphabet, newPrefix, length - 1);
        }
    }

    private void add(String word) {
        while (dictionary[currentIndex] != null) {
            wait(100);
        }
        dictionary[currentIndex] = word;
    }

    private void wait(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        working = false;
    }
}
