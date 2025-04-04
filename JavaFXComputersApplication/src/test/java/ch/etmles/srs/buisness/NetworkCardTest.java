package ch.etmles.srs.buisness;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;
import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;
class NetworkCardTest {

    @Test
    void testPing_Localhost_RetourneJoiniable() throws IOException, UnknownHostException {
        // Adresse IP localhost
        String ipAddress = "127.0.0.1";

        // Appel de la méthode ping
        String resultat = NetworkCard.ping(ipAddress);

        // Vérification : on s’attend à "Joiniable"
        assertEquals("Joiniable", resultat);
    }

    @Test
    void testGetMACAddress_IpInconnue_RetourneNull() {
        // IP qui n’apparaît vraisemblablement pas dans la table ARP locale
        String ipAddress = "192.168.254.254";

        // Appel de la méthode getMACAddress
        String mac = NetworkCard.getMACAddress(ipAddress);

        // Vérification : on s’attend à null si aucune entrée ARP n’est trouvée
        assertNull(mac, "L’adresse MAC devrait être null pour une IP inconnue dans la table ARP");
    }
}