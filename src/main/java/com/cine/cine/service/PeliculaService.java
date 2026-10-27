package com.cine.cine.service;

import com.cine.cine.domain.Pelicula;
import com.cine.cine.repository.PeliculaRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author barqu
 */
@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;
    private final FirebaseStorageService firebaseStorageService;

    public PeliculaService(PeliculaRepository peliculaRepository, FirebaseStorageService firebaseStorageService) {
        this.peliculaRepository = peliculaRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Pelicula> getPeliculas(boolean activos) {
        if (activos) {
            return peliculaRepository.findByActivoTrue();
        }
        return peliculaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Pelicula> getPelicula(Integer idPelicula) {
        return peliculaRepository.findById(idPelicula);
    }

    @Transactional
    public void save(Pelicula pelicula, MultipartFile imagenFile) {
        pelicula = peliculaRepository.save(pelicula);
        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.uploadImage(imagenFile, "pelicula", pelicula.getIdPelicula());
                pelicula.setRutaImagen(rutaImagen);
                peliculaRepository.save(pelicula);
            } catch (IOException e) {
                throw new IllegalStateException("No se pudo subir la imagen.", e);
            }
        }
    }

    @Transactional
    public void delete(Integer idPelicula) {
        peliculaRepository.deleteById(idPelicula);
    }
}