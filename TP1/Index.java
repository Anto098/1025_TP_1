package TP1;

public class Index {
    protected ListeCle listeVerticale = new ListeCle();

    public void inserer(String cle, String valeur, int freq) {      // On insère les noeuds dans l'ordre afin de ne pas avoir à les trier constamment
        Noeud vNode = listeVerticale.trouver(cle);

        if (vNode==null) {
            /*Si le noeud vertical "cle" n'existe pas, on l'ajoute et on lui assigne une
            nouvelle liste horizontale contenant uniquement le noeud avec "valeur" et "freq" */
            ListeValeur horizontale = new ListeValeur();
            horizontale.inserer(valeur, freq);
            listeVerticale.inserer(cle, horizontale);
        }
        else {
            /*Le noeud vertical existe et on veut trouver si le noeud contenant "valeur" et "int" existe,
            sinon on ajoute un noeud horizontal*/
            Noeud hNode = vNode.horizontale.trouver(valeur);
            if (hNode == null) vNode.horizontale.inserer(valeur, freq);
            else hNode.freq += freq;
        }
    }

}
