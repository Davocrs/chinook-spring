package davoesducastur.chinookspring.servicio;

import davoesducastur.chinookspring.dto.ArtistaDetalle;
import davoesducastur.chinookspring.modelo.Album;
import davoesducastur.chinookspring.modelo.Artista;
import davoesducastur.chinookspring.repositorio.AlbumRepository;
import davoesducastur.chinookspring.repositorio.ArtistaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistaServiceImplTest {

    @Mock
    private ArtistaRepository artistaRepository;

    @Mock
    private AlbumRepository albumRepository;

    @InjectMocks
    private ArtistaServiceImpl artistaService;

    @Test
    void buscarPorIdDevuelveLaFichaConSusAlbumes() {
        Artista acdc = new Artista();
        acdc.setId(1);
        acdc.setNombre("AC/DC");
        Album album = new Album(4, "Let There Be Rock", acdc);
        when(artistaRepository.findById(1)).thenReturn(Optional.of(acdc));
        when(albumRepository.findByArtistaIdOrderByTitulo(1)).thenReturn(List.of(album));

        ArtistaDetalle ficha = artistaService.buscarPorId(1);

        assertEquals("AC/DC", ficha.nombre());
        assertEquals(1, ficha.albumes().size());
        assertEquals("Let There Be Rock", ficha.albumes().get(0).titulo());
    }

    @Test
    void buscarPorIdLanzaNoEncontradoSiNoExiste() {
        when(artistaRepository.findById(9999)).thenReturn(Optional.empty());

        try {
            artistaService.buscarPorId(9999);
            fail("Tendría que haber lanzado NoEncontradoException");
        } catch (NoEncontradoException e) {
            assertEquals("No existe el artista 9999", e.getMessage());
        }
    }
}
