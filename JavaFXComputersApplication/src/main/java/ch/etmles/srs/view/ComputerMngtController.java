// ********************************************************************
// Auteur : Paul-Loup Germain & Diego Da Sylva
// Description : Classe Controller
// ********************************************************************

package ch.etmles.srs.view;

// Importe la classe "Computer" du package "ch.etmles.srs.buisness".
import ch.etmles.srs.buisness.Computer;

// Importe la classe "NetworkCard", qui représente peut-être une carte réseau.
import ch.etmles.srs.buisness.NetworkCard;

// Permet de créer une pause ou un délai dans une animation JavaFX.
import javafx.animation.PauseTransition;

// Permet de créer des propriétés JavaFX observables (ici pour des chaînes de caractères).
import javafx.beans.property.SimpleStringProperty;

// Collection spéciale utilisée par JavaFX pour mettre à jour automatiquement l'interface
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// Interface à implémenter pour gérer l'initialisation des éléments JavaFX
import javafx.fxml.Initializable;

// Importe plusieurs contrôles d’interface graphique (bouton, tableau, etc.)
import javafx.scene.control.*;

// Importe le contrôle "TextField" (champ de texte) séparément (pas nécessaire ici car déjà inclus ci-dessus).
import javafx.scene.control.TextField;

// Permet de spécifier une durée, par exemple pour une animation ou une pause.
import javafx.util.Duration;

// Gère les événements de type "clic de bouton" ou autres actions utilisateur.
import javafx.event.ActionEvent;

// Importe une forme de cercle pour afficher ou animer un élément graphique.
import javafx.scene.shape.Circle;

// Permet de définir des couleurs (par exemple : pour colorer un cercle).
import javafx.scene.paint.Color;

// Permet d’utiliser l’annotation @FXML, qui connecte les éléments de l'interface graphique
import javafx.fxml.FXML;

// Importe des classes pour la lecture/écriture de fichiers.
import java.io.*;

// Permet de manipuler des URL (adresses de ressources), souvent utilisées pour charger des fichiers ou accéder à Internet.
import java.net.URL;

// Contient des données localisées, par exemple pour afficher du texte dans différentes langues.
import java.util.ResourceBundle;

// Importe la classe Image de JavaFX pour pouvoir afficher ou manipuler des images dans l'interface graphique.
import javafx.scene.image.Image;

public class ComputerMngtController implements Initializable {

    // Déclaration des champs fxml
    @FXML private Circle clePingResult;
    @FXML private Slider spNbCores;
    @FXML private TextField txtGetIpAddress;
    @FXML private TextField txtGetMacAddress;
    @FXML private TextField txtName;
    @FXML private TextField txtModel;
    @FXML private TextField txtStorage;
    @FXML private TextField txtAddIpAddress;
    @FXML private TextField txtAddMask;
    @FXML private ToggleGroup tgRAMqty;
    @FXML private RadioButton rbRAM8;
    @FXML private ComboBox<String> cbOSChoice;
    @FXML private TableView<Computer> tableView;
    @FXML private TableColumn<Computer, String> colNom;
    @FXML private TableColumn<Computer, String> colModele;
    @FXML private TableColumn<Computer, String> colRam;
    @FXML private TableColumn<Computer, String> colStockage;
    @FXML private TableColumn<Computer, String> colCpu;
    @FXML private TableColumn<Computer, String> colOs;
    @FXML private TableColumn<Computer, String> colIp;
    @FXML private TableColumn<Computer, String> colMask;
    @FXML private javafx.scene.image.ImageView imgOsChoice;

    // Liste observable contenant des objets Computer.
    // Elle permet de mettre à jour automatiquement l'interface JavaFX quand la liste change (ajout, suppression, etc.).
    private final ObservableList<Computer> computerList = FXCollections.observableArrayList();

    NetworkCard myNetworkCard = new NetworkCard();

    // ********************************************************************
    // Cette méthode est appelée automatiquement lorsque l'interface graphique est chargée
    // ********************************************************************
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Ajoute des options dans une liste déroulante (ComboBox) appelée cbOSChoice
        cbOSChoice.getItems().addAll("Linux", "Windows", "MacOS");

        // Associe les colonnes de la table à la propriété de la classe coputer de chaque objet affiché
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colModele.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getModel()));
        colRam.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMemory())));
        colCpu.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getNbProcessors())));
        colStockage.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getHDD())));
        colOs.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOS()));
        colIp.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIp()));
        colMask.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMask()));

        // Remplit la table avec la liste des ordinateurs (computerList)
        tableView.setItems(computerList);

        // Ajoute un écouteur pour détecter quand l'utilisateur sélectionne un élément dans la table
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Si un nouvel élément est sélectionné, remplir les champs du formulaire avec ses données
                fillFieldsWithComputer(newSelection);
            }
        });
    }

    // ********************************************************************
    // Méthode appelée lorsqu'on clique sur le bouton "Ajouter"
    // ********************************************************************
    public void onAddButtonClick(ActionEvent event) {
        // Récupérer le bouton radio sélectionné dans le groupe (par exemple : 8 Go, 16 Go, etc.)
        RadioButton selectedRAM = (RadioButton) tgRAMqty.getSelectedToggle();

        // Vérifier si un bouton radio a bien été sélectionné (éviter les erreurs si l'utilisateur n'a rien choisi)
        if (selectedRAM != null) {
            try {
                // Convertir le texte du bouton radio sélectionné (par ex. "8") en nombre entier
                int ram = Integer.parseInt(selectedRAM.getText());

                // Lire le texte dans le champ de stockage (ex : "512") et le convertir en entier
                int storage = Integer.parseInt(txtStorage.getText());

                // Récupérer la valeur du Spinner (nombre de cœurs du processeur)
                int cores = (int) spNbCores.getValue();

                // Créer un nouvel objet Computer avec les données saisies par l'utilisateur
                Computer newPC = new Computer(
                        txtName.getText(),              // Nom de l'ordinateur
                        txtModel.getText(),             // Modèle
                        ram,                            // Quantité de RAM (en Go)
                        cores,                          // Nombre de cœurs
                        storage,                        // Capacité de stockage (en Go)
                        (String) cbOSChoice.getValue(),  // Système d'exploitation choisi dans la liste déroulante
                        txtAddIpAddress.getText(),
                        txtAddMask.getText()
                );

                // Ajouter ce nouvel ordinateur à la liste des ordinateurs
                computerList.add(newPC);

                // Réinitialiser les champs du formulaire pour permettre une nouvelle saisie
                clearFields();

            } catch (NumberFormatException e) {
                // Ce bloc est exécuté si la conversion en nombre échoue (par ex. l'utilisateur a écrit du texte au lieu d’un nombre)

            }
        } else {
            // Ce bloc est exécuté si aucun bouton radio n'a été sélectionné

        }
    }

    // ********************************************************************
    // Méthode appelée lorsqu'on clique sur le bouton "Éditer"
    // ********************************************************************
    public void onEditButtonClick(ActionEvent event) {
        // Récupère l'élément (ordinateur) actuellement sélectionné dans la table
        Computer selectedPC = tableView.getSelectionModel().getSelectedItem();

        // Vérifie qu'un élément est bien sélectionné
        if (selectedPC != null) {
            try {
                // Met à jour le nom de l'ordinateur avec le texte du champ txtName
                selectedPC.setName(txtName.getText());

                // Met à jour le modèle de l'ordinateur
                selectedPC.setModel(txtModel.getText());

                // Récupère le bouton radio sélectionné dans le ToggleGroup pour la RAM
                RadioButton selectedRAM = (RadioButton) tgRAMqty.getSelectedToggle();

                // Si un bouton radio est sélectionné (par exemple 8, 16, ou 32 Go de RAM)
                if (selectedRAM != null) {
                    // Convertit le texte du bouton (ex. "8") en entier et le stocke
                    selectedPC.setMemory(Integer.parseInt(selectedRAM.getText()));
                }

                // Récupère la valeur du spinner (nombre de cœurs de processeur) et la stocke
                selectedPC.setNbProcessors((int) spNbCores.getValue());

                // Convertit le texte du champ txtStorage en entier et le stocke dans le disque dur
                selectedPC.setHDD(Integer.parseInt(txtStorage.getText()));

                // Met à jour le système d'exploitation choisi dans la ComboBox
                selectedPC.setOS(cbOSChoice.getValue());

                selectedPC.setIp(txtAddIpAddress.getText());

                selectedPC.setMask(txtAddMask.getText());

                // Rafraîchit la table pour voir les modifications à l'écran
                tableView.refresh();

            } catch (NumberFormatException e) {
                // En cas d'erreur de conversion (ex. si l'utilisateur entre du texte au lieu d'un nombre)
                System.out.println("Erreur de conversion : " + e.getMessage());
            }
        }
    }

    // ********************************************************************
    // Méthode appelée pour importer les information d'un fichier text dans la tableview
    // ********************************************************************
    public void onImportButtonClick(ActionEvent event) {
        // On crée un objet File qui pointe vers le fichier "computer.txt"
        File file = new File("computer.txt");

        // On vérifie si le fichier existe avant d’essayer de le lire
        if (file.exists()) {

            // On vide la liste existante des ordinateurs (on recommence à zéro)
            computerList.clear();

            // Bloc try-with-resources : permet d’ouvrir automatiquement un fichier et le refermer correctement
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;

                // On lit le fichier ligne par ligne tant qu'il y a du contenu
                while ((line = reader.readLine()) != null) {

                    // On découpe la ligne en plusieurs morceaux en utilisant le point-virgule comme séparateur
                    String[] data = line.split(";");

                    // On vérifie qu’on a bien 6 éléments (nom, modèle, mémoire, nb processeurs, disque dur, OS)
                    if (data.length == 8) {
                        try {
                            // On récupère chaque champ de données et on le convertit si nécessaire
                            String name = data[0];                // Nom de l'ordinateur
                            String model = data[1];               // Modèle
                            int memory = Integer.parseInt(data[2]);         // Mémoire (RAM), en nombre
                            int nbProcessors = Integer.parseInt(data[3]);   // Nombre de processeurs
                            int hdd = Integer.parseInt(data[4]);            // Capacité disque dur
                            String os = data[5];                 // Système d’exploitation
                            String ip = data[6];
                            String mask = data[7];

                            // On crée un nouvel objet Computer avec les données récupérées
                            Computer pc = new Computer(name, model, memory, nbProcessors, hdd, os, ip, mask);

                            // On ajoute cet ordinateur à la liste
                            computerList.add(pc);

                        } catch (NumberFormatException e) {
                            // Si une conversion en nombre échoue, on affiche un message d’erreur et on ignore la ligne
                            System.out.println("Ligne ignorée : erreur de format dans les nombres");
                        }
                    }
                    // Si la ligne ne contient pas exactement 6 éléments, elle est ignorée sans message ici
                }

            } catch (IOException e) {
                // Si une erreur arrive pendant la lecture du fichier, on affiche la trace de l’erreur
                e.printStackTrace();
            }

            // On actualise l’affichage de la table pour que les nouveaux objets soient visibles
            tableView.refresh();
        }
    }

    // ********************************************************************
    // Méthode appelée pour exporter les information de la tableview dans un fichier text
    // ********************************************************************
    public void onExportButtonClick(ActionEvent event) {
        // On crée un objet "File" qui représente le fichier dans lequel on va écrire
        // Ici, le fichier s'appelle "computer.txt" et sera créé dans le dossier du projet
        File file = new File("computer.txt");

        // On utilise un bloc try-with-resources pour ouvrir un PrintWriter
        // Cela permet d'écrire du texte dans le fichier, et il se fermera automatiquement à la fin
        try (PrintWriter writer = new PrintWriter(file)) {

            // On parcourt la liste des ordinateurs (computerList)
            for (Computer pc : computerList) {

                // Pour chaque ordinateur, on écrit ses informations dans le fichier
                // Chaque donnée est séparée par un point-virgule pour faire un format lisible (comme un CSV)
                writer.println(
                        pc.getName() + ";" +         // Nom de l'ordinateur
                                pc.getModel() + ";" +        // Modèle
                                pc.getMemory() + ";" +       // Mémoire RAM
                                pc.getNbProcessors() + ";" + // Nombre de processeurs
                                pc.getHDD() + ";" +          // Capacité du disque dur
                                pc.getOS() + ";" +           // Système d'exploitation
                                pc.getIp() + ";" +
                                pc.getMask()
                );
            }

            // Si tout se passe bien, on affiche un message de confirmation dans la console
            System.out.println("Export réussi dans " + file.getAbsolutePath());

        } catch (IOException e) {
            // Si une erreur se produit (par exemple, impossible de créer ou écrire dans le fichier),
            // on affiche un message d'erreur avec le détail
            System.out.println("Erreur lors de l'export : " + e.getMessage());
        }
    }

    // ********************************************************************
    // Méthode pour remplir les champs d'un formulaire avec les informations d'un objet "Computer"
    // ********************************************************************
    private void fillFieldsWithComputer(Computer pc) {
        // Remplit le champ texte du nom avec le nom de l'ordinateur
        txtName.setText(pc.getName());

        // Remplit le champ texte du modèle avec le modèle de l'ordinateur
        txtModel.setText(pc.getModel());

        // -------- Gérer la sélection de la RAM avec des boutons radio --------
        // Parcourt tous les boutons radio disponibles dans le groupe tgRAMqty
        for (Toggle toggle : tgRAMqty.getToggles()) {

            // Cast (conversion) du Toggle en RadioButton
            RadioButton rb = (RadioButton) toggle;

            // Si le texte du bouton radio correspond à la quantité de RAM du PC...
            if (rb.getText().equals(String.valueOf(pc.getMemory()))) {

                // ...alors on sélectionne ce bouton radio
                tgRAMqty.selectToggle(rb);

                // Et on sort de la boucle car on a trouvé le bon bouton
                break;
            }
        }

        // Définit la valeur du spinner (ou champ numérique) pour le nombre de processeurs
        spNbCores.setValue(pc.getNbProcessors());

        // Convertit l'espace de stockage (en nombre) en texte, puis le place dans le champ texte
        txtStorage.setText(String.valueOf(pc.getHDD()));

        // Définit le système d'exploitation sélectionné dans la liste déroulante
        cbOSChoice.setValue(pc.getOS());

        txtAddIpAddress.setText(pc.getIp());

        txtAddMask.setText(pc.getMask());

        txtGetIpAddress.setText(pc.getIp());
    }

    // ********************************************************************
    // Méthode pour ping un hote
    // ********************************************************************
    @FXML
    public void onPingButtonClick(ActionEvent event) throws IOException {
        // Change la couleur du cercle (clePingResult) en bleu pour indiquer que le test est en cours
        clePingResult.setFill(Color.BLUE);

        // Récupère le texte que l'utilisateur a entré dans le champ de texte (par exemple : une adresse IP)
        String ipAddress = txtAddIpAddress.getText();

        // Utilise une méthode "ping" de l'objet "myNetworkCard" pour tester si l'adresse IP est joignable
        String result = myNetworkCard.ping(ipAddress);

        // Vérifie si le texte du résultat contient "Joiniable" (ce qui signifie que l'adresse a répondu au ping)
        if (result.contains("Joiniable")) {
            // Si le ping a réussi, on change la couleur du cercle en vert pour montrer que tout fonctionne
            clePingResult.setFill(Color.GREEN);
        }
        // Sinon, si le résultat contient "Inaccessible", cela signifie que l'adresse IP n’a pas répondu
        else if (result.contains("Inaccessible")) {
            // Dans ce cas, on met le cercle en rouge pour indiquer un échec
            clePingResult.setFill(Color.RED);
        }

        // Après 5 secondes, on remet la couleur du cercle à bleu pour réinitialiser l'état visuel
        resetCircleColorAfterDelay();
    }

    // ********************************************************************
    // Méthode appelée lorsque l'utilisateur clique sur le bouton "Calculer le sous-réseau"
    // ********************************************************************
    public void onCalculateSubnetButtonClick(ActionEvent event) {

        // Récupère l'adresse IP saisie dans le champ de texte de l'interface graphique
        String ipAddress = txtAddIpAddress.getText();

        // Récupère le masque de sous-réseau saisi par l'utilisateur
        String mask = txtAddMask.getText();

        // Applique l'adresse IP et le masque à la "carte réseau" (un objet qui gère les infos réseau)
        myNetworkCard.setIpAddress(ipAddress);
        myNetworkCard.setMask(mask);

        // Calcule l'adresse du sous-réseau à partir de l'adresse IP et du masque
        // Exemple : IP 192.168.1.10 et masque 255.255.255.0 → adresse de sous-réseau 192.168.1.0
        String subNetAddress;
        subNetAddress = myNetworkCard.getSubNetAddress();

        // Crée une boîte de dialogue (pop-up) pour afficher le résultat à l'utilisateur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        // Définit le titre de la fenêtre pop-up
        alert.setTitle("Résultats du sous-réseau");

        // Définit le texte d'en-tête de la boîte de dialogue
        alert.setHeaderText("Détails du sous-réseau");

        // Prépare le message à afficher avec l'adresse du sous-réseau calculée
        String message = "Adresse du sous-réseau : " + subNetAddress;

        // Met le message dans la boîte de dialogue
        alert.setContentText(message);

        // Affiche la boîte de dialogue et attend que l'utilisateur la ferme
        alert.showAndWait();
    }

    // ********************************************************************
    // Méthode appelée lorsqu'on clique sur un bouton pour obtenir l'adresse MAC
    // ********************************************************************
    public void onGetButtonClickMac(ActionEvent event) {

        // Récupère le texte saisi dans le champ texte prévu pour l'adresse IP
        String ipAddress = txtGetIpAddress.getText();

        // Appelle la méthode (getMACAddress) de l'objet myNetworkCard pour obtenir l'adresse MAC
        String result = myNetworkCard.getMACAddress(ipAddress);

        // Affiche le résultat (l'adresse MAC obtenue) dans un autre champ texte
        txtGetMacAddress.setText(result);
    }

    // ********************************************************************
    // Méthode privée qui remet la couleur du cercle en bleu après un délai
    // ********************************************************************
    private void resetCircleColorAfterDelay() {
        // Crée une pause (temporisation) de 5 secondes avant d’exécuter une action
        PauseTransition pause = new PauseTransition(Duration.seconds(5));

        // Déclare ce qu’il faut faire une fois que les 5 secondes sont écoulées :
        pause.setOnFinished(event -> clePingResult.setFill(Color.BLUE));

        // Lance la pause. L’action définie juste au-dessus s'exécutera automatiquement après 5 secondes
        pause.play();
    }

    // ********************************************************************
    // Méthode appelée lorsqu’un élément est sélectionné dans une ComboBox (cbOSChoice)
    // ********************************************************************
    public void onCbOsChoice(ActionEvent event) {
        // Récupère l’option choisie (par exemple "Linux", "Windows", "MacOS")
        String selectedOs = cbOSChoice.getValue();

        // Si rien n’est sélectionné (valeur nulle), on quitte la méthode
        if (selectedOs == null) return;

        // Selon le système choisi, on associe le nom d’une image spécifique
        String imageName = switch (selectedOs) {
            case "Linux" -> "ImageLinux.png";
            case "Windows" -> "ImageWindows.png";
            case "MacOS" -> "ImageMacOS.png";
            default -> null; // Si aucun des cas ne correspond, aucune image
        };

        // Si un nom d’image a bien été trouvé (donc pas null)
        if (imageName != null) {
            // Construit le chemin de l’image à partir du dossier dans "resources"
            // Ce chemin commence par un "/" car on l’utilise avec getResourceAsStream
            String imagePath = "/images/" + imageName;

            // Essaie de charger le fichier image sous forme de flux de données
            InputStream stream = getClass().getResourceAsStream(imagePath);

            // Si l’image n’a pas été trouvée à ce chemin, on affiche un message d’erreur
            if (stream == null) {
                System.out.println(" Image non trouvée à : " + imagePath);
                imgOsChoice.setImage(null); // On vide l’image affichée si absente
                return; // On arrête ici car on ne peut pas afficher l’image
            }

            // Si le flux de l’image est valide, on crée une image JavaFX avec ce flux
            Image image = new Image(stream);

            // On affiche l’image dans le composant graphique prévu à cet effet (imgOsChoice)
            imgOsChoice.setImage(image);
        }
    }

    // ********************************************************************
    // Cette méthode est appelée lorsqu'on clique sur le bouton "Reset" (réinitialiser)
    // ********************************************************************
    public void onResetButtonClick(ActionEvent event) {
        txtName.clear();
        txtModel.clear();
        txtStorage.clear();
        txtAddIpAddress.clear();
        txtAddMask.clear();
        rbRAM8.setSelected(true);
        spNbCores.setValue(1);
        cbOSChoice.setValue(null);
        imgOsChoice.setImage(null);
    }

    // ********************************************************************
    // Méthode pour réinitialiser tous les champs d'un formulaire
    // ********************************************************************
    private void clearFields() {
        txtName.clear();
        txtModel.clear();
        rbRAM8.setSelected(true);
        txtStorage.clear();
        spNbCores.setValue(1);
        cbOSChoice.getSelectionModel().clearSelection();
        txtAddIpAddress.clear();
        txtAddMask.clear();
    }
}