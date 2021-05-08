package TP1;
import java.util.Arrays;

public class Commandes {                                                        // Cet objet dicte ce qui doit être appelé lorsqu'on tape une commande dans la ligne de commandes
    public static void traiterCommande (String commande,Engin engin){
        String mots [] = commande.split(" ");
        switch (mots[0]) {
            case "indexer":    engin.indexer(mots);
                               break;
            case "inverser":   engin.indexerInv();
                               break;
            case "afficher":   engin.afficher();
                               break;
            case "rechercher": engin.rechercher(Arrays.copyOfRange(mots,1, mots.length));   // On passe en argument les mots sauf le mot qui lance la commande ("rechercher")
                               break;
            case "end":        System.exit(0);
            default : System.out.println("Veuillez selectionner une des commandes suivantes : " +
                                         "\n -indexer {fichier/repertoire}" +
                                         "\n -inverser" +
                                         "\n -afficher" +
                                         "\n -rechercher {requete}" +
                                         "\n -end");
                               break;
        }
    }
}
