
package com.cine.cine.service;

import com.cine.cine.domain.Snack;
import com.cine.cine.repository.SnackRepository;
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
public class SnackService {

    private final SnackRepository snackRepository;
    private final FirebaseStorageService firebaseStorageService;

    public SnackService(SnackRepository snackRepository, FirebaseStorageService firebaseStorageService) {
        this.snackRepository = snackRepository;
        this.firebaseStorageService = firebaseStorageService;
    }

    @Transactional(readOnly = true)
    public List<Snack> getSnacks(boolean activos) {
        if (activos) {
            return snackRepository.findByActivoTrue();
        }
        return snackRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Snack> getSnack(Integer idSnack) {
        return snackRepository.findById(idSnack);
    }

    @Transactional
    public void save(Snack snack, MultipartFile imagenFile) {
        snack = snackRepository.save(snack);
        if (imagenFile != null && !imagenFile.isEmpty()) {
            try {
                String rutaImagen = firebaseStorageService.uploadImage(imagenFile, "snack", snack.getIdSnack());
                snack.setRutaImagen(rutaImagen);
                snackRepository.save(snack);
            } catch (IOException e) {
                throw new IllegalStateException("No se pudo subir la imagen.", e);
            }
        }
    }

    @Transactional
    public void delete(Integer idSnack) {
        snackRepository.deleteById(idSnack);
    }
}