package com.corndel.nozama.exercises;

import io.javalin.Javalin;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.*;

public class D3E1 {
    // This is our counter:
    public static Counter counter = new Counter();

    /**
     * Creates a Javalin app with two endpoints.
     *
     * @return a configured Javalin instance
     * @see https://tech-docs.corndel.com/javalin/routing.html
     */
    public static Javalin createApp() {
        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/counter", () -> {
                    get("", CounterController::getCounter);
                    put("/increment", CounterController::increment);
                });
            });
        });
    }
}

class CounterController {
    /**
     * Responds with the current counter as a JSON object, e.g. { "count": 3 }.
     */
    public static void getCounter(Context ctx) {
        // TODO
        ctx.json(D3E1.counter);
    }

    /**
     * Increases the counter by 1 and then responds with the count.
     */
    public static void increment(Context ctx) {
        // TODO
        Counter counter = D3E1.counter;
        counter.setCount(counter.getCount() + 1);
        getCounter(ctx);
    }
}

class Counter {
    private int count;

    public Counter() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
