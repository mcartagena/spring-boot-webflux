package com.bolsadeideas.springboot.webflux.app;

import com.bolsadeideas.springboot.webflux.app.models.dao.ProductoDao;
import com.bolsadeideas.springboot.webflux.app.models.documents.Producto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

    @Autowired
    private ProductoDao dao;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args)  {

        mongoTemplate.dropCollection("productos").subscribe();

        Flux.just(
                Producto.builder().nombre("TV Panasonic Pantalla LCD").precio(456.89).build(),
                Producto.builder().nombre("Sony Camara HD Digital").precio(177.89).build(),
                Producto.builder().nombre("Apple iPod").precio(46.89).build(),
                Producto.builder().nombre("Sony Notebook").precio(846.89).build(),
                Producto.builder().nombre("Hewlett Packard Multifuntional").precio(200.89).build(),
                Producto.builder().nombre("Bianchi Bicicleta").precio(456.89).build(),
                Producto.builder().nombre("HP Notebook Omen 17").precio(2500.89).build(),
                Producto.builder().nombre("Mica Comoda 5 Cajones").precio(150.89).build(),
                Producto.builder().nombre("TV Sony Bravia OLED 4k Ultra HD").precio(2255.89).build()
        )
                .flatMap(producto -> {
                            producto.setCreadoEn(new Date());
                            return dao.save(producto);
                        }
                )
                .subscribe(producto -> log.info("Inserta: " + producto.getId() + " " + producto.getNombre()));
    }
}
