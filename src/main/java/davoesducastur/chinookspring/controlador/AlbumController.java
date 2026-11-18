package davoesducastur.chinookspring.controlador;

import davoesducastur.chinookspring.dto.AlbumDetalle;
import davoesducastur.chinookspring.dto.PistaResumen;
import davoesducastur.chinookspring.servicio.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Páginas de los álbumes.
 * Todas las rutas empiezan por /albumes.
 */
@Controller
@RequestMapping("/albumes")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    /**
     * Muestra la ficha de un álbum con su artista y su tabla de pistas.
     *
     * @param id identificador del álbum, tomado de la ruta
     * @return la página HTML completa de la ficha del álbum
     * @throws davoesducastur.chinookspring.servicio.NoEncontradoException si el álbum no existe
     */
    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        AlbumDetalle album = albumService.buscarPorId(id);
        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("<p>De <a href=\"/artistas/").append(album.artista().id()).append("\">")
                .append(Html.esc(album.artista().nombre())).append("</a></p>");
        cuerpo.append("<table border=\"1\"><tr><th>#</th><th>Pista</th><th>Duración</th></tr>");
        int numero = 1;
        for (PistaResumen pista : album.pistas()) {
            cuerpo.append("<tr><td>").append(numero).append("</td><td>")
                    .append(Html.esc(pista.nombre())).append("</td><td>")
                    .append(pista.duracion()).append("</td></tr>");
            numero++;
        }
        cuerpo.append("</table>");
        return Html.pagina(album.titulo(), cuerpo.toString());
    }
}