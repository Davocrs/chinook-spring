package davoesducastur.chinookspring.dto;

import java.util.List;

public record ArtistaDetalle(Integer id, String nombre, List<AlbumResumen> albumes) {
}