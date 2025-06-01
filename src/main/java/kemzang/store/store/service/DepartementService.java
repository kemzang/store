package kemzang.store.store.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import kemzang.store.store.model.Departement;
import org.springframework.stereotype.Service;
import kemzang.store.store.repositories.DepartementRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DepartementService {

    private final DepartementRepository departementRepository;

          //AjouterunnouveaudépartementenBD
          public Departement addDepartement(Departement departement){
         return departementRepository.save(departement);
         }

          //Recupérerlalistedetous lespostesquiexistentdanslaBD
          public List<Departement>allDepartements(){
         return departementRepository.findAll();
         }

         //RecupérerunpostequiexistedanslaBDparsonID
          public Optional<Departement>getDepartementById(UUID id){
         return departementRepository.findById(id);

         }

         //Modifierlesinformationssurunposte(existingDepartement)dejàexistantenBD
          public Departement updateDepartement(UUID id,Departement updatedDepartement){
         return departementRepository.findById(id).map(
                existingDepartement->{
                     existingDepartement.setLibelleDepartement(updatedDepartement.getLibelleDepartement());
                             return departementRepository.save(existingDepartement);
                     }).orElseThrow(()->new RuntimeException("Département non trouvé!"));
         }

          //SupprimerdelaBDunposteparsonID
          public void deleteDepartement(UUID id){
         // Siledépartementasupprimern'existepasilfautafficherunmessage
         if(!departementRepository.existsById(id)){
             throw new EntityNotFoundException("Ledépartementavecid"+id
             + "n'existepas!");
             }
         departementRepository.deleteById(id);
         }
}
