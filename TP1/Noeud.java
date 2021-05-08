package TP1;

public class Noeud
{
    protected String nom;
    protected Noeud prochain;
    protected ListeValeur horizontale;
    protected int freq;

    protected Noeud(String n, Noeud p, ListeValeur h) // Noeud Vertical
    {
        nom = n.toLowerCase();
        prochain = p;
        horizontale = h;            // Référence vers la liste horizontale que le noeud vertical va contenir
    }
    protected Noeud(String n, Noeud p, int f)   // Noeud Horizontal
    {
        nom = n.toLowerCase();
        freq = f;
        prochain = p;
    }
}