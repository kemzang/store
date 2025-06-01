package kemzang.store.store.repositories;

import kemzang.store.store.model.Departement;
import kemzang.store.store.model.Employe;
import kemzang.store.store.model.Poste;
import kemzang.store.store.model.TypePoste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeRepository extends JpaRepository<Employe, UUID> {
    List<Employe> findEmployeByPoste_Id(UUID id);
    List<Employe> findEmployeByDepartement_Id(UUID id);
}
