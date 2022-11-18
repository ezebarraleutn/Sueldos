/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tup.lciv.sueldos.models;

import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    
    private int legajo;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private Area area;
    private double sueldoBruto;
    private Date fechaIngreso;
    private List<Recibo> recibos;
    
}
