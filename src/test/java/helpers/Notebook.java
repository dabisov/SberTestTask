package helpers;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="Notebook")
@XmlType(propOrder = {"name", "diagonal", "weight"})
public class Notebook implements Comparable {

    private double diagonal;

    private double weight;

    private String name;

    public Notebook (){
        this.name = "";
        this.diagonal = 0d;
        this.weight = 0d;
    }

    public Notebook(Notebook notebook){
        this.name = notebook.name;
        this.diagonal = notebook.diagonal;
        this.weight = notebook.weight;
    }

    public Notebook(String name, double diagonal, double weight) {
        this.name = name;
        this.diagonal = diagonal;
        this.weight = weight;
    }

    @XmlElement(name = "Name")
    public String getName() {
        return name;
    }

    @XmlElement(name = "ScreenDiagonal")
    public double getDiagonal() {
        return diagonal;
    }

    @XmlElement(name = "Weight")
    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Object o) {
        Notebook notebook = (Notebook) o;
        if (this.diagonal > notebook.getDiagonal()) {
            return 1;
        } else if (this.diagonal < notebook.getDiagonal()) {
            return -1;
        } else if (this.diagonal == notebook.getDiagonal()) {
            if (this.weight < notebook.getWeight()) {
                return 1;
            } else if (this.weight > notebook.getWeight()) {
                return -1;
            } else {
                return 0;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return name + " [ diagonal=" + diagonal + ", weight=" + weight + "]";
    }
}
