/* FICHIER CARTTE.JAVA :
 *      - FICHIER DE CREATION DE CARTES
 * 
 *  DERNIÈRE MÀJ : 26/03/2019 par ROMAIN MONIER
 *  CRÉÉ PAR JULIE BALOUET
 *  2018/2019
 * ------------------------------------------
 *  INFOS :
 *      - CLASSE CARTE
 * ------------------------------------------
 */

package blackjack;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Point;

/** Classe de l'objet Carte
 * @author Julie BALOUET
 */
public class Carte extends JPanel
{    
    private Couleur couleur;
    private int num, compte;
    private ImageIcon image;
    private Point position;
    private JLabel label_image;
   
	/**
	 * Constructeur
     * @author Julie BALOUET
     * @param couleur Couleur / symbole de la carte
     * @param num Le numéro de la carte
     * @param compte La valeur de RC de la carte
     * @param im L'image de la carte
	*/
    public Carte(Couleur couleur, int num, int compte, ImageIcon im)
    {
        this.couleur = couleur;
        this.compte = compte;
        this.num = num;
        this.image = im;
        this.position = new Point(0,0);
        
        this.label_image = new JLabel(image);
        this.add(label_image);
        this.setBounds(position.x, position.y, image.getIconWidth(), image.getIconHeight());
        this.setVisible(false);
    }
    
    /**
	 * Modifie la position de la carte
     * @author Romain MONIER
     * @param pos La nouvelle position
	*/  
    public void setPosition(Point pos)
    {
        this.position = pos;
        this.setBounds(position.x, position.y, image.getIconWidth(), image.getIconHeight());
    }
        
    /**
	 * Retourne la couleur de la carte
     * @author Julie BALOUET
     * @return La couleur 
	*/
    public Couleur getCouleur()
    {
        return this.couleur;
    }
    
    /**
	 * Retourne la valeur de la carte
     * @author Julie BALOUET
     * @return La valeur de la carte 
	*/
    public int getValeur()
    {
        return this.num;
    }
    
    /**
	 * Retourne la valeur du RC de la carte
     * @author Julie BALOUET
     * @return RC 
	*/
    public int getCompte()
    {
        return this.compte;
    }
}
