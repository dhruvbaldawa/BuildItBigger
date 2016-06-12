package com.dhruvb.jokeprovider;

import java.util.Random;

/**
 * Created by dhruv on 24/5/16.
 */
public class JokeProvider {
    final private String[] ALL_JOKES = {
            "If you work in an office with Chuck Norris, don't ask him for his three-hole-punch.",
            "When Chuck Norris wants an egg, he cracks open a chicken.",
            "Chuck Norris can access private methods.",
            "Chuck Norris can believe it's not butter.",
    };
    public String getJoke() {
        return ALL_JOKES[new Random().nextInt(ALL_JOKES.length)];
    }
}
