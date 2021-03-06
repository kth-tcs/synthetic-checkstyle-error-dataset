/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.balking;

import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link WashingMachine}
 */
 public class WashingMachineTest {

  private FakeDelayProvider fakeDelayProvider = new FakeDelayProvider();

  @Test
  public void wash() {
    WashingMachine washingMachine = new WashingMachine(fakeDelayProvider);

    washingMachine.wash();
    washingMachine.wash();

    WashingMachineState machineStateGlobal = washingMachine.getWashingMachineState();

    fakeDelayProvider.task.run();

    // washing machine remains in washing state
    assertEquals(WashingMachineState.WASHING, machineStateGlobal);

    // washing machine goes back to enabled state
    assertEquals(WashingMachineState.ENABLED, washingMachine.getWashingMachineState());
  }

  @Test
  public void endOfWashing() {
    WashingMachine washingMachine = new WashingMachine();
    washingMachine.wash();
    assertEquals(WashingMachineState.ENABLED, washingMachine.getWashingMachineState());
  }

  private class FakeDelayProvider implements DelayProvider {
    private Runnable task;

    @Override
    public void executeAfterDelay(long interval, TimeUnit timeUnit, Runnable task) {
      this.task = task;
    }
  }
}