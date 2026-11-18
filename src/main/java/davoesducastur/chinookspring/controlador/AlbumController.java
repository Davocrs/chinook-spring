package davoesducastur.chinookspring.controlador;

import davoesducastur.chinookspring.dto.AlbumDetalle;
import davoesducastur.chinookspring.dto.PistaResumen;
import davoesducastur.chinookspring.servicio.AlbumService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/albumes")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String ficha(@PathVariable Integer id) {
        AlbumDetalle album = albumService.buscarPorId(id);
        StringBuilder html = new StringBuilder();
        html.append("<h1>").append(Html.esc(album.titulo())).append("</h1>");
        html.append("<p>De <a href=\"/artistas/").append(album.artista().id()).append("\">")
                .append(Html.esc(album.artista().nombre())).append("</a></p>");
        html.append("<ol>");
        for (PistaResumen pista : album.pistas()) {
            html.append("<li>").append(Html.esc(pista.nombre()))
                    .append(" (").append(pista.duracion()).append(")</li>");
        }
        html.append("</ol>");
        return html.toString();
    }
}
