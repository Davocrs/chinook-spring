package davoesducastur.chinookspring.operaciones;

import davoesducastur.chinookspring.modelo.Pista;
import davoesducastur.chinookspring.modelo.Playlist;
import davoesducastur.chinookspring.repositorio.PistaRepository;
import davoesducastur.chinookspring.repositorio.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GestorPlaylists {

    private final PlaylistRepository playlistRepository;
    private final PistaRepository pistaRepository;

    @Transactional
    public boolean anadirPista(Integer playlistId, Integer pistaId) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (playlist.isEmpty()) {
            throw new IllegalArgumentException("No existe la playlist " + playlistId);
        }
        Optional<Pista> pista = pistaRepository.findById(pistaId);
        if (pista.isEmpty()) {
            throw new IllegalArgumentException("No existe la pista " + pistaId);
        }
        return playlist.get().getPistas().add(pista.get());
    }
}