/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amusement.park;

/**
 *
 * @author Family
 */
public interface CoinsManager {
    
    public boolean increaseCoins(int value);

    public boolean hasEnoughMoney(int value);

    public boolean decreaseCoins(int value);

    public Integer getValue();
}
