/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cine.cine.service;
import com.cine.cine.domain.Empresa;
import com.cine.cine.repository.EmpresaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author barqu
 */
@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Transactional(readOnly = true)
    public List<Empresa> getEmpresas(boolean activos) {
        if (activos) {
            return empresaRepository.findByActivoTrue();
        }
        return empresaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Empresa> getEmpresa(Integer idEmpresa) {
        return empresaRepository.findById(idEmpresa);
    }

    @Transactional
    public void save(Empresa empresa) {
        empresaRepository.save(empresa);
    }

    @Transactional
    public void delete(Integer idEmpresa) {
        empresaRepository.deleteById(idEmpresa);
    }
}
