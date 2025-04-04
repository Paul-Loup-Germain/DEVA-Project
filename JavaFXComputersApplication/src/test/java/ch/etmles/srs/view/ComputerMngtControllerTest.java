package ch.etmles.srs.view;

import ch.etmles.srs.buisness.Computer;
import ch.etmles.srs.buisness.NetworkCard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Slider;
import java.lang.reflect.Field;
import javafx.application.Platform;

/**
 * Exemple de test "maison" SANS librairie externe (type JUnit),
 * utilisant la réflexion pour accéder aux champs privés.
 */
public class ComputerMngtControllerTest {

    public static void main(String[] args) {
        Platform.startup(() -> {
            testAddComputer();
            testCalculateSubnet();
            // Vous pouvez enchaîner vos autres tests ici

            // Une fois vos tests terminés, vous pouvez éventuellement fermer la plateforme :
            Platform.exit();
        });
    }

    /**
     * Démonstration : on teste l'ajout d'un nouvel ordinateur
     * via onAddButtonClick().
     */
    private static void testAddComputer() {
        System.out.println("=== testAddComputer ===");

        try {
            // 1) Instancier le contrôleur
            ComputerMngtController controller = new ComputerMngtController();

            // 2) Créer manuellement les champs JavaFX
            TextField nameField    = new TextField("PC de Test");
            TextField modelField   = new TextField("Model X");
            TextField storageField = new TextField("512");
            Slider   sliderCores   = new Slider(1, 2, 3);
            ComboBox<String> comboOS = new ComboBox<>();
            comboOS.setItems(FXCollections.observableArrayList("Linux", "Windows", "MacOS"));
            comboOS.getSelectionModel().select("Windows");

            ToggleGroup ramGroup = new ToggleGroup();
            RadioButton rb8  = new RadioButton("8");
            RadioButton rb16 = new RadioButton("16");
            rb8.setToggleGroup(ramGroup);
            rb16.setToggleGroup(ramGroup);
            rb8.setSelected(true); // on choisit 8 Go

            // Par réflexion, récupérer le champ 'computerList'
            Field computerListField = ComputerMngtController.class.getDeclaredField("computerList");
            computerListField.setAccessible(true);

            ObservableList<Computer> computerList = (ObservableList<Computer>) computerListField.get(controller);

            TableView<Computer> table = new TableView<>();
            table.setItems(computerList);

            // 4) Injecter tout ça dans le contrôleur par réflexion
            setPrivateField(controller, "txtName",    nameField);
            setPrivateField(controller, "txtModel",   modelField);
            setPrivateField(controller, "txtStorage", storageField);
            setPrivateField(controller, "spNbCores",  sliderCores);
            setPrivateField(controller, "cbOSChoice", comboOS);
            setPrivateField(controller, "tgRAMqty",   ramGroup);
            setPrivateField(controller, "rbRAM8",     rb8);
            setPrivateField(controller, "tableView",  table);

            // 5) Appeler la méthode du contrôleur
            controller.onAddButtonClick(null);

            // 6) Vérifier le résultat : la liste doit contenir 1 ordinateur
            //    (et les infos doivent correspondre)
            if (table.getItems().size() == 1) {
                Computer c = table.getItems().get(0);
                if ("PC de Test".equals(c.getName())
                        && "Model X".equals(c.getModel())
                        && c.getMemory() == 8
                        && c.getHDD() == 512
                        && c.getNbProcessors() == 4
                        && "Windows".equals(c.getOS())) {
                    System.out.println("testAddComputer => SUCCÈS");
                } else {
                    System.out.println("testAddComputer => ÉCHEC: valeurs incorrectes dans le Computer créé.");
                }
            } else {
                System.out.println("testAddComputer => ÉCHEC: la table ne contient pas 1 ordinateur.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Teste la méthode onCalculateSubnetButtonClick()
     * qui utilise la NetworkCard pour calculer l’adresse de sous-réseau.
     */
    private static void testCalculateSubnet() {
        System.out.println("=== testCalculateSubnet ===");

        try {
            // 1) Instancier le contrôleur
            ComputerMngtController controller = new ComputerMngtController();

            // 2) Simuler les champs nécessaires
            TextField ipField   = new TextField("192.168.1.10");
            TextField maskField = new TextField("255.255.255.0");

            // 3) Injecter la NetworkCard (si besoin) + champs
            setPrivateField(controller, "myNetworkCard", new NetworkCard());
            setPrivateField(controller, "txtIPAddressConfiguration", ipField);
            setPrivateField(controller, "txtConfigurationMask",      maskField);

            // 4) Appeler la méthode
            controller.onCalculateSubnetButtonClick(null);

            // 5) Vérifier le résultat
            //    On s'attend à ce que le sous-réseau soit 192.168.1.0
            //    après calcul dans la NetworkCard
            Field netCardField = ComputerMngtController.class.getDeclaredField("myNetworkCard");
            netCardField.setAccessible(true);
            NetworkCard netCard = (NetworkCard) netCardField.get(controller);

            String subNet = netCard.getSubNetAddress();
            if ("192.168.1.0".equals(subNet)) {
                System.out.println("testCalculateSubnet => SUCCÈS");
            } else {
                System.out.println("testCalculateSubnet => ÉCHEC: sous-réseau obtenu = " + subNet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Petite méthode utilitaire pour "forcer" l’injection
     * d’un champ privé via réflexion.
     */
    private static void setPrivateField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
