package TP1;

import java.io.File;
import java.util.Arrays;

public class Engin {

    protected Index index = new Index();
    protected Index indexInv = new Index();

    public void indexer (String [] mots){
        if(mots.length < 2)                                                 // S'il n'y a pas de path, on dit que c'est une erreur
            System.out.println("Erreur, veuillez saisir un fichier/repertoire");
        else {                                                              // S'il y a un path
            File file = new File(mots[1]);
            if(file.isFile())                                               // On regarde si le path est un fichier
                Lecture.lire_lignes(mots[1], this, file.getName());  // Si oui, on le traite
            else if(file.isDirectory() ){                                   // On regarde si le path est un répertoire
                String [] liste = file.list();

                for(String fichier : liste){                                // Si le fichier se termine par .txt, on le traite, sinon on continue de boucler
                    if(fichier.endsWith(".txt") ) {
                        Lecture.lire_lignes(mots[1] + "/" + fichier, this, fichier);
                    }
                }

            }
            else System.out.println("Veuillez specifier un path valide pour la fonction indexer"); // Sinon, le path est invalide
        }
    }

    public void indexerInv(){
        indexInv = new Index();                                         //on réinitialise la liste inversée à une liste vide
        if(index.listeVerticale.premier == null) return;                // Si le premier est vide, on ne peut pas l'inverser, donc on termine l'exécution de la fonction ici
        Noeud vNode = index.listeVerticale.premier;
        while(vNode != null){                                           // On boucle sur la liste verticale, tant que le noeud vertical n'est pas nul
            if(vNode.horizontale == null) {                             // Si la liste horizontale est nulle on passe au prochain noeud vertical sans faire le reste de la boucle
                vNode = vNode.prochain;
                continue;
            }
            Noeud hNode = vNode.horizontale.premier;                    // Si la liste horizontale existe, on insère tous ses noeuds horizontaux dans la liste inversée en tant que noeuds verticaux
            while (hNode != null){                                      // Le mot devient la clef, le document devient la valeur et la fréquence reste la même
                indexInv.inserer(hNode.nom,vNode.nom,hNode.freq);
                hNode = hNode.prochain;
            }
            vNode = vNode.prochain;
        }
    }

    public void afficher() {
        ListeCle vList = new ListeCle();
        for(int i = 0; i<2; i++) {                                      // boucle qui va permettre d'afficher les 2 index plutôt que de répéter du code

            if(i==0)  vList = index.listeVerticale;
            else  vList = indexInv.listeVerticale;

            if (vList.premier == null)                                  // Si le premier n'existe pas on ne peut rien print ... message d'erreur
                System.out.println("Erreur: soit rien n'a ete indexe ou la liste n'a pas ete inversee");
            else {
                Noeud vNoeud = vList.premier;                           // Si le premier existe, on dit que le premier noeud horizontal est le premier de la liste (soit index normal ou inversé)

                while (vNoeud != null) {                                // On fait 2 boucles while qui vont passer sur les noeuds verticaux et horizontaux et afficher les noeuds un par un
                    System.out.println("\nNom de la clef : " + vNoeud.nom);
                    ListeValeur hList = vNoeud.horizontale;
                    Noeud hNoeud = hList.premier;
                    while (hNoeud != null) {
                        System.out.println("    Valeur : " + hNoeud.nom + " Frequence : " + hNoeud.freq);
                        hNoeud = hNoeud.prochain;
                    }
                    vNoeud = vNoeud.prochain;
                }
            }

            if (i==0) System.out.println("=============================================================================");
        }

    }

    public ListeValeur rechercher(String [] mots) {                             // Si la liste n'a pas été inversée, on retourne une liste vide et un message d'erreur
        if (indexInv.listeVerticale.premier == null) {
            System.out.println("Vous n'avez pas indexe de documents ou vous avez oublie d'inverser les listes avant d'appeler la commande de recherche.");
            return new ListeValeur();
        }

        ListeValeur listeReponse = new ListeValeur();
        if (indexInv.listeVerticale.trouver(mots[0]) == null){                  //Si le 1er mot de la recherche n'appartient a aucun document, la recherche renvoie une liste vide
            System.out.println("Aucun document ne contient la recherche");
            return listeReponse;
            }
        Noeud premierMot = indexInv.listeVerticale.trouver(mots[0]);            // On crée une nouvelle liste dans laquelle on copie le contenu de la liste horizontale du premier mot qui correspond à la recherche
        Noeud hNoeud = premierMot.horizontale.premier;                          // pour ne pas référencer au même objet (ce qui crée des bugs)
        while(hNoeud != null) {
            listeReponse.inserer(hNoeud.nom,hNoeud.freq);
            hNoeud=hNoeud.prochain;
        }

        for (String mot : Arrays.copyOfRange(mots,1, mots.length)) {      //on boucle a partir du 2eme mot de la recherche (parce que le premier est deja traité plus haut)
            Noeud vNoeud = indexInv.listeVerticale.trouver(mot);                //vNoeud est la liste des documents qui contiennent le mot de la recherche
            if (vNoeud == null) {
                System.out.println("Un des mots de la recherche n'appartient à aucun document");
                return new ListeValeur();                                       //Si un mot de la recherche n'appartient a aucun document, la recherche renvoie une liste vide
            }
                Noeud hNoeudReponse = listeReponse.premier;                     //hNoeudReponse est un document appartenant a la liste listeReponse

            while(hNoeudReponse != null) {                                      //On boucle sur les documents de listeReponse
                Noeud documentContientMot = vNoeud.horizontale.trouver(hNoeudReponse.nom);  //On veut savoir si un document qui est actuellement dans la reponse contient le prochain mot ou non
                if ((documentContientMot != null)) {                            //si le document contient le prochain mot de la recherche, alors il reste dans listeReponse et on lui ajoute la frequence du prochain mot
                    hNoeudReponse.freq += documentContientMot.freq;
                }else                                                           //si le document ne contient pas le prochain mot, on ne le veut pas dans la reponse
                    listeReponse.enlever(hNoeudReponse.nom);

                hNoeudReponse=hNoeudReponse.prochain;
            }
        }

        listeReponse.trierFreq();                                                // On trie la liste de réponses
        Noeud n = listeReponse.premier;
        while(n != null) {                                                       // On affiche le résultat de la recherche
            System.out.println("Document : "+ n.nom + "\n    Frequence : "+n.freq);
            n=n.prochain;
        }
        return listeReponse;
    }

}