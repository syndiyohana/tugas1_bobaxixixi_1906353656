package apap.tugas.bobaxixixi.repository;

import apap.tugas.bobaxixixi.model.BobaTeaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BobaTeaDB extends JpaRepository<BobaTeaModel, Long>{
    Optional<BobaTeaModel> findByidBobaTea(Long idBobaTea);
}
