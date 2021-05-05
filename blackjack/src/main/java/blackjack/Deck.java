/* FICHIER DECK.JAVA :
 *      - FICHIER DE CREATION DE DECKS
 * 
 *  DERNIÈRE MÀJ : 27/03/2019 par ROMAIN MONIER et JULIE BALOUET
 *  CRÉÉ PAR JULIE BALOUET
 *  2018/2019
 * ------------------------------------------
 *  INFOS :
 *      - CLASSE DECK
 * ------------------------------------------
 */

package blackjack;
 
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/** Classe de l'objet Deck
 * @author Julie BALOUET
 */
public class Deck
{
    private ArrayList<Carte> deck;
    
	/**
	 * Constructeur, appelle regenerer()
     * @author Julie BALOUET
	*/
    public Deck() throws IOException
    {
        deck = new ArrayList<Carte>();
        
        regenerer();
    }
    
    /**
	 * Retourne le deck de cartes
     * @author Julie BALOUET
     * @return Le deck
	*/
    public ArrayList<Carte> getDeck()
    {
        return deck;
    }   
    
    /**
	 * Génère ou regénère les cartes du deck (52 cartes)
     * @author Julie BALOUET, Morgane BOUCHARD
	*/
    public void regenerer() throws IOException
    {
        Carte asPique = new Carte(Couleur.PIQUE, 1, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/as_pique.png"))));
        deck.add(asPique);
        Carte asCoeur = new Carte(Couleur.COEUR, 1, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/as_coeur.png"))));
        deck.add(asCoeur);
        Carte asTrefle = new Carte(Couleur.TREFLE, 1, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/as_trefle.png"))));
        deck.add(asTrefle);
        Carte asCarreau = new Carte(Couleur.CARREAU, 1, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/as_carreau.png"))));
        deck.add(asCarreau);
        //AS
        
        Carte deuxPique = new Carte(Couleur.PIQUE,2, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/2_pique.png"))));
        deck.add(deuxPique);
        Carte deuxCoeur = new Carte(Couleur.COEUR,2, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/2_coeur.png"))));
        deck.add(deuxCoeur);
        Carte deuxTrefle = new Carte(Couleur.TREFLE,2, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/2_trefle.png"))));
        deck.add(deuxTrefle);
        Carte deuxCarreau = new Carte(Couleur.CARREAU,2, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/2_carreau.png"))));
        deck.add(deuxCarreau);
        //DEUX
        
        Carte troisPique = new Carte(Couleur.PIQUE,3, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/3_pique.png"))));
        deck.add(troisPique);
        Carte troisCoeur = new Carte(Couleur.COEUR,3, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/3_coeur.png"))));
        deck.add(troisCoeur);
        Carte troisTrefle = new Carte(Couleur.TREFLE,3, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/3_trefle.png"))));
        deck.add(troisTrefle);
        Carte troisCarreau = new Carte(Couleur.CARREAU,3, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/3_carreau.png"))));
        deck.add(troisCarreau);
        //TROIS
        
        Carte quatrePique = new Carte(Couleur.PIQUE,4, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/4_pique.png"))));
        deck.add(quatrePique);
        Carte quatreCoeur = new Carte(Couleur.COEUR,4, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/4_coeur.png"))));
        deck.add(quatreCoeur);
        Carte quatreTrefle = new Carte(Couleur.TREFLE,4, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/4_trefle.png"))));
        deck.add(quatreTrefle);
        Carte quatreCarreau = new Carte(Couleur.CARREAU,4, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/4_carreau.png"))));
        deck.add(quatreCarreau);
        //QUATRE
        
        Carte cinqPique = new Carte(Couleur.PIQUE,5, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/5_pique.png"))));
        deck.add(cinqPique);
        Carte cinqCoeur = new Carte(Couleur.COEUR,5, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/5_coeur.png"))));
        deck.add(cinqCoeur);
        Carte cinqTrefle = new Carte(Couleur.TREFLE,5, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/5_trefle.png"))));
        deck.add(cinqTrefle);
        Carte cinqCarreau = new Carte(Couleur.CARREAU,5, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/5_carreau.png"))));
        deck.add(cinqCarreau);
        //CINQ
        
        Carte sixPique = new Carte(Couleur.PIQUE,6, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/6_pique.png"))));
        deck.add(sixPique);
        Carte sixCoeur = new Carte(Couleur.COEUR,6, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/6_coeur.png"))));
        deck.add(sixCoeur);
        Carte sixTrefle = new Carte(Couleur.TREFLE,6, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/6_trefle.png"))));
        deck.add(sixTrefle);
        Carte sixCarreau = new Carte(Couleur.CARREAU,6, 1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/6_carreau.png"))));
        deck.add(sixCarreau);
        //SIX
        
        Carte septPique = new Carte(Couleur.PIQUE,7, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/7_pique.png"))));
        deck.add(septPique);
        Carte septCoeur = new Carte(Couleur.COEUR,7, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/7_coeur.png"))));
        deck.add(septCoeur);
        Carte septTrefle = new Carte(Couleur.TREFLE,7, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/7_trefle.png"))));
        deck.add(septTrefle);
        Carte septCarreau = new Carte(Couleur.CARREAU,7, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/7_carreau.png"))));
        deck.add(septCarreau);
        //SEPT
        
        Carte huitPique = new Carte(Couleur.PIQUE,8, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/8_pique.png"))));
        deck.add(huitPique);
        Carte huitCoeur = new Carte(Couleur.COEUR,8, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/8_coeur.png"))));
        deck.add(huitCoeur);
        Carte huitTrefle = new Carte(Couleur.TREFLE,8, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/8_trefle.png"))));
        deck.add(huitTrefle);
        Carte huitCarreau = new Carte(Couleur.CARREAU,8, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/8_carreau.png"))));
        deck.add(huitCarreau);
        //HUIT
        
        Carte neufPique = new Carte(Couleur.PIQUE,9, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/9_pique.png"))));
        deck.add(neufPique);
        Carte neufCoeur = new Carte(Couleur.COEUR,9, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/9_coeur.png"))));
        deck.add(neufCoeur);
        Carte neufTrefle = new Carte(Couleur.TREFLE,9, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/9_trefle.png"))));
        deck.add(neufTrefle);
        Carte neufCarreau = new Carte(Couleur.CARREAU,9, 0, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/9_carreau.png"))));
        deck.add(neufCarreau);
        //NEUF
        
        Carte dixPique = new Carte(Couleur.PIQUE,10, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/10_pique.png"))));
        deck.add(dixPique);
        Carte dixCoeur = new Carte(Couleur.COEUR,10, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/10_coeur.png"))));
        deck.add(dixCoeur);
        Carte dixTrefle = new Carte(Couleur.TREFLE,10, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/10_trefle.png"))));
        deck.add(dixTrefle);
        Carte dixCarreau = new Carte(Couleur.CARREAU,10, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/10_carreau.png"))));
        deck.add(dixCarreau);
        //DIX
        
        
        Carte valetPique = new Carte(Couleur.PIQUE,11, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/valet_pique.png"))));
        deck.add(valetPique);
        Carte valetCoeur = new Carte(Couleur.COEUR,11, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/valet_coeur.png"))));
        deck.add(valetCoeur);
        Carte valetTrefle = new Carte(Couleur.TREFLE,11, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/valet_trefle.png"))));
        deck.add(valetTrefle);
        Carte valetCarreau = new Carte(Couleur.CARREAU,11, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/valet_carreau.png"))));
        deck.add(valetCarreau);
        //VALET
        
        Carte damePique = new Carte(Couleur.PIQUE,12, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/reine_pique.png"))));
        deck.add(damePique);
        Carte dameCoeur = new Carte(Couleur.COEUR,12, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/reine_coeur.png"))));
        deck.add(dameCoeur);
        Carte dameTrefle = new Carte(Couleur.TREFLE,12, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/reine_trefle.png"))));
        deck.add(dameTrefle);
        Carte dameCarreau = new Carte(Couleur.CARREAU,12, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/reine_carreau.png"))));
        deck.add(dameCarreau);
        //DAME
        
        Carte roiPique = new Carte(Couleur.PIQUE,13, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/roi_pique.png"))));
        deck.add(roiPique);
        Carte roiCoeur = new Carte(Couleur.COEUR,13, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/roi_coeur.png"))));
        deck.add(roiCoeur);
        Carte roiTrefle = new Carte(Couleur.TREFLE,13, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/roi_trefle.png"))));
        deck.add(roiTrefle);
        Carte roiCarreau = new Carte(Couleur.CARREAU,13, -1, new ImageIcon(ImageIO.read(Blackjack.getResourceStream("img/cartes/roi_carreau.png"))));
        deck.add(roiCarreau);
        //ROI 
    }
    
    /**
	 * Retourne une carte aléatoire dans le deck ou null si le deck est vide
     * @author Julie BALOUET, Romain MONIER
     * @return Carte aléatoire du deck ou null
	*/
    public Carte getCarteAleatoire()
    {
        int n = (int)(Math.random() * deck.size());
        
        if(deck.isEmpty()){
            return null;
        }
        else {
            Carte carte = deck.get(n);
            deck.remove(n);
            return carte;
        }
    }
}
