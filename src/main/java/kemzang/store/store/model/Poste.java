package model;

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
  @NotBlank(message="Lelibelléduposteestobligatoire")
  private TypePoste libellePoste;
 
  @Column(nullable=false)
  @Min(value=42000,message = "LesalaireminimumdoitêtreaudessusduSMIG")
  private Integer salaireMin;
 
  @Column(nullable=false)
  @Max(value=1000000,message ="Lesalairemaxestde1million")
  private Integer salaireMax;
 
  @OneToMany(mappedBy="poste",cascade=CascadeType.ALL)
  private List<Employe> employes;
 
  @AssertTrue(message="Lesalairemaximumdoitêtresupérieurausalireminimum")
  public boolean isSalaireValide(){
  //pouréviterNullPointerException
  if (salaireMin==null|| salaireMax==null) return true;
  return salaireMax>salaireMin;
  }
}
