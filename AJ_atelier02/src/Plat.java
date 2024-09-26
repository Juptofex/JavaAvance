import domaine.Ingredient;
import domaine.IngredientQuantifie;
import domaine.Instruction;
import domaine.Unite;

import java.time.Duration;
import java.util.*;

public class Plat {
    public enum Difficulte {
        X, XX, XXX, XXXX, XXXXX;

        @Override
        public String toString() {
            return "*".repeat(this.ordinal() + 1);
        }
    }

    public enum Cout {
        $, $$, $$$, $$$$, $$$$$;

        @Override
        public String toString() {
            return "€".repeat(this.ordinal() + 1);
        }
    }

    public enum Type {
        ENTREE, PLAT, DESSERT;
    }

    private final String nom;
    private final int nbPersonnes;
    private final Difficulte niveauDeDifficulte;
    private final Cout cout;
    private final Type type;
    private Duration dureeEnMinutes;
    private final List<Instruction> instructions;
    private final Set<IngredientQuantifie> ingredientsQuantifies;

    public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout, Type type) {
        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDeDifficulte;
        this.cout = cout;
        this.type = type;
        this.dureeEnMinutes = Duration.ofMinutes(0);
        this.instructions = new ArrayList<>();
        this.ingredientsQuantifies = new HashSet<>();
    }

    public String getNom() {
        return nom;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Difficulte getNiveauDeDifficulte() {
        return niveauDeDifficulte;
    }

    public Cout getCout() {
        return cout;
    }

    public Type getType() {
        return type;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    public void insererInstruction(int position, Instruction instruction) {
        if (position <= 0 || position > instructions.size() + 1) {
            throw new IllegalArgumentException("Position invalide");
        }
        instructions.add(position - 1, instruction);
        updateDureeEnMinutes();
    }

    public void ajouterInstruction(Instruction instruction) {
        instructions.add(instruction);
        updateDureeEnMinutes();
    }

    public Instruction remplacerInstruction(int position, Instruction instruction) {
        if (position <= 0 || position > instructions.size()) {
            throw new IllegalArgumentException("Position invalide");
        }
        Instruction oldInstruction = instructions.set(position - 1, instruction);
        updateDureeEnMinutes();
        return oldInstruction;
    }

    public Instruction supprimerInstruction(int position) {
        if (position <= 0 || position > instructions.size()) {
            throw new IllegalArgumentException("Position invalide");
        }
        Instruction removedInstruction = instructions.remove(position - 1);
        updateDureeEnMinutes();
        return removedInstruction;
    }

    public boolean ajouterIngredient(Ingredient ingredient, int quantite, Unite unite) {
        return ingredientsQuantifies.add(new IngredientQuantifie(ingredient, quantite, unite));
    }

    public boolean ajouterIngredient(Ingredient ingredient, int quantite) {
        return ingredientsQuantifies.add(new IngredientQuantifie(ingredient, quantite, Unite.NEANT));
    }

    public boolean modifierIngredient(Ingredient ingredient, int quantite, Unite unite) {
        return ingredientsQuantifies.add(new IngredientQuantifie(ingredient, quantite, unite));
    }

    public boolean supprimerIngredient(Ingredient ingredient) {
        IngredientQuantifie ing = trouverIngredientQuantifie(ingredient);
        if (ing != null) {
            ingredientsQuantifies.remove(ing);
            return true;
        }
        return false;
    }

    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient) {
        for (IngredientQuantifie ingredientQuantifie : ingredientsQuantifies) {
            if (ingredientQuantifie.getIngredient().equals(ingredient)) {
                return ingredientQuantifie;
            }
        }
        return null;
    }

    public List<Instruction> instructions() {
        return Collections.unmodifiableList(instructions);
    }

    private void updateDureeEnMinutes() {
        dureeEnMinutes = instructions.stream()
                .map(Instruction::getDureeEnMinutes)
                .reduce(Duration.ZERO, Duration::plus);
    }

    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + " \n\n";
        res += "Ingrédients :\n";
        for (domaine.IngredientQuantifie ing : this.ingredientsQuantifies) {
            res += ing + "\n";
        }
        int i = 1;
        res += "\n";
        for (domaine.Instruction instruction : this.instructions) {
            res += i++ + ". " + instruction + "\n";
        }
        return res;
    }
}
