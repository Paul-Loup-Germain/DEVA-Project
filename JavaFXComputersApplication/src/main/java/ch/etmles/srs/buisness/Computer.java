// ********************************************************************
// Auteur : Paul-Loup Germain & Diego Da Sylva
// Description : Classe computer
// ********************************************************************

package ch.etmles.srs.buisness;

public class Computer {

    // Attributs
    private String name;
    private String model;
    private int memory;
    private int nbProcessors;
    private int HDD; 
    private String OS;
    private NetworkCard card;

    // Constructeurs
    public Computer(String name, String model, int memory, int nbProcessors, int HDD, String OS) {
        this.name = name;
        this.model = model;
        this.memory = memory;
        this.nbProcessors = nbProcessors;
        this.HDD = HDD;
        this.OS = OS;
		this.card = null;
    }

	public Computer(String name, String model, int memory, int nbProcessors, int HDD, String OS, NetworkCard card) {
        this.name = name;
        this.model = model;
        this.memory = memory;
        this.nbProcessors = nbProcessors;
        this.HDD = HDD;
        this.OS = OS;
		this.card = null;
    }

    // Getters
    public NetworkCard getCard() {
        return card;
    }
    public String getName() {
        return name;
    }
    public String getModel() {
        return model;
    }
    public int getMemory() {
        return memory;
    }
    public int getNbProcessors() {
        return nbProcessors;
    }
    public int getHDD() {
        return HDD;
    }
    public String getOS() {
        return OS;
    }

    // Setters
    public void setCard(NetworkCard card) {
        this.card = card;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setModel(String model) {
        this.model = model;
    }
    public void setMemory(int memory) {
        this.memory = memory;
    }
    public void setNbProcessors(int nbProcessors) {
        this.nbProcessors = nbProcessors;
    }
    public void setHDD(int HDD) {
        this.HDD = HDD;
    }
    public void setOS(String OS) {
        this.OS = OS;
    }
}