// ********************************************************************
// Auteur : Paul-Loup Germain & Diego Da Sylva
// Description : Classe Networkcard
// ********************************************************************

package ch.etmles.srs.buisness;

// Permet de lire du texte (comme des lignes de commande ou des fichiers) efficacement
import java.io.BufferedReader;

// Gère les erreurs d'entrée/sortie (comme la lecture ou l'écriture de données)
import java.io.IOException;

// Permet de lire les entrées de l'utilisateur via la console (le clavier)
import java.io.InputStreamReader;

// Représente une adresse IP (par exemple, 192.168.1.1 ou un nom de domaine)
import java.net.InetAddress;

// Gère les erreurs quand un nom d'hôte (ex: google.com) ne peut pas être trouvé
import java.net.UnknownHostException;

// Fournit les outils pour créer et utiliser des expressions régulières (regex)
import java.util.regex.Matcher;

// Utilisé pour définir des modèles (patterns) d'expressions régulières à rechercher dans du texte
import java.util.regex.Pattern;

public class NetworkCard {
    
    // Attributs
    private String macAddress = "";
    private int bitRate = 0;
    private String ipAddress = "";
    private String mask = "";

    // Constructeurs
    public NetworkCard(String macAddress, int debit, String ipAddress, String mask) {
        this.macAddress = macAddress;
        this.bitRate = debit;
        this.ipAddress = ipAddress;
        this.mask = mask;
    }
    
     public NetworkCard(String ipAddress, String mask) {
        this.ipAddress = ipAddress;
        this.mask = mask;
    }
    
    public NetworkCard() {
    } 

    // ********************************************************************
    // Méthode permettant de convertir une adresse ou un
    // masque en un tableau de 4 entiers
    // ********************************************************************
    // ********************************************************************
    // Méthode permettant de convertir une adresse ou un
    // masque en un tableau de 4 entiers
    // ********************************************************************
    private int[] convertStringIntoTabInt(String str) {
        // DÃ©claration d'un tableau d'entiers
        int[] tab = new int[4];
        String[] strParts = str.split("[.]");

        // Conversion de chaque partie de la chaÃ®ne en nombre entier
        for (int i = 0; i < strParts.length; i++) {
            tab[i] = Integer.parseInt(strParts[i]);
        }
        return tab;
    }

    // ********************************************************************
    // Méthode permettant de convertir un tableau de 4 entiers en une 
    // chaîne de caractères contenant une adresse
    // ********************************************************************
    private String convertTabIntIntoString(int[] tab) {
        String address = "";
        for (int i = 0; i < tab.length; i++) {
            if (i == 0) {
                address = address + tab[i];
            } else {
                address = address + "." + tab[i];
            }
        }
        return address;
    }

    // Setters
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public void setMask(String mask) {
        this.mask = mask;
    }

    // ********************************************************************
    // Méthode permettant de calculer l'adresse du sous-réseau
    // ********************************************************************
    public String getSubNetAddress(){

        // Séparer les éléments de l'adresse ip
        int[] ipAddressTab = convertStringIntoTabInt(ipAddress);

        // Séparer les éléments du masque
        int[] maskTab = convertStringIntoTabInt(mask);

        // Créer un tableau permettant de stocker le résultat
        int[] ipNetTab = new int[4];
        for (int i = 0; i < ipAddressTab.length; i++) {
            // Construire la nouvelle adresse
            ipNetTab[i] = ipAddressTab[i] & maskTab[i];
        }

        // Convertir le tableau d'entiers en une chaîne de caractères
        String addressNet = convertTabIntIntoString(ipNetTab);
        return addressNet;
    }
    
    // **********************************************************************
    // Méthode permettant de calculer l'adresse du broadcast
    // **********************************************************************
    public String getBroadcastAddress() {
        
        // Séparer les éléments de l'adresse ip
        int[] ipAddressTab = convertStringIntoTabInt(ipAddress);
        
        // Séparer les éléments du masque
        int[] maskTab = convertStringIntoTabInt(mask);
        
        // Créer un tableau permettant de stocker le résultat
        int[] broadcastTab = new int[4];
        for (int i = 0; i < ipAddressTab.length; i++) {
            
            // Construire la nouvelle adresse
            broadcastTab[i] = ipAddressTab[i] | (~maskTab[i] & 255);
        }
        
        // Convertir le tableau d'entiers en une chaîne de caractères
        String broadcast = convertTabIntIntoString(broadcastTab);
        return broadcast;
    }

    // ********************************************************************
    // Méthode permettant de ping un host distant
    // ********************************************************************
    public static String ping(String ipAddress) throws IOException, UnknownHostException {
        // On initialise une variable qui contiendra le résultat à retourner
        String result = "";

        // On transforme l'adresse IP (au format texte) en un objet InetAddress
        // Cela permet d’utiliser des méthodes réseau sur cette adresse
        InetAddress hostIP = InetAddress.getByName(ipAddress);

        // On vérifie si l'adresse IP est joignable (ping) dans un délai de 5000 millisecondes (5 secondes)
        if (hostIP.isReachable(5000))
            result = "Joiniable"; // Si c'est le cas, on indique que l'hôte est joignable
        else
            result = "Inaccessible"; // Sinon, on indique qu'il est inaccessible

        // On retourne le résultat sous forme de texte
        return result;
    }

    // ********************************************************************
    // Méthode permettant de récupérer l'adresse MAC à partir de l'adresse IP
    // ********************************************************************
    public static String getMACAddress(String ipAddress) {
        // Variable pour stocker le résultat (adresse MAC)
        String result = null;

        try {
            // Crée un objet ProcessBuilder pour exécuter la commande système "arp -a"
            // Cette commande affiche la table ARP, c'est-à-dire les adresses IP et MAC connues
            ProcessBuilder pb = new ProcessBuilder("arp", "-a");

            // Lance l'exécution de la commande
            Process process = pb.start();

            // Lit la sortie de la commande ligne par ligne
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;

            // Crée une expression régulière (regex) pour détecter la ligne contenant l'adresse IP
            // et capturer l'adresse MAC associée. Exemple de ligne : "192.168.1.1    00-11-22-33-44-55"
            Pattern pattern = Pattern.compile(ipAddress + "\\s+([\\w\\-:]+)");

            // Parcourt chaque ligne de la sortie de la commande
            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line); // Applique le motif regex à la ligne

                if (matcher.find()) { // Si une correspondance est trouvée
                    result = matcher.group(1); // On extrait l'adresse MAC (groupe 1 de la regex)
                }
            }
        } catch (Exception e) {
            // En cas d'erreur (commande non trouvée, etc.), on ne fait rien ici.

        }

        // Retourne l'adresse MAC trouvée ou null si aucune correspondance
        return result;
    }
}