/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tup.lciv.sueldos.modelsDTO;

import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tup.lciv.sueldos.models.Area;
import tup.lciv.sueldos.models.Recibo;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoDTO {
    
    private int legajo;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private Area area;
    private double sueldoBruto;
    private int antiguedad;
    private List<Recibo> recibos;
    
}
