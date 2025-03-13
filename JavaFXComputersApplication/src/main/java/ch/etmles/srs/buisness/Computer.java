package ch.etmles.srs.buisness;

public class Computer {
    
    private String name;
    private String model;
    private int memory;
    private int nbProcessors;
    private int HDD; 
    private String OS;
    private NetworkCard card;


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
    
    public NetworkCard getCard() {
        return card;
    }

    public void setCard(NetworkCard card) {
        this.card = card;
    }
    
}
