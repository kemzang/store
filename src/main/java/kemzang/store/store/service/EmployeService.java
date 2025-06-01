package kemzang.store.store.service;

import kemzang.store.store.dto.EmployeDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import kemzang.store.store.model.Departement;
import kemzang.store.store.model.Employe;
import kemzang.store.store.model.Poste;
import kemzang.store.store.model.TypePoste;
import org.springframework.stereotype.Service;
import kemzang.store.store.repositories.EmployeRepository;
import kemzang.store.store.repositories.PosteRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final PosteRepository posteRepository;

          //AjouterunnouvelemployedanslaBD
          public Employe addEmploye(Employe employe){

         return employeRepository.save(employe);
         }

         //RecupérerlalistedetouslesemployesquiexistentdanslaBD
         public List<EmployeDTO>getAllEmployes(){
         return employeRepository.findAll()
         .stream()
         .map(this::mapToDTO)
         .collect(Collectors.toList());
         }

          //RecupérerunemployéquiexistedanslaBDparsonID
          public EmployeDTO getEmployeById(UUID id){
         Employe employe=employeRepository.findById(id)
         .orElseThrow(()->new RuntimeException("Employé non trouvé!"));
         return mapToDTO(employe);
         }

          //RecupérerlalistedesemployésparPoste
          public List<EmployeDTO>getAllEmployeesByPoste(UUID id){
         return employeRepository.findEmployeByPoste_Id(id)
         .stream()
         .map(this::mapToDTO)
         .collect(Collectors.toList());
        }

         //RecupérerlalistedesemployésparDepartement
         public List<EmployeDTO>getAllEmployeesByDepartement(UUID id){
         return employeRepository.findEmployeByDepartement_Id(id)
         .stream()
         .map(this::mapToDTO)
         .collect(Collectors.toList());
         }

          //Modifierlesinformationssurunemploye(existingEmploye)dejàexistantenBD
          public Employe updateEmploye(UUID id,Employe updatedEmploye){
         return employeRepository.findById(id).map(
                 existingEmploye->{
                     existingEmploye.setNom(updatedEmploye.getNom());
                     existingEmploye.setPoste(updatedEmploye.getPoste());
                     existingEmploye.setDepartement(updatedEmploye.getDepartement());
                     existingEmploye.setDateEmbauche(updatedEmploye.getDateEmbauche());
                     existingEmploye.setEmail(updatedEmploye.getEmail());
                     existingEmploye.setSalaire(updatedEmploye.getSalaire());

                     return employeRepository.save(existingEmploye);
                     }
                 ).orElseThrow(()->new RuntimeException("Employénontrouvé!"));
         }

         //SupprimerdelaBDunemployeparsonID
        public void deleteEmploye(UUID id){
        if (!employeRepository.existsById(id)){

            throw new EntityNotFoundException("L'employé avec id " + id +
                    " n'existe pas !");
        }
        employeRepository.deleteById(id);
    }
    // Methode 1 de conversion d'une entité en entiteDTO
    public EmployeDTO mapToDTO(Employe employe) {
        return new EmployeDTO(
                employe.getId(),
                employe.getNom(),
                employe.getEmail(),
                Period.between(employe.getDateEmbauche(), LocalDate.now()).getYears(),
                employe.getSalaire(),
                employe.getPoste().getLibellePoste(),
                employe.getDepartement().getLibelleDepartement()
        );
    }
}
