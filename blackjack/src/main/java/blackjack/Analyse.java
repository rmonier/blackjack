/* FICHIER ANALYSE.JAVA :
 *      - ANALYSE DU JEU POUR LE COMPTAGE
 *      - GERE LE DEROULEMENT DE LA PARTIE
 * 
 *  DERNIÈRE MÀJ : 11/04/2019 par ROMAIN MONIER
 *  CRÉÉ PAR ROMAIN MONIER
 *  2018/2019
 * ------------------------------------------
 *  INFOS :
 *      - CLASSE DE CONTROLE
 * ------------------------------------------
 */

package blackjack;

import java.io.IOException;
import java.util.ArrayList;

/** Analyse le jeu et les cartes et gère le déroulement de la partie
 * @author Romain MONIER
 */
public class Analyse
{
	/*	ATTRIBUTS STATIQUES */
	
	private static int s_argent_dispo, s_mise, s_points_joueur, s_points_croupier, s_compte_cartes, s_tour, s_nb_cartes_sorties, s_nb_as_onze_joueur, s_nb_as_onze_croupier;
	private static BaseDeDonnees s_bdd;
    private static ArrayList<Integer> s_decks_vides;
    private static ArrayList<Carte> s_cartes_joueur, s_cartes_croupier;
    private static boolean s_mise_faite, s_fin_partie, s_blackjack_joueur, s_blackjack_croupier, s_assurance_dispo, s_assurance;
    private static Resultat s_resultat;
    private static final int S_SEUIL_RC = 2;
    
    /*  INITIALISATION  */
    
	/**
     * Initialise Analyse en récupérant les valeurs enregistrées
     * @author Romain MONIER
     * @param bdd La base de données
     */
    public static void init(BaseDeDonnees bdd)
    {
        s_bdd = bdd;
        
        int argent = s_bdd.recup_argent();
        s_argent_dispo = argent < 0 ? 100 : argent;
        
        reset();
        
        s_decks_vides = new ArrayList<Integer>();
    }
	
	/*	GETTERS	*/
    
    /**
	 * Retourne obligatoirement une carte aléatoire depuis un deck aléatoire. Si les decks sont vides, ils seront regénérés pour pouvoir renvoyer une carte. 
     * @author Romain MONIER
     * @param decks Tableau des decks du jeu
     * @return Carte aléatoire d'un deck
	*/
    public static Carte getCarteAleatoire(Deck[] decks) throws IOException
    {
        int num_deck;
        Carte carte;
        
        s_nb_cartes_sorties++;
        
        do
        {        
            do {
                num_deck = (int)(Math.random() * decks.length);
            } while(s_decks_vides.contains(num_deck));
            
            Deck deck_alea = decks[num_deck];
            carte = deck_alea.getCarteAleatoire();
            
            if(carte != null)
                return carte;
            else
            {
                s_decks_vides.add(num_deck);
                
                if(s_decks_vides.size() == decks.length)
                {
                    for(int i = 0 ; i < decks.length ; i++) { // regénération des 6 decks de 52 cartes
                        decks[i].regenerer();
                    }
                    s_decks_vides.clear();
                    s_nb_cartes_sorties = 0;
                    s_compte_cartes = 0; // remise à zéro du comptage car nouveaux paquets de cartes
                    
                    num_deck = (int)(Math.random() * decks.length);
                    
                    deck_alea = decks[num_deck];
                    carte = deck_alea.getCarteAleatoire();
                    
                    return carte;
                }
            }
        } while(carte == null);
        
        return carte;
    }
    
    /**
	 * Calcule le seuil du RT grâce au RC (comptage de cartes) à partir duquel on peut conseiller de miser plus ou moins d'argent.
     * @author Romain MONIER
     * @param decks Tableau des decks du jeu
     * @return Le seuil de RT positif
	*/
    public static double getSeuilPositifRT(Deck[] decks)
    {
        double rt = 0.0;
        
        rt = ((double)(52 * S_SEUIL_RC) / (double)((52 * decks.length) - s_nb_cartes_sorties));
        
        return rt >= 0 ? rt : rt*-1.0;
    }
    
    /**
	 * Récupère le seuil de RT grâce à getSeuilPositifRT() et le rend négatif
     * @author Romain MONIER
     * @param decks Tableau des decks du jeu
     * @return Le seuil de RT négatif
	*/
    public static double getSeuilNegatifRT(Deck[] decks)
    {
        return (Analyse.getSeuilPositifRT(decks) * -1.0);
    }
    
    /**
	 * Récupère l'argent disponible
     * @author Romain MONIER
     * @return L'argent disponible
	*/
    public static int getArgent()
    {
        return s_argent_dispo;
    }
    
    /**
	 * Récupère la mise actuelle
     * @author Romain MONIER
     * @return La mise actuelle
	*/
    public static int getMise()
    {
        return s_mise;
    }
     
    /**
	 * Récupère les points du Joueur
     * @author Romain MONIER
     * @return Les points du Joueur
	*/
    public static int getPointsJoueur()
    {
        return s_points_joueur;
    }
    
    /**
	 * Récupère les points du Croupier
     * @author Romain MONIER
     * @return Les points du Croupier
	*/
    public static int getPointsCroupier()
    {
        return s_points_croupier;
    }
    
    /**
	 * Récupère les cartes du Joueur
     * @author Julie Balouet
     * @return Les cartes du Joueur
	*/
    public static ArrayList<Carte> getCarteJoueur()
    {
        return s_cartes_joueur;
    }
    
    /**
	 * Récupère les cartes du Croupier
     * @author Julie Balouet
     * @return Les cartes du Croupier
	*/
     public static ArrayList<Carte> getCarteCroupier()
    {
        return s_cartes_croupier;
    }
    
    /**
	 * Retourne vrai si la mise a été faite
     * @author Romain MONIER
     * @return Booléen sur l'action d'avoir miser de l'argent
	*/
    public static boolean isMiseFaite()
    {
        return s_mise_faite;
    }
    
    /**
	 * Retourne vrai si la partie est finie
     * @author Romain MONIER
     * @return Booléen sur le fait d'avoir fini la partie
	*/
    public static boolean isPartieFinie()
    {
        return s_fin_partie;
    }
    
    /**
	 * Récupère le tour actuel
     * @author Romain MONIER
     * @return Tour actuel de la partie
	*/
    public static int getTour()
    {
        return s_tour;
    }
    
    /**
	 * Retourne vrai si le joueur a fait Blackjack
     * @author Romain MONIER
     * @return Booléen sur l'action d'avoir fait blackjack
	*/
    public static boolean isJoueurBlackjack()
    {
        if(s_cartes_joueur.size() == 2)
        {
            if((s_cartes_joueur.get(0).getValeur() == 1 && s_cartes_joueur.get(1).getValeur() >= 10) || (s_cartes_joueur.get(1).getValeur() == 1 && s_cartes_joueur.get(0).getValeur() >= 10))
                s_blackjack_joueur = true;
        }
        
        return s_blackjack_joueur;
    }
    
    /**
	 * Retourne vrai si le croupier a fait Blackjack
     * @author Romain MONIER
     * @return Booléen sur l'action d'avoir fait blackjack
	*/
    public static boolean isCroupierBlackjack()
    {
        if(s_cartes_croupier.size() == 2)
        {
            if((s_cartes_croupier.get(0).getValeur() == 1 && s_cartes_croupier.get(1).getValeur() >= 10) || (s_cartes_croupier.get(1).getValeur() == 1 && s_cartes_croupier.get(0).getValeur() >= 10))
                s_blackjack_croupier = true;
        }
        
        return s_blackjack_croupier;
    }
    
    /**
	 * Retourne le résultat final de la partie en cours (gagné perdu etc)
     * @author Romain MONIER
     * @return Résultat de la partie (enum)
	*/
    public static Resultat getResultat()
    {
        return s_resultat;
    }
    
    /**
	 * Récupère le RC (comptage de carte)
     * @author Romain MONIER
     * @return Le RC
	*/
    public static int getCompte()
    {
        return s_compte_cartes;
    }
    
    /**
	 * Calcule le RT actuel grâce au RC (comptage de cartes) pour pouvoir le comparer plus tard avec le seuil
     * @author Romain MONIER
     * @param decks Tableau des decks du jeu
     * @return Le RT actuel
	*/
    public static double getCompteRT(Deck[] decks)
    {
        double rt = 0.0;
        
        rt = ((double)(52 * Analyse.getCompte()) / (double)((52 * decks.length) - s_nb_cartes_sorties));
        
        return rt;
    }
    
    /**
	 * Retourne vrai si le split est possible
     * @author Romain MONIER
     * @return Booléen sur la faisabilité du split
	*/
    public static boolean isSplitPossible()
    {
        int valeur_carte1 = (s_cartes_joueur.get(s_tour).getValeur() >= 10) ? 10 : s_cartes_joueur.get(s_tour).getValeur();
        int valeur_carte2 = (s_cartes_joueur.get(s_tour+1).getValeur() >= 10) ? 10 : s_cartes_joueur.get(s_tour+1).getValeur();
        
        if(s_cartes_joueur.size() >= s_tour+2 && valeur_carte1 == valeur_carte2)
            return true;
        else
            return false;
    }
    
    /**
	 * Retourne vrai si l'assurance est disponible
     * @author Romain MONIER
     * @return Booléen sur la disponibilité de l'assurance
	*/
    public static boolean isAssuranceDispo()
    {
        return s_assurance_dispo;
    }
    
    /**
	 * Retourne vrai si l'assurance a été prise
     * @author Romain MONIER
     * @return Booléen sur la prise de l'assurance
	*/
    public static boolean isAssurance()
    {
        return s_assurance;
    }
	
	/*	SETTERS	*/
    
    /**
	 * Réinitialise le jeu pour tout recommencer (au niveau des paramètres)
     * @author Romain MONIER
	*/
    public static void reset()
    {
        resetGame();
        s_nb_cartes_sorties = 0;
        s_compte_cartes = 0;
    }   
    
    /**
	 * Réinitialise la partie en cours (au niveau des paramètres)
     * @author Romain MONIER
	*/
    public static void resetGame()
    {
        s_mise = 0;
        s_points_joueur = 0;
        s_points_croupier = 0;
        s_nb_as_onze_joueur = 0;
        s_nb_as_onze_croupier = 0;
        s_tour = 0;
        s_mise_faite = false;
        s_fin_partie = false;
        s_blackjack_croupier = false;
        s_blackjack_joueur = false;
        s_assurance_dispo = false;
        s_assurance = false;
        s_resultat = null;
        
        s_cartes_joueur = new ArrayList<Carte>();
        s_cartes_croupier = new ArrayList<Carte>();
    }
    
    /**
	 * Réinitialise l'argent à 100€
     * @author Romain MONIER
	*/
    public static void resetArgent()
    {
        s_argent_dispo = 100;
        s_bdd.maj_argent(100);
    }
    
    /**
	 * Indique que la mise vient d'être faite
     * @author Romain MONIER
	*/
    public static void miseFaite()
    {
        s_mise_faite = true;
    }
    
    /**
	 * Passe au tour suivant
     * @author Romain MONIER
	*/
    public static void newTour()
    {
        s_tour++;
    }
    
    /**
	 * Indique que la partie est finie et analyse le résultat en répartissant les gains
     * @author Romain MONIER
	*/
    public static void finPartie()
    {
        s_fin_partie = true;
        
        s_blackjack_croupier = isCroupierBlackjack();
        s_blackjack_joueur = isJoueurBlackjack();
        
        if(s_points_croupier > 21)
        {
            if(s_points_joueur > 21)
                s_resultat = Resultat.EGALITE;
            else
                s_resultat = Resultat.GAGNE;
        }
        else if(s_points_joueur > 21)
            s_resultat = Resultat.PERDU;
        else if(s_points_croupier > s_points_joueur)
            s_resultat = Resultat.PERDU;
        else if(s_points_croupier < s_points_joueur)
            s_resultat = Resultat.GAGNE;
        else if(s_points_croupier == s_points_joueur)
        {
            if(s_blackjack_croupier && s_blackjack_joueur)
                s_resultat = Resultat.EGALITE;
            else if(s_blackjack_croupier)
                s_resultat = Resultat.PERDU;
            else if(s_blackjack_joueur)
                s_resultat = Resultat.GAGNE;
            else
                s_resultat = Resultat.EGALITE;
        }
        
        switch(s_resultat)
        {
            case EGALITE:
                addArgent(s_mise);
                s_mise = 0;
            break;
            case GAGNE:
                if(s_blackjack_joueur)
                    addArgent((int)(s_mise + (s_mise*1.5)));
                else
                    addArgent(s_mise * 2);
                s_mise = 0;
            break;
            case PERDU:
                if(s_assurance && s_blackjack_croupier)
                    addArgent((int)(s_mise * 1.5));
                s_mise = 0;
            break;
            default:
                System.out.println("ERREUR");
        }
    }
    
    /**
	 * Mise l'argent en retirant des fonds la mise
     * @author Romain MONIER
     * @param mise L'argent misé
     * @return Vrai si ça a fonctionné, faux si l'argent n'était pas suffisant
	*/
    public static boolean miseArgent(int mise)
    {
        if(s_argent_dispo >= mise)
        {
            s_argent_dispo -= mise;
            s_bdd.maj_argent(s_argent_dispo);
            s_mise += mise;
            return true;
        }
        else
            return false;
    }
    
    /**
	 * Ajoute l'argent au porte-monnaie
     * @author Romain MONIER
     * @param gain Les gains
	*/
    public static void addArgent(int gain)
    {
        s_argent_dispo += gain;
        s_bdd.maj_argent(s_argent_dispo);
    }
    
    /**
	 * Ajoute une carte à la main du Joueur
     * @author Romain MONIER
     * @param carte La carte à ajouter
     * @return Vrai si on a réussi, faux si la carte ne peux pas être ajoutée
	*/
    public static boolean addCarteJoueur(Carte carte)
    {
        if(carte != null)
        {
            s_cartes_joueur.add(carte);
            s_compte_cartes += carte.getCompte();
            
            if(carte.getValeur() == 1)
            {
                if(11 + s_points_joueur <= 21){
                    s_points_joueur += 11;
                    s_nb_as_onze_joueur++;
                }
                else
                    s_points_joueur += 1;
            }
            else if(carte.getValeur() >= 10)
                s_points_joueur += 10;
            else
                s_points_joueur += carte.getValeur();
            
            // Si on a dépassé 21 et qu'on a des as à 11, on peut les passer à 1
            for(int i = s_nb_as_onze_joueur ; i > 0 && s_points_joueur > 21; i--)
            {
                s_points_joueur -= 10;
                s_nb_as_onze_joueur--;
            }
        }
        else
            return false;
            
        return true;
    }
    
    /**
	 * Ajoute une carte à la main du Croupier
     * @author Romain MONIER
     * @param carte La carte à ajouter
     * @return Vrai si on a réussi, faux si la carte ne peux pas être ajoutée
	*/
    public static boolean addCarteCroupier(Carte carte)
    {
        if(carte != null)
        {
            s_cartes_croupier.add(carte);
            s_compte_cartes += carte.getCompte();
            
            if(carte.getValeur() == 1)
            {
                if(s_tour == 0)
                    s_assurance_dispo = true;
                else
                    s_assurance_dispo = false;
                if(11 + s_points_croupier <= 21){
                    s_points_croupier += 11;
                    s_nb_as_onze_croupier++;
                }
                else
                    s_points_croupier += 1;
            }
            else if(carte.getValeur() >= 10)
                s_points_croupier += 10;
            else
                s_points_croupier += carte.getValeur();
                
            // Si on a dépassé 21 et qu'on a des as à 11, on peut les passer à 1
            for(int i = s_nb_as_onze_croupier ; i > 0 && s_points_croupier > 21; i--)
            {
                s_points_croupier -= 10;
                s_nb_as_onze_croupier--;
            }
        }
        else
            return false;
            
        return true;
    }
    
    /**
	 * Prend l'assurance
     * @author Romain MONIER
     * @return Vrai si on a réussi à prendre l'assurance
	*/
    public static boolean takeAssurance()
    {
        if(s_assurance_dispo && s_argent_dispo >= (int)(s_mise / 2))
        {
            s_assurance = true;
            s_argent_dispo -= (int)(s_mise / 2);
            s_bdd.maj_argent(s_argent_dispo);
            return true;
        }
        else
            return false;
    }
    
    /**
	 * Retourne la stratégie à adopter en se basant sur les mains du Joueur et du Croupier ainsi que le tableau statistique de stratégie
     * @author Julie BALOUET, Morgane BOUCHARD
     * @return La stratégie (enum)
	*/
    public static Strategie getStrategie()
    {
        int i=0;
        int j=0;
        
        if(Analyse.getCarteJoueur().size() <= 2 && (Analyse.getCarteJoueur().get(0).getValeur()==1 || Analyse.getCarteJoueur().get(1).getValeur()==1) && (Analyse.getCarteJoueur().get(0).getValeur()!= Analyse.getCarteJoueur().get(1).getValeur()))
        {         
            if(Analyse.getCarteJoueur().get(0).getValeur()==2 || Analyse.getCarteJoueur().get(1).getValeur()==2){
                i=35;
             }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==3 || Analyse.getCarteJoueur().get(1).getValeur()==3){
                i=34;
             }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==4 || Analyse.getCarteJoueur().get(1).getValeur()==4){
                i=33;  
             }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==5 || Analyse.getCarteJoueur().get(1).getValeur()==5){
                i=32; 
                 }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==6 || Analyse.getCarteJoueur().get(1).getValeur()==6){
                i=31; 
                 }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==7 || Analyse.getCarteJoueur().get(1).getValeur()==7){
                i=30;
                 }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==8 || Analyse.getCarteJoueur().get(1).getValeur()==8){
                i=29; 
                 }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==9 || Analyse.getCarteJoueur().get(1).getValeur()==9){
                i=28;
                 }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==10 || Analyse.getCarteJoueur().get(1).getValeur()==10|| Analyse.getCarteJoueur().get(0).getValeur()==11 || Analyse.getCarteJoueur().get(1).getValeur()==11 || Analyse.getCarteJoueur().get(0).getValeur()==12 || Analyse.getCarteJoueur().get(1).getValeur()==12 || Analyse.getCarteJoueur().get(0).getValeur()==13 || Analyse.getCarteJoueur().get(1).getValeur()==13){
                i=27;
            }//else
        }//if
        else if(Analyse.getCarteJoueur().size() <= 2 && Analyse.getCarteJoueur().get(0).getValeur()== Analyse.getCarteJoueur().get(1).getValeur())
        {
            if(Analyse.getCarteJoueur().get(0).getValeur()== 1)
            {
                i=17;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()== 2)
            {
                i=26;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()== 3)
             {
                i=25;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==4)
             {
                i=24;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==5)
             {
                i=23;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==6)
             {
                i=22;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==7)
             {
                i=21;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==8)
             {
                i=20;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==9)
             {
                i=19;
            }
            else if(Analyse.getCarteJoueur().get(0).getValeur()==10 || Analyse.getCarteJoueur().get(0).getValeur()==11 || Analyse.getCarteJoueur().get(0).getValeur()==12 || Analyse.getCarteJoueur().get(0).getValeur()==13)
             {
                i=18;
            }//elseif
        }//elseif
        else 
        {
            if(Analyse.getPointsJoueur()== 21)
            {
             i=0;
            } else
            if(Analyse.getPointsJoueur()== 20)
            {
                 i=1;
            } else  
            if(Analyse.getPointsJoueur()== 19)
            {
                 i=2;
            } else
            if(Analyse.getPointsJoueur()== 18)
            {
                 i=3;
            } else
            if(Analyse.getPointsJoueur()== 17)
            {
                 i=4;
            } else
            if(Analyse.getPointsJoueur()== 16)
            {
                 i=5;
            } else 
            if(Analyse.getPointsJoueur()== 15)
            {
                 i=6;
            } else
            if(Analyse.getPointsJoueur()== 14)
            {
                 i=7;
            } else
            if(Analyse.getPointsJoueur()== 13)
            {
                 i=8;
            } else
            if(Analyse.getPointsJoueur()== 12)
            {
                 i=9;
            } else  
            if(Analyse.getPointsJoueur()== 11)
            {
                 i=10;
            } else
            if(Analyse.getPointsJoueur()== 10)
            {
                 i=11;
            } else
            if(Analyse.getPointsJoueur()== 9)
            {
                 i=12;
            } else
            if(Analyse.getPointsJoueur()== 8)
            {
                 i=13;
            } else  
            if(Analyse.getPointsJoueur()== 7)
            {
                 i=14;
            } else
            if(Analyse.getPointsJoueur()== 6)
            {
                 i=15;
            } else
            if(Analyse.getPointsJoueur()== 5)
            {
                 i=16;
            } //if
        }//else
        
        if( Analyse.getCarteCroupier().get(0).getValeur()==1)
        {
          j=9;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==2)
        {
          j=0;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==3)
        {
          j=1;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==4)
        {
          j=2;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==5)
        {
          j=3;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==6)
        {
          j=4;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==7)
        {
          j=5;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==8)
        {
          j=6;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==9)
        {
          j=7;
        }else if( Analyse.getCarteCroupier().get(0).getValeur()==10 || Analyse.getCarteCroupier().get(0).getValeur()==11 || Analyse.getCarteCroupier().get(0).getValeur()==12 || Analyse.getCarteCroupier().get(0).getValeur()==13)
        {
          j=8;
        }
        
        return Tableau.getTabStrategie()[i][j];
    }
}
