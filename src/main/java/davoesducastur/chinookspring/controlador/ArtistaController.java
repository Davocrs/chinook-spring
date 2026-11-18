package davoesducastur.chinookspring.controlador;

import davoesducastur.chinookspring.dto.AlbumResumen;
import davoesducastur.chinookspring.dto.ArtistaDetalle;
import davoesducastur.chinookspring.dto.ArtistaResumen;
import davoesducastur.chinookspring.servicio.ArtistaService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * Páginas de los artistas: buscador y ficha.
 * Todas las rutas empiezan por /artistas.
 */
@Controller
@RequestMapping("/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    /**
     * Redirige a la página de búsqueda.
     *
     * @return la orden de redirección a /artistas/buscar (código 302)
     */
    @GetMapping
    public String inicio() {
        return "redirect:/artistas/buscar";
    }

    /**
     * Muestra el buscador y, si se ha escrito un texto, los artistas cuyo nombre lo contiene.
     *
     * @param texto texto buscado; vacío si no viene en la URL
     * @return la página con el formulario y los resultados
     */
    @GetMapping("/buscar")
    @ResponseBody
    public String buscar(@RequestParam(defaultValue = "") String texto) {
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("<form action=\"/artistas/buscar\" method=\"get\">")
                .append("<input name=\"texto\" value=\"").append(Html.esc(texto)).append("\">")
                .append(" <button>Buscar</button></form>");
        if (!texto.isBlank()) {
            List<ArtistaResumen> artistas = artistaService.buscarPorNombre(texto);
            cuerpo.append("<p>Artistas que contienen «").append(Html.esc(texto)).append("»: ")
                    .append(artistas.size()).append("</p><ul>");
            for (ArtistaResumen artista : artistas) {
                cuerpo.append("<li><a href=\"/artistas/").append(artista.id()).append("\">")
                        .append(Html.esc(artista.nombre())).append("</a></li>");
            }
            cuerpo.append("</ul>");
        }
        return Html.pagina("Buscar artistas", cuerpo.toString());
    }

    /**
     * Muestra la ficha de un artista con sus álbumes.
     *
     * @param id identificador del artista, tomado de la ruta
     * @return la página de la ficha
     * @throws davoesducastur.chinookspring.servicio.NoEncontradoException si el artista no existe (la traduce a 404 ManejadorErrores)
     */
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        ArtistaDetalle artista = artistaService.buscarPorId(id);
        StringBuilder cuerpo = new StringBuilder();
        if (artista.albumes().isEmpty()) {
            cuerpo.append("<p>Este artista no tiene álbumes en la tienda.</p>");
        } else {
            cuerpo.append("<p>").append(artista.albumes().size()).append(" álbumes:</p><ul>");
            for (AlbumResumen album : artista.albumes()) {
                cuerpo.append("<li><a href=\"/albumes/").append(album.id()).append("\">")
                        .append(Html.esc(album.titulo())).append("</a></li>");
            }
            cuerpo.append("</ul>");
        }
        return Html.pagina(artista.nombre(), cuerpo.toString());
    }
}