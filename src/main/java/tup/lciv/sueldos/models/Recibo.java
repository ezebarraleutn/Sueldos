/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tup.lciv.sueldos.models;

import java.sql.Date;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recibo {
    
    private int idrecibo;
    private int anio;
    private int mes;
    private int legajo;
    private double montoAntiguedad;
    private double jubilacion;
    private double obraSocial;
    private double fondoAComplejidad;
    private double sueldoNeto;
    
}
