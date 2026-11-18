package davoesducastur.chinookspring.controlador;

import davoesducastur.chinookspring.dto.GeneroResumen;
import davoesducastur.chinookspring.servicio.GeneroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * Páginas de los géneros musicales.
 * Todas las rutas empiezan por /generos.
 */
@Controller
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    /**
     * Muestra el listado de todos los géneros ordenados alfabéticamente con su número de pistas.
     *
     * @return la página HTML completa del listado de géneros
     */
    @GetMapping
    @ResponseBody
    public String listar() {
        List<GeneroResumen> generos = generoService.listar();
        StringBuilder cuerpo = new StringBuilder("<ul>");
        for (GeneroResumen genero : generos) {
            cuerpo.append("<li>").append(Html.esc(genero.nombre())).append(" (")
                    .append(genero.numeroPistas()).append(" pistas)</li>");
        }
        cuerpo.append("</ul>");
        return Html.pagina("Géneros", cuerpo.toString());
    }
}