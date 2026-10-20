package davoesducastur.chinookspring.operaciones;

import davoesducastur.chinookspring.modelo.Album;
import davoesducastur.chinookspring.modelo.Artista;
import davoesducastur.chinookspring.repositorio.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ConsultasArtistas {

    private final ArtistaRepository artistaRepository;

    @Transactional(readOnly = true)
    public List<String> titulosDeAlbumes(Integer artistaId) {
        Optional<Artista> artista = artistaRepository.findById(artistaId);
        if (artista.isEmpty()) {
            throw new IllegalArgumentException("No existe el artista " + artistaId);
        }
        List<String> titulos = new ArrayList<>();
        for (Album album : artista.get().getAlbumes()) {
            titulos.add(album.getTitulo());
        }
        Collections.sort(titulos);
        return titulos;
    }
}