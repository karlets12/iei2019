package application;

public class Smartphone {
 
 public String nombre;
 public String precio;
 public String vendedor;
 
 
 public Smartphone(String nombre, String precio, String vendedor)
 {
     this.nombre = nombre;
     this.precio = precio;
     this.vendedor=vendedor;
 }


public String getNombre() {
	return nombre;
}


public void setNombre(String nombre) {
	this.nombre = nombre;
}


public String getPrecio() {
	return precio;
}


public void setPrecio(String precio) {
	this.precio = precio;
}


public String getVendedor() {
	return vendedor;
}


public void setVendedor(String vendedor) {
	this.vendedor = vendedor;
}


}
