/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.etmles.srs.buisness;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
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
        String result = "";
        InetAddress hostIP = InetAddress.getByName(ipAddress);
        if (hostIP.isReachable(5000))
            result = "OKE";
        else
            result = "NOT OKE";

        return result;
    }

    public static String getMACAddress(String ipAddress) {
        String result = null;
        try {
            // Exécute la commande "arp -a" sous Windows ou "arp -n" sous Linux/Mac
            ProcessBuilder pb = new ProcessBuilder("arp", "-a");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            Pattern pattern = Pattern.compile(ipAddress + "\\s+([\\w\\-:]+)"); // Regex pour extraire la MAC de l'IP

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    result = "IP: " + ipAddress + " -> MAC: " + matcher.group(1);
                }
            }
        } catch (Exception e) {

        }
        return result;
    }
}


