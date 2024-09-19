import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Iterator;

public class Commande {

    private static int numeroSuivant = 1;
    private int numero;
    private Client client;
    private LocalDateTime date;
    private ArrayList<LigneDeCommande> lignesDeCommande;

    public Commande(Client client) {
        if (!client.enregistrer(this)) {
            throw new IllegalArgumentException("Impossible de créer une commande pour un client ayant encore une commande en cours");
        }
        this.numero = numeroSuivant;
        numeroSuivant++;
        this.client = client;
        this.date = LocalDateTime.now();
        lignesDeCommande = new ArrayList<>();
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

    public boolean ajouter(Pizza pizza, int quantite) {
        if (this!=client.getCommandeEnCours()) {
            return false;
        }
        for (LigneDeCommande ligneDeCommande : lignesDeCommande) {
            if (ligneDeCommande.getPizza().equals(pizza)) {
                ligneDeCommande.setQuantite(ligneDeCommande.getQuantite()+quantite);
                return true;
            }
        }
        lignesDeCommande.add(new LigneDeCommande(pizza, quantite));
        return true;
    }

    public boolean ajouter(Pizza pizza) {
        return ajouter(pizza, 1);
    }

    public double calculerMontantTotal() {
        double montantTotal = 0;
        for (LigneDeCommande ligneDeCommande : lignesDeCommande) {
            montantTotal += ligneDeCommande.calculerPrixTotal();
        }
        return montantTotal;
    }

    public String detailler() {
        StringBuilder detail = new StringBuilder();
        detail.append("Commande n° ").append(numero).append(" du ").append(date).append(" pour ").append(client).append(" :\n");
        for (LigneDeCommande ligneDeCommande : lignesDeCommande) {
            detail.append(ligneDeCommande).append("\n");
        }
        detail.append("Montant total : ").append(calculerMontantTotal()).append(" €");
        return detail.toString();
    }

    public Iterator<LigneDeCommande> iterator() {
        return lignesDeCommande.iterator();
    }

    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        String encours = "";
        if (client.getCommandeEnCours() == this)
            encours = " (en cours)";
        return "Commande n° " + numero + encours + " du " + client + "\ndate : " + formater.format(date);
    }
}
