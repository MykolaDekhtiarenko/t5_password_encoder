package com.epam.training;

import com.epam.training.concurrent.DataGenerator;
import com.epam.training.concurrent.Decryptor;
import com.epam.training.concurrent.Stoppable;

import java.util.ArrayList;
import java.util.List;

public class Application {
    private List<Stoppable> threads;
    private int threadsAmount;
    private String hashToDecrypt;

    public Application(int threadsAmount, String hashToDecrypt) {
        this.threadsAmount = threadsAmount;
        this.hashToDecrypt = hashToDecrypt;
        threads = new ArrayList<>(threadsAmount);
    }

    public void start() {
        DataGenerator generator = new DataGenerator(100000);
        new Thread(generator).start();
        threads.add(generator);

        for (int i = 0; i < threadsAmount; i++) {
            Decryptor decryptor = new Decryptor(i, threadsAmount, generator.getDictionary(), hashToDecrypt, this);
            new Thread(decryptor).start();
            threads.add(decryptor);
        }
    }

    public void stop() {
        threads.forEach(Stoppable::stop);
    }

}
