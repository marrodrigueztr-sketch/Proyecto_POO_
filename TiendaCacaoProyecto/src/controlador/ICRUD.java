
package controlador;


/*
sergio martinez y camilo londoño
     * NOTA SOBRE EL USO DE <T> :
     * Se utiliza <T> en lugar de 'Object' por tres razones principales:
     * 1. Reutilización: Permite que una sola interfaz defina el contrato para cualquier clase (Usuario, Finca, etc.).
     * 2. Seguridad de Tipos: Al indicarle a Java el tipo exacto de dato desde el principio, el compilador nos 
     * avisa de errores antes de ejecutar el programa.
     * 3. Evita el "Casting": Nos ahorra tener que forzar la traducción de objetos (ej. evitar hacer '(Usuario) obj'), 
     * logrando un código más limpio, moderno y menos propenso a fallos ("ClassCastException").
     */
public interface ICRUD<T> {
    
    public boolean insertar(T obj); // Create
    public T consultarUno(T obj);   // Read Buscar por ID
    public void consultarTodos();   // Read (Imprimir todos)
    public boolean modificar(T obj);// Update
    public boolean borrar(T obj);   // Delete
    public void ordenar();          // sort de la api de java
    public void ordenarBurbuja();   // Sort 
    public void ordenarQuicksort(); // Sort 
    public void ordenarMersort();   // Sort 
}
    

