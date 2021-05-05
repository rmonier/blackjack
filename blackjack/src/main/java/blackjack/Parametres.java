/* FICHIER PARAMETRES.JAVA :
 *      - PARAMETRAGE SONS
 *
 *  DERNIÈRE MÀJ : 09/04/2019 par ROMAIN MONIER
 *  CRÉÉ PAR ROMAIN MONIER
 *  2018/2019
 * ------------------------------------------
 *  INFOS :
 *      - 
 * ------------------------------------------
 */

package blackjack;

/** Réglages dans options
 * @author Romain MONIER
 */
public class Parametres
{
	/*	ATTRIBUTS STATIQUES */
	
	private static boolean s_son, s_musique, s_comptage;
	private static BaseDeDonnees s_bdd;
    
    /*  INITIALISATION  */
    
	/**
     * Initialise Parametres en récupérant les valeurs enregistrées
     * @author Romain MONIER
     * @param bdd La base de données
     */
    public static void init(BaseDeDonnees bdd)
    {
        s_bdd = bdd;
        
        /*  MUSIQUE  */
        
        int musique = s_bdd.recup_audio(true);
        
        if(musique == 1)
            s_musique = true;
        else if(musique == 0)
            s_musique = false;
        else{
            s_musique = true;
        }
            
        /*  SONS  */
        
        int son = s_bdd.recup_audio(false);
        
        if(son == 1)
            s_son = true;
        else if(son == 0)
            s_son = false;
        else{
            s_son = true;
        }
            
        /*  COMPTAGE  */
        
        int comptage = s_bdd.recup_comptage();
        
        if(comptage == 1)
            s_comptage = true;
        else if(comptage == 0)
            s_comptage = false;
        else{
            s_comptage = true;
        }
    }
	
	/*	GETTERS	*/
	
	/**
     * Vérifie si la musique est activée 
     * @author Romain MONIER
     * @return Vrai si elle est activée
     */
	public static boolean isMusicActive() 
	{
		return s_musique;
	}
	
	/**
     * Vérifie si les sons sur les boutons sont activés 
     * @author Romain MONIER
     * @return Vrai s'ils sont activés
     */
	public static boolean isSoundActive()
	{
		return s_son;
	}
	
	/**
     * Vérifie si le comptage est activé 
     * @author Romain MONIER
     * @return Vrai s'il est activé
     */
	public static boolean isComptageActive()
	{
		return s_comptage;
	}
	
	/*	SETTERS	*/
	
	/**
     * Remet la musique ou la désactive
     * @author Romain MONIER
     * @param musique jouer ou non
     * @param mus la musique qu'on veut jouer
     */
	public static void setMusique(boolean musique, Audio mus)
	{
		s_musique = musique;
		
		if (!s_musique){ 	
            mus.stop();
		}
		else{ 	
            mus.jouer();
		}
        
        s_bdd.maj_audio(s_musique, true);
	}
	
	/**
     * Coupe le son ou l'active
     * @author Romain MONIER
     * @param son ON/OFF
     */
	public static void setSon(boolean son)
	{
		s_son = son;
        s_bdd.maj_audio(s_son, false);
	}
	
	/**
     * Désactive le comptage ou l'active
     * @author Romain MONIER
     * @param comptage ON/OFF
     */
	public static void setComptage(boolean comptage)
	{
		s_comptage = comptage;
        s_bdd.maj_comptage(s_comptage);
	}
}
