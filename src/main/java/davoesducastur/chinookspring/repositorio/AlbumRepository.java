package davoesducastur.chinookspring.repositorio;

import davoesducastur.chinookspring.modelo.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    List<Album> findByArtistaIdOrderByTitulo(Integer artistaId);
}