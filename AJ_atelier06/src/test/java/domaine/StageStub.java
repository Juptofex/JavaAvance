package domaine;

import java.util.Set;

public class StageStub implements Stage {

    private int numeroDeSemaine;
    private Sport sport;
    private Moniteur moniteur;

    public StageStub(int numeroDeSemaine, Sport sport, Moniteur moniteur) {
        this.numeroDeSemaine=numeroDeSemaine;
        this.sport=sport;
        this.moniteur=moniteur;
    }

    @Override
    public String getIntitule() {
        return null;
    }

    @Override
    public String getLieu() {
        return null;
    }

    @Override
    public int getNumeroDeSemaine() {
        return this.numeroDeSemaine;
    }

    @Override
    public Sport getSport() {
        return this.sport;
    }

    @Override
    public boolean enregistrerMoniteur(Moniteur moniteur) {
        return false;
    }

    @Override
    public boolean supprimerMoniteur() {
        return false;
    }

    @Override
    public Moniteur getMoniteur() {
        return this.moniteur;
    }

    @Override
    public boolean ajouterEnfant(Enfant enfant) {
        return false;
    }

    @Override
    public boolean supprimerEnfant(Enfant enfant) {
        return false;
    }

    @Override
    public boolean contientEnfant(Enfant enfant) {
        return false;
    }

    @Override
    public int nombreDEnfants() {
        return 0;
    }

    @Override
    public Set<Enfant> enfants() {
        return null;
    }
}
