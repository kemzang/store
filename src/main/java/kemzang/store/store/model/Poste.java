package kemzang.store.store.model;

import jakarta.persistence.*;
  import jakarta.validation.constraints.*;
  import lombok.*;
 
  import java.util.List;
  import java.util.UUID;

 
  @Entity
  @Table(name="postes")
  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @ToString

public class Poste {
    @Id
  @GeneratedValue
  private UUID id;
 
  @Enumerated(EnumType.STRING)
  @Column(nullable=false)
  private TypePoste libellePoste;
 
  @Column(nullable=false)
  @Min(value=42000,message = "Le salaire minimum doit être au dessus du SMIG")
  private Integer salaireMin;
 
  @Column(nullable=false)
  @Max(value=1000000,message ="Le salaire max est de 1million")
  private Integer salaireMax;
 
  @OneToMany(mappedBy="poste",cascade=CascadeType.ALL)
  private List<Employe> employes;
 
  @AssertTrue(message="Le salaire maximum doit être supérieur au salire minimum")
  public boolean isSalaireValide(){
  //pouréviterNullPointerException
  if (salaireMin==null|| salaireMax==null) return true;
  return salaireMax>salaireMin;
  }

    @Override
    public String toString() {
      return "Poste{" +
              "id=" + id +
              ", libellePoste='" + libellePoste + '\'' +
              '}';
    }
}
