package entity;

public class Apartment {
    private int id;
    private District district;
    private String address;
    private int square;
    private int flatsCount;
    private int price;

    public Apartment() {
    }

    public Apartment(int id, District district, String address, int square, int flatsCount, int price) {
        this.district = district;
        this.address = address;
        this.square = square;
        this.flatsCount = flatsCount;
        this.price = price;
    }

    public Apartment(String district, String address, int square, int flatsCount, int price) {
        this.district = new District(district);
        this.address = address;
        this.square = square;
        this.flatsCount = flatsCount;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Квартира " +
                "в районе " + district.getName() +
                ", по адресу " + address + "." +
                " Площадь квартиры " + square +
                " м. кв., " + flatsCount +
                " комнаты. Стоимость " + price + "$";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSquare() {
        return square;
    }

    public void setSquare(int square) {
        this.square = square;
    }

    public int getFlatsCount() {
        return flatsCount;
    }

    public void setFlatsCount(int flatsCount) {
        this.flatsCount = flatsCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
