package model;

import java.sql.Date;

public class Prestamo {
    private int id;
    private int idDocumento;
    private int idUsuario;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    private boolean devuelto;

    public Prestamo(int id, int idDocumento, int idUsuario, Date fechaPrestamo, Date fechaDevolucion, boolean devuelto) {
        this.id = id;
        this.idDocumento = idDocumento;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    public int getId() { return id; }
    public int getIdDocumento() { return idDocumento; }
    public int getIdUsuario() { return idUsuario; }
    public Date getFechaPrestamo() { return fechaPrestamo; }
    public Date getFechaDevolucion() { return fechaDevolucion; }
    public boolean isDevuelto() { return devuelto; }

    public void setId(int id) { this.id = id; }
    public void setIdDocumento(int idDocumento) { this.idDocumento = idDocumento; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public void setFechaPrestamo(Date fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public void setFechaDevolucion(Date fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
    public void setDevuelto(boolean devuelto) { this.devuelto = devuelto; }

    @Override
    public String toString() {
        return "Prestamo de documento " + idDocumento + " al usuario " + idUsuario + ", hasta " + fechaDevolucion + (devuelto ? " (devuelto)" : "");
    }
}
