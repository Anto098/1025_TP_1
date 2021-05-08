package TP1;

public class ListeValeur {
    protected Noeud premier;

    protected Noeud trouver(String name) {          // méthode pour trouver un string à partir de son nom (valeur), fournie sur Studium
        Noeud n = premier;
        while (n != null && !n.nom.equals(name) )
            n = n.prochain;
        return n;
    }

    public void inserer(String nom, int f) // compare to : plus petit que 0, a est avant b
                                           //              plus grand que 0, b est avant a
    {
        if (premier == null) premier = new Noeud(nom, null, f); // première fonction insérer, elle prend en argument le nom (valeur) du Noeud ainsi que sa fréquence
        else inserer(nom,premier,f);                                // On appelle la deuxième fonction insérer (private car elle sera utilisée pour la récursion)
    }                                                               // On lui donne le premier de la liste en argument pour pouvoir boucler sur la liste

    private void inserer (String nom, Noeud n, int f){

        if (n.prochain == null) n.prochain = new Noeud (nom, null, f); // Si on arrive à la fin de la liste, on insère le nouveau noeud à la fin
        else if (nom.compareTo(n.prochain.nom) == 0)                       // Si un noeud existe avec le même nom, on incrémente juste la fréquence du noeud existant
            n.freq += 1;
        else
        {
            if (nom.compareTo(n.prochain.nom) < 0)                          // Sinon, si le nom qu'on veut insérer est avant (lexicographiquement) le prochain mot dans la liste
                n.prochain = new Noeud(nom, n.prochain, f);                 // on place le nom à insérer entre le nom courant et son prochain
            else
                inserer(nom,n.prochain,f);                                  // Sinon, on continue de boucler ( jusqu'à éventuellement insérer ce noeud dans la liste de façon triée)
        }
    }

    protected void enlever(String nom) {                    // Méthode enlever fournie sur Studium, on cherche le nom (valeur) d'un Noeud en bouclant sur la liste et on l'enlève
        Noeud n = premier;
        if (premier == null) return;
        if (premier.nom == nom)
        {
            premier = premier.prochain;
            return;
        }
        while (n.prochain != null && n.prochain.nom != nom) n = n.prochain;
        if (n.prochain != null) n.prochain = n.prochain.prochain;
    }

    public void trierFreq() {
        //On cree une nouvelle liste horizontale a laquelle on veut ajouter au fur
        //et a mesure les noeuds de la liste non-triee par frequence
        ListeValeur nvListe = new ListeValeur();

        //On commence par le premier noeud de la liste non-triee
        if(premier==null) return;
        trierFreq(premier, nvListe);

        //On assigne le premier de la nouvelle liste au premier de l'ancienne
        premier=nvListe.premier;
    }

    private void trierFreq(Noeud startNode, ListeValeur nvListe) {
        //Si il n'y a plus d'autre noeuds a ajouter dans la nouvelle liste, on s'arrete
        if (startNode == null) return;

        //On trie le prochain noeud
        trierFreq(startNode.prochain, nvListe);

        //On insere le noeud dans la nouvelle liste
        insererFreq(startNode, nvListe, nvListe.premier);
    }

    private void insererFreq(Noeud nodeToInsert, ListeValeur nvListe, Noeud n) {
        //Si la nouvelle liste est vide...
        if (nvListe.premier==null)
            nvListe.premier = new Noeud(nodeToInsert.nom,null,nodeToInsert.freq);

        //Si la frequence du premier est plus petite, on change le premier
        else if (nodeToInsert.freq > nvListe.premier.freq)
            nvListe.premier = new Noeud(nodeToInsert.nom,nvListe.premier,nodeToInsert.freq);

        //Si il n'y a pas de prochain, alors la nouvelle freq est la plus petite
        else if (n.prochain==null)
            n.prochain = new Noeud(nodeToInsert.nom,null,nodeToInsert.freq);

        //Sinon, on ajoute le noeud en ordre decroissant des frequences
        else if (nodeToInsert.freq >= n.prochain.freq)
            n.prochain = new Noeud(nodeToInsert.nom, n.prochain, nodeToInsert.freq);

        //Sinon, on continue a boucler
        else
            insererFreq(nodeToInsert,nvListe,n.prochain);
    }
}
