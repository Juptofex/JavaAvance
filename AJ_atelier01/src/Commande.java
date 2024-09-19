import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Commande {

    private static int numeroSuivant = 1;
    private int numero;
    private Client client;
    private LocalDateTime date;
    private ArrayList<LigneDeCommande> lignesDeCommande;

    public Commande(Client client) {

    }

    public int getNumero() {
        return numero;
    }

    public Client getClient() {
        return client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Iterator<LigneDeCommande> iterator() {
        return lignesDeCommande.iterator();
    }
}
