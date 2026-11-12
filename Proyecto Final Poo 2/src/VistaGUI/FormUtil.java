package VistaGUI;

import CONTROLADOR.ControladorMovimiento;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 * Métodos de apoyo compartidos por todos los formularios: mensajes, tablas,
 * combos, bloqueo de teclas inválidas y validación de campos.
 * <p>
 * Los métodos de validación lanzan {@link IllegalArgumentException} con un mensaje
 * claro; cada formulario la atrapa y la muestra con {@link #aviso}.
 *
 * @author Grupo 1
 */
public final class FormUtil {

    private FormUtil() {
    }

    // ------------------------------------------------------------------ mensajes
    public static void aviso(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Validación", JOptionPane.WARNING_MESSAGE);
    }

    public static void info(Component padre, String mensaje) {
        JOptionPane.showMessageDialog(padre, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirmar(Component padre, String mensaje) {
        return JOptionPane.showConfirmDialog(padre, mensaje, "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }

    // ------------------------------------------------------------------ tablas
    /**
     * Configura una tabla de solo lectura con las columnas indicadas.
     *
     * @return el modelo para agregar filas
     */
    public static DefaultTableModel configurarTabla(JTable tabla, String... columnas) {
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla.setModel(modelo);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return modelo;
    }

    // ------------------------------------------------------------------ combos
    /** Texto de un ítem de combo con el formato "id - descripción". */
    public static String item(int id, String descripcion) {
        return id + " - " + descripcion;
    }

    /** @return el id del ítem seleccionado ("12 - ..."), o 0 si es "Todos", "0 - ..." o no hay selección */
    public static int idSeleccionado(JComboBox<String> combo) {
        Object sel = combo.getSelectedItem();
        if (sel == null) return 0;
        String texto = sel.toString();
        int guion = texto.indexOf(" - ");
        if (guion <= 0) return 0;
        try {
            return Integer.parseInt(texto.substring(0, guion).trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /** Selecciona en el combo el ítem cuyo id coincide (si no existe, selecciona el primero). */
    public static void seleccionarPorId(JComboBox<String> combo, int id) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).startsWith(id + " - ")) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        if (combo.getItemCount() > 0) combo.setSelectedIndex(0);
    }

    // ------------------------------------------------------------------ bloqueo de teclas
    private static void bloquear(KeyEvent evt) {
        evt.consume();
        Toolkit.getDefaultToolkit().beep();
    }

    /** Solo permite dígitos. */
    public static void soloEnteros(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && !Character.isISOControl(c)) bloquear(evt);
    }

    /** Solo permite dígitos y un único punto decimal. */
    public static void soloDecimal(KeyEvent evt, JTextField campo) {
        char c = evt.getKeyChar();
        boolean puntoValido = c == '.' && !campo.getText().contains(".");
        if (!Character.isDigit(c) && !puntoValido && !Character.isISOControl(c)) bloquear(evt);
    }

    /** Solo permite letras y espacios. */
    public static void soloLetras(KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isLetter(c) && c != ' ' && !Character.isISOControl(c)) bloquear(evt);
    }

    /** Solo permite dígitos y '/', con un máximo de 10 caracteres (DD/MM/AAAA). */
    public static void soloFecha(KeyEvent evt, JTextField campo) {
        char c = evt.getKeyChar();
        if (Character.isISOControl(c)) return;
        if ((!Character.isDigit(c) && c != '/') || campo.getText().length() >= 10) bloquear(evt);
    }

    // ------------------------------------------------------------------ validaciones
    private static IllegalArgumentException error(JTextField campo, String mensaje) {
        campo.requestFocus();
        return new IllegalArgumentException(mensaje);
    }

    /** Texto obligatorio con longitud mínima. */
    public static String texto(JTextField campo, String nombre, int minimo) {
        String t = campo.getText().trim();
        if (t.isEmpty()) throw error(campo, "El campo '" + nombre + "' es obligatorio.");
        if (t.length() < minimo) throw error(campo, "El campo '" + nombre + "' debe tener al menos " + minimo + " caracteres.");
        return t;
    }

    /** Nombre: mínimo 3 caracteres, solo letras y espacios. */
    public static String nombre(JTextField campo, String nombre) {
        String t = texto(campo, nombre, 3);
        for (char c : t.toCharArray()) {
            if (!Character.isLetter(c) && c != ' ') {
                throw error(campo, "El campo '" + nombre + "' solo admite letras y espacios.");
            }
        }
        return t;
    }

    /** Entero obligatorio mayor o igual a {@code minimo}. */
    public static int entero(JTextField campo, String nombre, int minimo) {
        String t = texto(campo, nombre, 1);
        int n;
        try {
            n = Integer.parseInt(t);
        } catch (NumberFormatException e) {
            throw error(campo, "El campo '" + nombre + "' debe ser un número entero.");
        }
        if (n < minimo) throw error(campo, "El campo '" + nombre + "' debe ser mayor o igual a " + minimo + ".");
        return n;
    }

    /** Decimal obligatorio mayor o igual a {@code minimo}. */
    public static float decimal(JTextField campo, String nombre, float minimo) {
        String t = texto(campo, nombre, 1);
        float n;
        try {
            n = Float.parseFloat(t);
        } catch (NumberFormatException e) {
            throw error(campo, "El campo '" + nombre + "' debe ser numérico (use punto para decimales).");
        }
        if (n < minimo) throw error(campo, "El campo '" + nombre + "' no puede ser menor que " + minimo + ".");
        return n;
    }

    /** Correo con estructura básica usuario@dominio.ext. */
    public static String correo(JTextField campo) {
        String t = texto(campo, "Correo", 1);
        if (!t.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)*\\.[A-Za-z]{2,}$")) {
            throw error(campo, "El correo no tiene un formato válido (ej. usuario@empresa.com).");
        }
        return t;
    }

    /** Fecha obligatoria con formato DD/MM/AAAA y que exista en el calendario. */
    public static LocalDate fechaLocal(JTextField campo, String nombre) {
        String t = texto(campo, nombre, 1);
        LocalDate f = ControladorMovimiento.convertirFecha(t);
        if (f == null) throw error(campo, "El campo '" + nombre + "' debe ser una fecha válida con formato DD/MM/AAAA.");
        return f;
    }

    /** Igual que {@link #fechaLocal} pero devuelve el texto validado. */
    public static String fecha(JTextField campo, String nombre) {
        return fechaLocal(campo, nombre).format(ControladorMovimiento.FORMATO_FECHA);
    }
}
