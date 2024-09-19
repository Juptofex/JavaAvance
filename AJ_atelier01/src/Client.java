import java.util.Objects;
public class Client {

    private static int numeroSuivant;
    private int numero;
    private String  nom;
    private String  prenom;
    private String telephone;
    private Commande commandeEnCours;

    public Client(String nom, String prenom, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.numero = numeroSuivant;
        numeroSuivant++;
    }

    public int getNumero() {
        return numero;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public Commande getCommandeEnCours() {
        return commandeEnCours;
    }

    public boolean enregistrer(Commande commande) {

    }

    public boolean cloturerCommandeEnCours() {

    }

    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "client n° " + numero + " (" + prenom  + " " + nom + ", telephone : " + telephone +")";
    }
}
