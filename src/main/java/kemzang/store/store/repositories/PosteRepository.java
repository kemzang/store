package kemzang.store.store.repositories;

import org.springframework.stereotype.Repository;

import kemzang.store.store.model.Employe;
import kemzang.store.store.model.Poste;
import kemzang.store.store.model.TypePoste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PosteRepository  extends JpaRepository<Poste, UUID> {
    // Pour eviter les doublons sur les postes en BD
    boolean existsDistinctByLibellePoste(TypePoste libellePoste);
}
