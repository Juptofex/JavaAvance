package employee;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeManagement {

    /**
     * Un Supplier est un objets qui fournit des résultats. Appeler sa méthode get()
     * vous fournira le type passé en paramètre, ici un Stream de String.
     * Complétez la fonction pour créer le Stream à partir du fichier streamingvf.cvs
     */
    private static final Supplier<Stream<String>> supplier = () -> {
        //TODO: retourner un stream créer à partir du fichier. Aidez vous de la p.15 : "Créer des streams"
        //      En cas d'IOException, vous devez lancer une UncheckedIOException
        try  {
            return Files.readAllLines(Paths.get("AJ_atelier04/resources/streamingvf.csv")).stream();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    };

    private static final Supplier<Stream<Employee>> employee = () -> {
        return supplier.get()
                .map(Employee::new);
    };

    public static void main(String[] args) {

        System.out.println("1. Première ligne du fichier : [" + firstLine() + "]");
        System.out.println("\n3. Noms de famille de plus de 8 lettres contenant 'K' ou 'O' :\n" + filteredLastnames());
        System.out.println("\n4. Liste des comptes occurences de 'e' :\n" + occurencesOfE());
        System.out.println("\n5. Tous les emails se terminent-ils par 'streamingvf.be' ? :" + allEmailCorrect());
        System.out.println("\n6. Prénom d'un employé au log nom de famille : " + longLastName());
        System.out.println("\n7. Nombre d'employés à mi-temps : " + numbreOfPartTimers());
        System.out.println("\n8. Ids selon plein temps/mi-temps :");
        timeDistrubution().forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.print("\n9. Plus long nom du fichier : ");
        printLongestName();

    }

    /**
     * Utilise le Supplier pour retourner la première ligne du fichier streamingvf.csv
     * N'oubliez pas la gestion des exceptions grâce au try-with-resources
     * @return une String contenant la première ligne du fichier
     */
    private static String firstLine() {
        return supplier.get()
                .findFirst()
                .orElse("None");
    }


    /**
     * Filtre le stream pour retourner une liste des noms de famille (lastname) de plus de 8 lettres
     * qui contiennent la lettre 'O' ou la lettre 'K'
     * @return une liste de String de plus de 8 lettres contenant 'O' ou 'K'
     */
    private static List<String> filteredLastnames() {
        Predicate<String> predicate = s -> s.length() > 8;
        //      combiner predicate avec d'autres  (p.7 : "Predicate"), puis le passer en paramètre de
        //      de l'appel filter() pour filtrer les résultats.
        return employee.get()
                .map(s -> s.getLastname())
                .filter(predicate)
                .filter(s -> s.contains("O") || s.contains("K"))
                .toList();
    }

    /**
     * Calcule les occurrences du carcatère 'e' dans les prénoms des employés
     * @return une liste contenant le nombre d'occurrences de 'e' pour chaque prénom d'employé.
     */
    private static List<Integer> occurencesOfE() {
        //      Construire une BiFunction qui prend comme premier type de paramètre un Employee,
        //      comme deuxième type de paramètre un Character, et qui prend Integer comme type de retour.
        //      Cette BiFunction doit retourner le nombre d'occurrences du char passé en paramètre dans le
        //      prénom (firstname) de l'Employee passé en paramètre.
        //      Retourner une liste contenant le nombre d'occurences du caractère 'e' dans les
        //      prénoms de tous les employés en utilisant votre BiFunction.
        BiFunction <String, Character, Integer> occurences = (s, c) -> (int) s.chars().filter(ch -> ch == c).count();
        return employee.get()
                .map(s -> s.getFirstname())
                .map(s -> occurences.apply(s, 'e'))
                .toList();
    }

    /**
     * Vérifie que tous les emails des employés se terminent par "streamingvf.be"
     * @return true si tous les emails se terminent par "streamingvf.be", false sinon
     */
    private static boolean allEmailCorrect() {
        return employee.get()
                .map(s -> s.getEmail())
                .allMatch(s -> s.endsWith("streamingvf.be"));
    }

    /**
     * Retourne le prénom d'un employé dont le nom de famille a une longueur supérieure à 14,
     * ou une String contenant "None" si aucun ne correspond.
     * @return une String contenant un prénom ou "None"
     */
    private static String longLastName() {
        return employee.get()
                .map(s -> s.getLastname())
                .filter(s -> s.length() > 14)
                .findFirst()
                .orElse("None");
    }

    /**
     * Calcule le nombre d'employé à mi-temps de la boîte
     * @return le nombre d'employé à mi-temps de la boîte
     */
    private static long numbreOfPartTimers() {
        return employee.get()
                .filter(s -> !s.isFullTime())
                .count();
    }

    /**
     * Catégorise les employés selon qu'ils travaillent à mi-temps ou à plein temps.
     * @return une map de liste des id des employés selon qu'ils travaillent à mi-temps (false)
     *         ou à plein temps (true)
     */
    private static Map<Boolean, List<Integer>> timeDistrubution() {
        return employee.get()
                .collect(Collectors.partitioningBy(s -> s.isFullTime(), Collectors.mapping(s -> s.getId(), Collectors.toList())));
    }

    /**
     * Permet au Consumer d'utiliser le supplier défini en début de classe pour
     * effectuer ses opérations.
     * @param consumer un consommateur de Stream de String.
     */
    private static void withLines(Consumer<Stream<String>> consumer) {
        //      try-with-resources avec le Supplier. Le consumer doit utiliser (en utilisant sa méthode accept())
        //      le résultat du Supplier.
        try (Stream<String> stream = supplier.get()) {
            consumer.accept(stream);
        }
    }

    /**
     * Fournit un Consumer à withLines. Le Consumer doit print le plus long nom de famille du fichier
     */
    private static void printLongestName() {
        withLines( lines -> {
            //TODO: print le plus long nom de famille du fichier
            System.out.println(lines.map(s -> s.split(";")[1])
                    .max(Comparator.comparingInt(String::length))
                    .orElse("None"));
        });
    }



}
