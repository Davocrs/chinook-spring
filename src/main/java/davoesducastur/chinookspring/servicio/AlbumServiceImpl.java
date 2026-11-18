package davoesducastur.chinookspring.servicio;

import davoesducastur.chinookspring.dto.AlbumDetalle;
import davoesducastur.chinookspring.dto.ArtistaResumen;
import davoesducastur.chinookspring.dto.PistaResumen;
import davoesducastur.chinookspring.modelo.Album;
import davoesducastur.chinookspring.modelo.Pista;
import davoesducastur.chinookspring.repositorio.AlbumRepository;
import davoesducastur.chinookspring.repositorio.PistaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceImpl implements AlbumService {

    private final AlbumRepository albumRepository;
    private final PistaRepository pistaRepository;

    public AlbumServiceImpl(AlbumRepository albumRepository, PistaRepository pistaRepository) {
        this.albumRepository = albumRepository;
        this.pistaRepository = pistaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AlbumDetalle buscarPorId(Integer id) {
        Optional<Album> encontrado = albumRepository.findById(id);
        if (encontrado.isEmpty()) {
            throw new NoEncontradoException("No existe el álbum " + id);
        }
        Album album = encontrado.get();
        ArtistaResumen artista = new ArtistaResumen(album.getArtista().getId(), album.getArtista().getNombre());
        List<PistaResumen> pistas = new ArrayList<>();
        for (Pista pista : pistaRepository.findByAlbumIdOrderById(id)) {
            pistas.add(new PistaResumen(pista.getId(), pista.getNombre(), pista.getDuracion(),
                    album.getId(), album.getTitulo()));
        }
        return new AlbumDetalle(album.getId(), album.getTitulo(), artista, pistas);
    }
}