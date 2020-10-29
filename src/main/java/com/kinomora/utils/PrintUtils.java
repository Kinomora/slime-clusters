package com.kinomora.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.Clock;
import java.time.LocalTime;

public class PrintUtils extends PrintStream {

    Clock clock;

    public PrintUtils(OutputStream out, Clock clock) {
        super(out);
        this.clock = clock;
    }

    @Override
    public void print(String string) {
        super.print("\r");
        super.print("[" + LocalTime.now(this.clock) + "] " + string);
    }
}
