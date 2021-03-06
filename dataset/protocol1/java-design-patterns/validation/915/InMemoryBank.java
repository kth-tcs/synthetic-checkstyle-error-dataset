/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.hexagonal.banking;

import java.util.HashMap;
import java.util.Map;

import com.iluwatar.hexagonal.domain.LotteryConstants;

/**
 * 
 * Banking implementation
 *
 */
public class InMemoryBank implements WireTransfers {

  private static Map<String, Integer> accounts = new HashMap<>();
  
  static {
    accounts.put (LotteryConstants.SERVICE_BANK_ACCOUNT, LotteryConstants.SERVICE_BANK_ACCOUNT_BALANCE);
  }
  
  @Override
  public void setFunds(String bankAccount, int amount) {
    accounts.put(bankAccount, amount);
  }

  @Override
  public int getFunds(String bankAccount) {
    return accounts.getOrDefault(bankAccount, 0);
  }

  @Override
  public boolean transferFunds(int amount, String sourceBackAccount, String destinationBankAccount) {
    if (accounts.getOrDefault(sourceBackAccount, 0) >= amount) {
      accounts.put(sourceBackAccount, accounts.get(sourceBackAccount) - amount);
      accounts.put(destinationBankAccount, accounts.get(destinationBankAccount) + amount);
      return true;
    } else {
      return false;
    }
  }
}
