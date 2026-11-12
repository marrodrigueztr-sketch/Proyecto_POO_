/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package CONTROLADOR;

/**
 *
 * @author Usuario
 * Todos sus metodos son abstractos
 */

public interface ICRUD {
    // C - Create: Inserta un registro en la lista. Retorna true si se logra.
    public boolean insertar(Object obj); 
    
    // R - Read: Imprime todos los registros de la lista dinámica en consola.
    public void consultarTodos();          
    
    // R - Read: Busca un elemento específico. Retorna el objeto si existe, o null.
    public Object consultarUno(Object obj);  
    
    // R - Read: Ordena la lista de forma natural (ascendente o criterio por defecto).
    public void ordenar();                 
    
    // R - Read: Ordena la lista con un criterio secundario (Burbuja). 1 = Descendente.
    public void ordenar(int descendente);  
    
    // U - Update: Modifica los datos de un registro ya existente en la lista.
    public Object update(Object obj);        
    
    // D - Delete: Elimina un registro y rompe sus dependencias. Retorna true si se logra.
    public boolean delete(Object obj);
}
