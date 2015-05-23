package com.example.DefineMath;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class com.example.DefineMath.DefineMathTest \
 * com.example.DefineMath.tests/android.test.InstrumentationTestRunner
 */
public class DefineMathTest extends ActivityInstrumentationTestCase2<DefineMath> {

    public DefineMathTest() {
        super("com.example.DefineMath", DefineMath.class);
    }

    public void testInitialization() throws Exception {


    }
}
