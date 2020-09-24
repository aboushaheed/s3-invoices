package com.billing.poc.infra.aws.s3;

/**
 * a simple pair object
 *
 * @param <F> type of the first item of the pair
 * @param <S> type of the second item of the pair
 */
public class Pair<F, S> {

    private final F first;
    private final S second;

    public Pair(F first, S second) {
        super();
        this.first = first;
        this.second = second;
    }
    public F getFirst() {
        return first;
    }
    public S getSecond() {
        return second;
    }

}
