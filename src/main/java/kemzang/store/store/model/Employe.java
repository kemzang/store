package kemzang.store.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name="employes")
@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employe{

     @Id
     @GeneratedValue
     private UUID id;

     @Column(nullable=false)
     @NotBlank(message="Le nom est obligatoire")
     @Size(min=2,max=64,message="Le nom doit contenir entre 2 et 64 caractères")
     private String nom;

     @Column(nullable=false)
     @Email(message="Email non valide")
     private String email;

     @Column(nullable=false)
     @PastOrPresent(message="La date d'embauche ne peut être supérieur à aujourd'hui")
     private LocalDate dateEmbauche;

     @Column(nullable=false)
     @Min(value=42000,message = "Le salaire doit être supérieur ou égale au SMIG")
     @Max(value=1000000,message ="Le salaire doit être inférieur ou égale à 1000000")
     private Integer salaire;

     @ManyToOne
     @JoinColumn(name="poste")
     private Poste poste;

     @ManyToOne
     @JoinColumn(name="departement")
     private Departement departement;

     @Override
     public String toString() {
          return "Employe{" +
                  "id=" + id +
                  ", nom='" + nom + '\'' +
                  ", email='" + email + '\'' +
                  ", dateEmbauche=" + dateEmbauche +
                  ", salaire=" + salaire +
                  ", poste=" + (poste != null ? poste.getLibellePoste() : "null") +  // Évitez d'appeler toString() sur l'objet complet
                  ", departement=" + (departement != null ? departement.getLibelleDepartement() : "null") +
                  '}';
     }
     }