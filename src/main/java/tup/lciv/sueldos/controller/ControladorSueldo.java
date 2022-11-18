/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tup.lciv.sueldos.controller;

import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import tup.lciv.sueldos.models.*;
import tup.lciv.sueldos.modelsDTO.EmpleadoDTO;
import tup.lciv.sueldos.modelsDTO.ReporteDTO;
import tup.lciv.sueldos.repository.RepositorioSueldo;


@RestController
public class ControladorSueldo {
    
    
    @Autowired
    private RepositorioSueldo repo;
    
    @GetMapping("getAreas")
    public ResponseEntity<List<Area>> getAreas(){
        try{
            List<Area> areas = repo.getAreas();
            
            if(areas.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(areas);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping("getProximoLegajo")
    public ResponseEntity<Integer> getProximoLegajo(){
        try{
            int legajo = repo.getProximoLegajo();
            
            return ResponseEntity.ok(legajo);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @PostMapping("agregarEmpleado")
    public ResponseEntity agregarEmpleado(@Valid @RequestBody Empleado empleado){
        try{
            repo.agregarEmpleado(empleado);
            return ResponseEntity.ok(null);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping("getEmpleados")
    public ResponseEntity<List<EmpleadoDTO>> getEmpleados(){
        try{
            List<EmpleadoDTO> empleados = repo.getEmpleados();
            
            if(empleados.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(empleados);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping("getEmpleado/{legajo}")
    public ResponseEntity<EmpleadoDTO> getEmpleado(@PathVariable int legajo){
        try{
            EmpleadoDTO empleado = repo.getEmpleado(legajo);
            
            return ResponseEntity.ok(empleado);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @PostMapping("generarRecibo")
    public ResponseEntity generarRecibo(@RequestBody Recibo recibo){
        try{
            repo.generarRecibo(recibo);
            return ResponseEntity.ok(null);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping("getRecibos/{legajo}")
    public ResponseEntity<List<Recibo>> getRecibos(@PathVariable int legajo){
        try{
            List<Recibo> recibos = repo.getRecibos(legajo);
            
            if(recibos.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(recibos);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
    @GetMapping("reporteSueldos/{anio}/{mes}")
    public ResponseEntity<List<ReporteDTO>> reporteSueldos(@PathVariable int anio,@PathVariable int mes){
        try{
            List<ReporteDTO> reportes = repo.reporteSueldos(anio, mes);
            
            if(reportes.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(reportes);
            
        }catch(SQLException ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }
    
}
