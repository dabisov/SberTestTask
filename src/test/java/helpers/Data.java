package helpers;

import java.util.List;

public class Data{

    private int upperPrice;

    private List<Manufacturer> manufacturers;

    public Data (Data data){
        this.upperPrice = data.getUpperPrice();
        this.manufacturers = data.getManufacturers();
    }

    public Data (int upperPrice, List<Manufacturer> manufacturers){
        this.upperPrice = upperPrice;
        this.manufacturers = manufacturers;
    }

    public int getUpperPrice() {
        return upperPrice;
    }

    public List<Manufacturer> getManufacturers() {
        return manufacturers;
    }
}
