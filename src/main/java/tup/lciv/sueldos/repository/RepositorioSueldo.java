package tup.lciv.sueldos.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.ApplicationScope;
import tup.lciv.sueldos.models.Area;
import tup.lciv.sueldos.models.Empleado;
import tup.lciv.sueldos.models.Recibo;
import tup.lciv.sueldos.modelsDTO.EmpleadoDTO;
import tup.lciv.sueldos.modelsDTO.ReporteDTO;

@Repository
@ApplicationScope
public class RepositorioSueldo {

    @Autowired
    private DataSource source;

    public ArrayList<Area> getAreas() throws SQLException {

        ArrayList<Area> areas = new ArrayList<Area>();

        try {
            Connection cnn = source.getConnection();

            Statement st = (Statement) cnn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM area");

            while (rs.next()) {

                int idarea = rs.getInt(1);
                String nombre = rs.getString(2);

                Area a = new Area(idarea, nombre);

                areas.add(a);
            }

            rs.close();
            st.close();
            cnn.close();

        } catch (SQLException ex) {
            throw ex;
        }

        return areas;
    }

    public int getProximoLegajo() throws SQLException {

        int legajo = 0;

        try {
            Connection cnn = source.getConnection();

            Statement st = (Statement) cnn.createStatement();

            ResultSet rs = st.executeQuery("SELECT max(legajo) + 1 FROM empleado");

            while (rs.next()) {

                legajo = rs.getInt(1);

            }

            rs.close();
            st.close();
            cnn.close();

            return legajo;

        } catch (SQLException ex) {
            throw ex;
        }
    }

    public void agregarEmpleado(Empleado e) throws SQLException {

        try {
            
            int legajo = getProximoLegajo();
            
            Connection cnn = source.getConnection();

            PreparedStatement pst = cnn.prepareStatement("INSERT INTO empleado (legajo, nombre, apellido, fechaNacimiento, idarea, sueldobruto, fechaIngreso) VALUES(?,?, ?, ?, ?, ?, ?)");

            pst.setInt(1, legajo);
            pst.setString(2, e.getNombre());
            pst.setString(3, e.getApellido());
            pst.setDate(4, e.getFechaNacimiento());
            pst.setInt(5, e.getArea().getIdarea());
            pst.setDouble(6, e.getSueldoBruto());
            pst.setDate(7, e.getFechaIngreso());

            pst.executeUpdate();

            pst.close();
            cnn.close();

        } catch (SQLException ex) {
            throw ex;
        }

    }

    public List<EmpleadoDTO> getEmpleados() throws SQLException {

        List<EmpleadoDTO> empleados = new ArrayList<EmpleadoDTO>();

        try {
            Connection cnn = source.getConnection();

            Statement st = (Statement) cnn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM empleado");

            while (rs.next()) {

                int legajo = rs.getInt(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                Date fechaNacimiento = rs.getDate(4);
                int idarea = rs.getInt(5);
                double sueldoBruto = rs.getDouble(6);
                Date fechaIngreso = rs.getDate(7);

                LocalDateTime hoy = LocalDateTime.now();

                int antiguedad = (int) (fechaIngreso.toLocalDate().getYear() - hoy.getYear());

                PreparedStatement pst = cnn.prepareStatement("SELECT * FROM area where idarea = ?");
                pst.setInt(1, idarea);
                ResultSet rss = pst.executeQuery();

                while (rss.next()) {

                    String nombrearea = rss.getString(2);
                    Area area = new Area(idarea, nombrearea);

                    PreparedStatement pstt = cnn.prepareStatement("SELECT * FROM recibo where legajo = ?");
                    pstt.setInt(1, legajo);
                    ResultSet rst = pstt.executeQuery();

                    List<Recibo> recibos = new ArrayList<Recibo>();

                    while (rst.next()) {

                        int idrecibo = rst.getInt(1);
                        int anio = rst.getInt(2);
                        int mes = rst.getInt(3);
                        double montoAntiguedad = rst.getDouble(5);
                        double jubilacion = rst.getDouble(6);
                        double obraSocial = rst.getDouble(7);
                        double fondoAComplejidad = rst.getDouble(8);
                        double sueldoNeto = rst.getDouble(9);

                        Recibo recibo = new Recibo(idrecibo, anio, mes, legajo, montoAntiguedad, jubilacion, obraSocial, fondoAComplejidad, sueldoNeto);
                        recibos.add(recibo);

                    }

                    EmpleadoDTO e = new EmpleadoDTO(legajo, nombre, apellido, fechaNacimiento, area, sueldoBruto, antiguedad, recibos);
                    empleados.add(e);
                }
            }

            rs.close();
            st.close();
            cnn.close();

        } catch (SQLException ex) {
            throw ex;
        }

        return empleados;
    }

    public EmpleadoDTO getEmpleado(int legajo) throws SQLException {
        
        EmpleadoDTO empleado = new EmpleadoDTO();

        List<EmpleadoDTO> empleados = getEmpleados();
        
        for (EmpleadoDTO e : empleados) {
            if (e.getLegajo() == legajo) {
                empleado = e;
                break;
            }
        }
        
        return empleado;
    }
    
    public int getProximoIdRecibo() throws SQLException {

        int idrecibo = 0;

        try {
            Connection cnn = source.getConnection();

            Statement st = (Statement) cnn.createStatement();

            ResultSet rs = st.executeQuery("SELECT max(idrecibo) + 1 FROM recibo");

            while (rs.next()) {

                idrecibo = rs.getInt(1);

            }

            rs.close();
            st.close();
            cnn.close();

            return idrecibo;

        } catch (SQLException ex) {
            throw ex;
        }
    }

    public void generarRecibo(Recibo r) throws SQLException {

        try {
            
            int idrecibo = getProximoIdRecibo(); 
            
            Connection cnn = source.getConnection();

            EmpleadoDTO e = getEmpleado(r.getLegajo());

            PreparedStatement pst = cnn.prepareStatement("INSERT INTO recibo (idrecibo, anio, mes, legajo, montoAntiguedad, jubilacion, obraSocial, fondoAComplejidad, sueldoNeto) \n"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

            pst.setInt(1, idrecibo);
            pst.setInt(2, r.getAnio());
            pst.setInt(3, r.getMes());
            pst.setInt(4, r.getLegajo());
            pst.setDouble(5, r.getMontoAntiguedad());
            pst.setDouble(6, r.getJubilacion());
            pst.setDouble(7, r.getObraSocial());
            pst.setDouble(8, r.getFondoAComplejidad());
            double sueldoNeto = (double) e.getSueldoBruto() + (double) r.getMontoAntiguedad() - (double) r.getFondoAComplejidad() - (double) r.getJubilacion() - (double) r.getObraSocial();
            pst.setDouble(9, sueldoNeto);

            pst.executeUpdate();

            pst.close();
            cnn.close();

        } catch (SQLException ex) {
            throw ex;
        }

    }

    public List<Recibo> getRecibos(int legajo) throws SQLException {

        List<Recibo> recibos = new ArrayList<Recibo>();

        try {
            Connection cnn = source.getConnection();

            PreparedStatement pst = cnn.prepareStatement("SELECT * FROM recibo WHERE legajo = ?");

            pst.setInt(1, legajo);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                int idrecibo = rs.getInt(1);
                int anio = rs.getInt(2);
                int mes = rs.getInt(3);
                double montoAntiguedad = rs.getDouble(5);
                double jubilacion = rs.getDouble(6);
                double obraSocial = rs.getDouble(7);
                double fondoAComplejidad = rs.getDouble(8);
                double sueldoNeto = rs.getDouble(9);

                Recibo r = new Recibo(idrecibo, anio, mes, legajo, montoAntiguedad, jubilacion, obraSocial, fondoAComplejidad, sueldoNeto);

                recibos.add(r);

            }

            rs.close();
            pst.close();
            cnn.close();

        } catch (SQLException ex) {
            throw ex;
        }

        return recibos;
    }

    public List<ReporteDTO> reporteSueldos(int anio, int mes) throws SQLException {

        List<ReporteDTO> reportes = new ArrayList<ReporteDTO>();

        try {
            Connection cnn = source.getConnection();

            PreparedStatement pst = cnn.prepareStatement("select a.nombre, sum(r.sueldoNeto)\n"
                    + "from recibo r\n"
                    + "join empleado e\n"
                    + "on r.legajo = e.legajo\n"
                    + "join area a\n"
                    + "on a.idarea = e.idarea\n"
                    + "where anio = ?\n"
                    + "and mes = ?\n"
                    + "group by a.nombre");

            pst.setInt(1, anio);
            pst.setInt(2, mes);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String area = rs.getString(1);
                double sumaSueldos = rs.getDouble(2);

                ReporteDTO r = new ReporteDTO(area, sumaSueldos);

                reportes.add(r);

            }

            rs.close();
            pst.close();
            cnn.close();

        } catch (SQLException ex) {
            throw ex;
        }

        return reportes;
    }
}
