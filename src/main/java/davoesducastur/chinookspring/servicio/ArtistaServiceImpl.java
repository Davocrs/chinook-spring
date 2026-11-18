package davoesducastur.chinookspring.servicio;

import davoesducastur.chinookspring.dto.AlbumResumen;
import davoesducastur.chinookspring.dto.ArtistaDetalle;
import davoesducastur.chinookspring.dto.ArtistaResumen;
import davoesducastur.chinookspring.modelo.Album;
import davoesducastur.chinookspring.modelo.Artista;
import davoesducastur.chinookspring.repositorio.AlbumRepository;
import davoesducastur.chinookspring.repositorio.ArtistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtistaServiceImpl implements ArtistaService {

    private final ArtistaRepository artistaRepository;
    private final AlbumRepository albumRepository;

    public ArtistaServiceImpl(ArtistaRepository artistaRepository, AlbumRepository albumRepository) {
        this.artistaRepository = artistaRepository;
        this.albumRepository = albumRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArtistaResumen> buscarPorNombre(String texto) {
        List<ArtistaResumen> resultado = new ArrayList<>();
        for (Artista artista : artistaRepository.findByNombreContainingIgnoreCaseOrderByNombre(texto)) {
            resultado.add(new ArtistaResumen(artista.getId(), artista.getNombre()));
        }
        return resultado;
    }

    @Override
    @Transactional(readOnly = true)
    public ArtistaDetalle buscarPorId(Integer id) {
        Optional<Artista> artista = artistaRepository.findById(id);
        if (artista.isEmpty()) {
            throw new NoEncontradoException("No existe el artista " + id);
        }
        List<AlbumResumen> albumes = new ArrayList<>();
        for (Album album : albumRepository.findByArtistaIdOrderByTitulo(id)) {
            albumes.add(new AlbumResumen(album.getId(), album.getTitulo()));
        }
        return new ArtistaDetalle(artista.get().getId(), artista.get().getNombre(), albumes);
    }
}