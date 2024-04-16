package org.acme.pub.runner;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

// Defining quarkus app entry point

@QuarkusMain
public class application {

    public static void main(String[] args) {
        Quarkus.run(args);
    }

}
