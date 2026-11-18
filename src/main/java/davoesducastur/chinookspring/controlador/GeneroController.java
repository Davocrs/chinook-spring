package davoesducastur.chinookspring.controlador;

import davoesducastur.chinookspring.dto.GeneroResumen;
import davoesducastur.chinookspring.servicio.GeneroService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/generos")
public class GeneroController {

    private final GeneroService generoService;

    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    @GetMapping
    @ResponseBody
    public String listar() {
        StringBuilder html = new StringBuilder();
        html.append("<h1>Géneros</h1><ul>");
        for (GeneroResumen genero : generoService.listar()) {
            html.append("<li>").append(Html.esc(genero.nombre()))
                    .append(" (").append(genero.numeroPistas()).append(" pistas)</li>");
        }
        html.append("</ul>");
        return html.toString();
    }
}