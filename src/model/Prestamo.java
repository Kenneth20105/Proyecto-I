package model;

import java.time.LocalDate;

public class Prestamo {
    private int id;
    private int idDocumento;
    private int idUsuario;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean devuelto;

    public Prestamo(int id, int idDocumento, int idUsuario, LocalDate fechaPrestamo, boolean devuelto, LocalDate fechaDevolucion) {
        this.id = id;
        this.idDocumento = idDocumento;
        this.idUsuario = idUsuario;
        this.fechaPrestamo = fechaPrestamo;
        this.devuelto = devuelto;
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getId() {
        return id;
    }

    public int getIdDocumento() {
        return idDocumento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucion() {
        return fechaDevolucion;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdDocumento(int idDocumento) {
        this.idDocumento = idDocumento;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public void setFechaDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Prestamo{" +
                "id=" + id +
                ", idDocumento=" + idDocumento +
                ", idUsuario=" + idUsuario +
                ", fechaPrestamo=" + fechaPrestamo +
                ", fechaDevolucion=" + fechaDevolucion +
                ", devuelto=" + devuelto +
                '}';
    }
}
