package davoesducastur.chinookspring.controlador;

import davoesducastur.chinookspring.dto.AlbumResumen;
import davoesducastur.chinookspring.dto.ArtistaDetalle;
import davoesducastur.chinookspring.servicio.ArtistaService;
import davoesducastur.chinookspring.servicio.NoEncontradoException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArtistaController.class)
class ArtistaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistaService artistaService;

    @Test
    void fichaMuestraElNombreYLosAlbumes() throws Exception {
        ArtistaDetalle acdc = new ArtistaDetalle(1, "AC/DC", List.of(new AlbumResumen(4, "Let There Be Rock")));
        when(artistaService.buscarPorId(1)).thenReturn(acdc);

        mockMvc.perform(get("/artistas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>AC/DC</h1>")))
                .andExpect(content().string(containsString("href=\"/albumes/4\"")));
    }

    @Test
    void fichaDeArtistaInexistenteDevuelve404() throws Exception {
        when(artistaService.buscarPorId(9999)).thenThrow(new NoEncontradoException("No existe el artista 9999"));

        mockMvc.perform(get("/artistas/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No existe el artista 9999")));
    }

    @Test
    void idQueNoEsUnNumeroDevuelve400() throws Exception {
        mockMvc.perform(get("/artistas/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void elBuscadorEscapaLoQueEscribeElUsuario() throws Exception {
        when(artistaService.buscarPorNombre("<b>hola")).thenReturn(List.of());

        mockMvc.perform(get("/artistas/buscar").param("texto", "<b>hola"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("«&lt;b&gt;hola»")));
    }

    @Test
    void unNombreConApostrofoSeEscapa() throws Exception {
        ArtistaDetalle guns = new ArtistaDetalle(88, "Guns N' Roses", List.of());
        when(artistaService.buscarPorId(88)).thenReturn(guns);

        mockMvc.perform(get("/artistas/88"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Guns N&#39; Roses")));
    }

    @Test
    void unaRutaGetPedidaConPostDevuelve405() throws Exception {
        mockMvc.perform(post("/artistas/1"))
                .andExpect(status().isMethodNotAllowed());
    }
}