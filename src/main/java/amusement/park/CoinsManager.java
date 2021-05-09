package amusement.park;


public interface CoinsManager {
    
    public boolean increaseCoins(int value);

    public boolean hasEnoughMoney(int value);

    public boolean decreaseCoins(int value);

    public Integer getValue();
}
