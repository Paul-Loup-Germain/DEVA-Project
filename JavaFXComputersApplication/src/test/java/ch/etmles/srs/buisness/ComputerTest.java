package ch.etmles.srs.buisness;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class ComputerTest {

    @Test
    public void testConstructeurEtGetters() {
        // GIVEN
        String name = "MonPC";
        String model = "XYZ-123";
        int memory = 16;
        int nbProcessors = 4;
        int HDD = 512;
        String OS = "Windows 10";

        // WHEN
        Computer computer = new Computer(name, model, memory, nbProcessors, HDD, OS);

        // THEN
        assertEquals(name, computer.getName(), "Le nom devrait correspondre à la valeur initialisée");
        assertEquals(model, computer.getModel(), "Le modèle devrait correspondre à la valeur initialisée");
        assertEquals(memory, computer.getMemory(), "La mémoire devrait correspondre à la valeur initialisée");
        assertEquals(nbProcessors, computer.getNbProcessors(), "Le nombre de processeurs devrait correspondre à la valeur initialisée");
        assertEquals(HDD, computer.getHDD(), "La taille du disque dur devrait correspondre à la valeur initialisée");
        assertEquals(OS, computer.getOS(), "Le système d'exploitation devrait correspondre à la valeur initialisée");
        assertNull(computer.getCard(), "La carte réseau devrait être nulle si non spécifiée dans le constructeur");
    }

    @Test
    public void testSetters() {
        // GIVEN
        Computer computer = new Computer("PC1", "Model1", 8, 2, 256, "Linux");

        // WHEN
        computer.setName("PC2");
        computer.setModel("Model2");
        computer.setMemory(16);
        computer.setNbProcessors(8);
        computer.setHDD(1024);
        computer.setOS("Ubuntu");

        // THEN
        assertEquals("PC2", computer.getName(), "Le nom devrait être mis à jour par le setter");
        assertEquals("Model2", computer.getModel(), "Le modèle devrait être mis à jour par le setter");
        assertEquals(16, computer.getMemory(), "La mémoire devrait être mise à jour par le setter");
        assertEquals(8, computer.getNbProcessors(), "Le nombre de processeurs devrait être mis à jour par le setter");
        assertEquals(1024, computer.getHDD(), "La taille du disque dur devrait être mise à jour par le setter");
        assertEquals("Ubuntu", computer.getOS(), "Le système d'exploitation devrait être mis à jour par le setter");
    }
}