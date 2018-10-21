package com.epam.training.decoder;

import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashCalculator {

    public static String hash(String toHash) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(toHash, StandardCharsets.UTF_8);
        return hasher.hash().toString();
    }

}
