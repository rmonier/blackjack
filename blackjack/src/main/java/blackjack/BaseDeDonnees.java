/* FICHIER BASEDEDONNEES.JAVA :
 *      - FICHIER DE CONNEXION BDD & REQUETES
 * 
 *  DERNIÈRE MÀJ : 09/04/2019 par ROMAIN MONIER
 *  CRÉÉ PAR ROMAIN MONIER
 *  2018/2019
 * ------------------------------------------
 *  INFOS :
 *      - CLASSE DE MODELE
 * ------------------------------------------
 */

package blackjack;

import java.sql.*;

/** La base de données
 * @author Romain MONIER
 */
public class BaseDeDonnees
{
    private Connection conn;
         
	/**
	 * Constructeur
     * @author Romain MONIER
     * @param adresseBD Adresse Base de Données
	*/
    public BaseDeDonnees(String adresseBD)
    {
        try // Tente de se connecter à la BDD
        {           
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(adresseBD);
            
            /*  CREATION DE LA BDD SI ELLE N'EXISTE PAS */
            
            Statement stmt = conn.createStatement();
            
            PreparedStatement prep_res = conn.prepareStatement(""
                                                                + "CREATE TABLE IF NOT EXISTS Parametres ( \n"
                                                                + "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
                                                                + "    musique tinyint(1), \n"
                                                                + "    son tinyint(1), \n"
                                                                + "    comptage tinyint(1) \n"
                                                                + "    )"
                                                            + "");
            prep_res.executeUpdate();
            prep_res.close();
            prep_res = conn.prepareStatement(""
                                                                + "CREATE TABLE IF NOT EXISTS Joueur ( \n"
                                                                + "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, \n"
                                                                + "    argent int(11) \n"
                                                                + "    )"
                                                            + "");
            prep_res.executeUpdate();
            prep_res.close();
            prep_res = conn.prepareStatement("INSERT OR IGNORE INTO Parametres VALUES(1,1,1,1)");
            prep_res.executeUpdate();
            prep_res.close();
            prep_res = conn.prepareStatement("INSERT OR IGNORE INTO Joueur VALUES(1,100)");
            prep_res.executeUpdate();
            prep_res.close();
            
            stmt.close();
        }
        catch(Exception e) // Sinon erreur, fin du programme
        {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
    
	/**
	 * Destructeur
     * @author Romain MONIER
	*/
    public void close() throws SQLException
    {
        conn.close();
    }
    
    //  METHODES DE RECUPERATION
    
	/**
	 * Exécute la requête, renvoi la valeur du paramètre audio
     * @author Romain MONIER
     * @param isMusic Récupère soit la musique soit le son
     * @return Booléen sur l'audio ou code d'erreur
	*/
    public int recup_audio(boolean isMusic)
    {                
        try
        {
            Statement stmt = conn.createStatement();
            
            PreparedStatement prep_res;
            
            if(isMusic)
                prep_res = conn.prepareStatement("SELECT Parametres.musique FROM Parametres");
            else
                prep_res = conn.prepareStatement("SELECT Parametres.son FROM Parametres");
            
            ResultSet res = prep_res.executeQuery();
            
            int valeur = -1;
            while(res.next())
            {
                valeur = res.getInt(1);
            }
            
            res.close();
            prep_res.close();
            stmt.close();
            
            return valeur;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -2;
        }
    }
    
	/**
	 * Exécute la requête, renvoi la valeur du paramètre de comptage
     * @author Romain MONIER
     * @return Booléen sur le comptage ou code d'erreur
	*/
    public int recup_comptage()
    {                
        try
        {
            Statement stmt = conn.createStatement();
            
            PreparedStatement prep_res;
            prep_res = conn.prepareStatement("SELECT Parametres.comptage FROM Parametres");
            ResultSet res = prep_res.executeQuery();
            
            int valeur = -1;
            while(res.next())
            {
                valeur = res.getInt(1);
            }
            
            res.close();
            prep_res.close();
            stmt.close();
            
            return valeur;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -2;
        }
    }
    
	/**
	 * Exécute la requête, renvoi l'argent du joueur
     * @author Romain MONIER
     * @return Argent ou code d'erreur
	*/
    public int recup_argent()
    {                
        try
        {
            Statement stmt = conn.createStatement();
            
            PreparedStatement prep_res;
            prep_res = conn.prepareStatement("SELECT Joueur.argent FROM Joueur");
            ResultSet res = prep_res.executeQuery();
            
            int valeur = -1;
            while(res.next())
            {
                valeur = res.getInt(1);
            }
            
            res.close();
            prep_res.close();
            stmt.close();
            
            return valeur;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -2;
        }
    }
    
    
     //  METHODES DE MISE A JOUR
    
	/**
	 * Exécute la requête, modifie les paramètres audio dans la BDD
     * @author Romain MONIER
     * @param valeur Nouvelle valeur du paramètre
     * @param isMusic Modifie soit la musique soit le son
     * @return Code d'erreur
	*/
    public int maj_audio(boolean valeur, boolean isMusic)
    {
        try
        {
            Statement stmt = conn.createStatement();
            
            /*  MISE A JOUR DE LA BDD */
            
            String req_maj;
            
            if(isMusic)
                req_maj = "UPDATE Parametres SET musique=?";
            else
                req_maj = "UPDATE Parametres SET son=?";
                
            PreparedStatement prep_res_maj = conn.prepareStatement(req_maj);
            
            int valeur_int;
            if(valeur)
                valeur_int = 1;
            else
                valeur_int = 0;
            
            prep_res_maj.setInt(1, valeur_int);
            
            if(prep_res_maj.executeUpdate() == 0){ // si on a pas modifié de données => probleme
                prep_res_maj.close();
                stmt.close();
                return -3;
            }
            
            prep_res_maj.close();
        
            stmt.close();
            
            return 1;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -2;
        }
    }
    
	/**
	 * Exécute la requête, modifie le paramètre comptage dans la BDD
     * @author Romain MONIER
     * @param valeur Nouvelle valeur du paramètre
     * @return Code d'erreur
	*/
    public int maj_comptage(boolean valeur)
    {
        try
        {
            Statement stmt = conn.createStatement();
            
            /*  MISE A JOUR DE LA BDD */
            
            String req_maj;
            req_maj = "UPDATE Parametres SET comptage=?";
            PreparedStatement prep_res_maj = conn.prepareStatement(req_maj);
            
            int valeur_int;
            if(valeur)
                valeur_int = 1;
            else
                valeur_int = 0;
            
            prep_res_maj.setInt(1, valeur_int);
            
            if(prep_res_maj.executeUpdate() == 0){ // si on a pas modifié de données => probleme
                prep_res_maj.close();
                stmt.close();
                return -3;
            }
            
            prep_res_maj.close();
        
            stmt.close();
            
            return 1;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -2;
        }
    }
    
	/**
	 * Exécute la requête, modifie l'argent dans la BDD
     * @author Romain MONIER
     * @param valeur Nouvelle valeur de l'argent
     * @return Code d'erreur
	*/
    public int maj_argent(int valeur)
    {
        try
        {
            Statement stmt = conn.createStatement();
            
            /*  MISE A JOUR DE LA BDD */
            
            String req_maj;
            req_maj = "UPDATE Joueur SET argent=?";
            PreparedStatement prep_res_maj = conn.prepareStatement(req_maj);
            
            prep_res_maj.setInt(1, valeur);
            
            if(prep_res_maj.executeUpdate() == 0){ // si on a pas modifié de données => probleme
                prep_res_maj.close();
                stmt.close();
                return -3;
            }
            
            prep_res_maj.close();
        
            stmt.close();
            
            return 1;
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            return -2;
        }
    }
}
