package edu.uob.Tools;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Output {
    public static PrintStream originalOut = System.out;
    public static ByteArrayOutputStream data = new ByteArrayOutputStream();

    public static PrintStream captureStream = new PrintStream(data);

    public static void changeOutputStream(){
        System.setOut(Output.captureStream);
    }

    public static void restoreOutputStream() {
        System.setOut(Output.originalOut);
    }
}
