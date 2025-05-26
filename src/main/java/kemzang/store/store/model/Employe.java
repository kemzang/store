package model;

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
     @NotBlank(message="Lenomestobligatoire")
     @Size(min=2,max=64,message="Lenomdoitcontenirentre2et64caractères")
     private String nom;

     @Column(nullable=false)
     @Email(message="Emailnonvalide")
     private String email;

     @Column(nullable=false)
     @PastOrPresent(message="Ladated'embauchenepeutêtresupérieuràaujourd'hui")
     private LocalDate dateEmbauche;

     @Column(nullable=false)
     @Min(value=42000,message = "LesalairedoitêtresupérieurouégaleauSMIG")
     @Max(value=1000000,message ="Lesalairedoitêtreinférieurouégaleà1000000")
     private Integer salaire;

     @ManyToOne
     @JoinColumn(name="poste")
     private Poste poste;

     @ManyToOne
     @JoinColumn(name="departement")
     private Departement departement;
     }