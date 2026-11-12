/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author sergio martinez y camilo londoño
 */
public class Usuario implements Comparable<Usuario>{
        private int idCedula; 
        private String nombre;  
        private String apellido;
        private double telefono; 
        private String correo; 
        private String direccion;
        private int roll;
        private String contrasena;
        private Finca finca;

        public Usuario() {
        }

        public Usuario(int idCedula, String nombre, String apellido, double telefono, String correo, String direccion, int roll, String contrasena) {

            this.idCedula = idCedula;
            this.nombre = nombre;
            this.apellido = apellido;
            this.telefono = telefono;
            this.correo = correo;
            this.direccion = direccion;
            this.roll = roll;
            this.contrasena = contrasena;
        }

        public Usuario(int idCedula, String nombre, String apellido, double telefono, String correo, String direccion, int roll, String contrasena, Finca finca) {
            this.idCedula = idCedula;
            this.nombre = nombre;
            this.apellido = apellido;
            this.telefono = telefono;
            this.correo = correo;
            this.direccion = direccion;
            this.roll = roll;
            this.contrasena = contrasena;
            this.finca = finca;
        }

        public int getIdCedula() {
            return idCedula;
         }

        public void setIdCedula(int idCedula) {
            this.idCedula = idCedula;
         }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public double getTelefono() {
            return telefono;
        }

        public void setTelefono(double telefono) {
            this.telefono = telefono;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public String getDireccion() {
            return direccion;
        }

        public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        public int getRoll() {
            return roll;
        }

        public void setRoll(int roll) {
            this.roll = roll;
        }

        public String getContrasena() {
            return contrasena;
        }

        public void setContrasena(String contrasena) {
            this.contrasena = contrasena;
        }

        public Finca getFinca() {
            return finca;
        }

        public void setFinca(Finca finca) {
            this.finca = finca;
        }

        //La estructura

        @Override
        public String toString() {
            return "Usuario{" + "idCedula=" + idCedula + ", nombre=" + nombre + ", apellido=" + apellido + ", telefono=" + telefono + ", correo=" + correo + ", direccion=" + direccion + ", roll=" + roll + ", contrasena=" + contrasena + ", finca=" + finca + '}';
        }


        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.idCedula;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Usuario other = (Usuario) obj;
            return this.idCedula == other.idCedula;
        }    
         @Override
        public int compareTo(Usuario otroUsuario) {
            return Integer.compare(this.idCedula, otroUsuario.idCedula);
        }          
        
        
    }
