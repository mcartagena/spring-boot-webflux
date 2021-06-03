package com.bolsadeideas.springboot.webflux.app.models.documents;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="productos")
public class Producto {
    @Id
    private String id;

    private String nombre;
    private Double precio;
    private Date creadoEn;
}
