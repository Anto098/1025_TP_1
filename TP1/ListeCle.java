package TP1;

public class ListeCle {

    protected Noeud premier;

    protected Noeud trouver(String name)        // méthode pour trouver un string à partir de son nom (Clef), fournie sur Studium
    {
        Noeud n = premier;
        while (n != null && !n.nom.equals(name) )
            n = n.prochain;
        return n;
    }

    protected void inserer(String nom, ListeValeur l) // compare to : plus petit que 0, a est avant b
                                                   //              plus grand que 0, b est avant a
    {
        if (premier == null) premier = new Noeud(nom, null, l); // première fonction Insérer, elle prend en paramètres le nom (Clef) et une liste Horizontale.
        else inserer(nom,premier,l);                                // Elle appelle une deuxième fonction qui va lui donner en argument le premier. On le fait en 2 fonctions
    }                                                               // pour ne pas avoir à donner le premier en argument dans la première à chaque fois

    private void inserer (String nom, Noeud n, ListeValeur l){      // Méthode privée car récursive, on veut que seulement la méthode insérer (protected) puisse l'appeler
        if (n.prochain == null) n.prochain = new Noeud (nom, null, l);  // Si on arrive à la fin de la liste, on insère le noeud
        else
        {
            if (nom.compareTo(n.prochain.nom) < 0)                  // Sinon, si le nom qu'on veut insérer est avant (lexicographiquement) le prochain mot dans la liste
                n.prochain = new Noeud(nom, n.prochain, l);         // on place le nom à insérer entre le nom courant et son prochain
            else
                inserer(nom,n.prochain,l);                          // Sinon, on continue de boucler ( jusqu'à éventuellement insérer ce noeud dans la liste de façon triée)
        }
    }

}
