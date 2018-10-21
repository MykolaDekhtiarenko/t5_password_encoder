package com.epam.training.concurrent;

import com.epam.training.Application;

import static com.epam.training.decoder.HashCalculator.hash;

public class Decryptor implements Runnable, Stoppable {

    private int position;
    private int amountOfThreads;
    private String[] dictionary;
    private String stringToDecrypt;
    private Application application;

    private volatile boolean working = true;

    public Decryptor(int position, int amountOfThreads, String[] dictionary, String stringToDecrypt, Application application) {
        this.position = position;
        this.amountOfThreads = amountOfThreads;
        this.dictionary = dictionary;
        this.stringToDecrypt = stringToDecrypt;
        this.application = application;
    }

    @Override
    public void run() {
        tryToDecrypt();
    }

    private void tryToDecrypt() {
        int index = position;
        while (working) {
            String candidate = getStringToDecrypt(index);
            if (hash(candidate).equals(stringToDecrypt)) {
                System.out.println("Password was: " + candidate);
                application.stop();
            }
            index = getNextIndex(index);
        }
    }

    private int getNextIndex(int current) {
        if (current + amountOfThreads < dictionary.length) {
            return current + amountOfThreads;
        }
        return current + amountOfThreads - dictionary.length;
    }

    private String getStringToDecrypt(int index) {
        while (dictionary[index] == null) {
            wait(100);
        }
        String res = dictionary[index];
        dictionary[index] = null;
        return res;
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
        this.working = false;
    }
}
