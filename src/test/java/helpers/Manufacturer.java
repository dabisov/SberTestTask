package helpers;

public class Manufacturer {

    private String name;

    private int min, max;

    public Manufacturer(Manufacturer manufacturer){
        this.name = manufacturer.getName();
        this.min = manufacturer.getMin();
        this.max = manufacturer.getMax();
    }

    public Manufacturer(String makerName, int minPrice, int maxPrice) {
        this.name = makerName;
        this.min = minPrice;
        this.max = maxPrice;
    }

    public int getMin(){
        return this.min;
    }

    public int getMax(){
        return this.max;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString() {
        return name + " [" + min + ", " + max + ']';
    }
}
