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
public class CoinsManagerImpl implements CoinsManager {
    
    private int coins = 100;

    @Override
    public boolean increaseCoins(int value) {
        coins += value;
        return true;
    }

    @Override
    public boolean hasEnoughMoney(int value) {
        int aux = coins;
        return (aux - value ) > 0;
    }

    @Override
    public boolean decreaseCoins(int value) {
        if (hasEnoughMoney(value)) {
            this.coins -= value;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Integer getValue() {
        return this.coins;
    }
    
}
