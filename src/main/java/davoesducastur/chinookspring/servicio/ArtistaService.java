package davoesducastur.chinookspring.servicio;

import davoesducastur.chinookspring.dto.ArtistaDetalle;
import davoesducastur.chinookspring.dto.ArtistaResumen;
import java.util.List;

public interface ArtistaService {
    List<ArtistaResumen> buscarPorNombre(String texto);
    ArtistaDetalle buscarPorId(Integer id);
}
