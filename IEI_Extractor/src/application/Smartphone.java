package application;

import javafx.beans.property.SimpleStringProperty;

public class Smartphone {
 
 public SimpleStringProperty nombre;
 public SimpleStringProperty precio;
 public SimpleStringProperty vendedor;
 
 
 public Smartphone(String nombre, String precio, String vendedor)
 {
     this.nombre = new SimpleStringProperty(nombre);
     this.precio = new SimpleStringProperty(precio);
     this.vendedor=new SimpleStringProperty(vendedor);
 }


public String getNombre() {
	return nombre.get();
}


public void setNombre(String nombre) {
	this.nombre = new SimpleStringProperty(nombre);
}


public String getPrecio() {
	return precio.get();
}


public void setPrecio(String precio) {
	this.precio = new SimpleStringProperty(precio);
}


public String getVendedor() {
	return vendedor.get();
}


public void setVendedor(String vendedor) {
	this.vendedor = new SimpleStringProperty(vendedor);
}


}
