package application;

public class Smartphone {
 
 public String nombre;
 public String precioActual;
 public String precioAnterior;
 public String vendedor;
 
 
 public Smartphone(String nombre, String precioActual,String precioAnterior, String vendedor)
 {
     this.nombre = nombre;
     this.precioActual = precioActual;
     this.precioAnterior = precioAnterior;
     this.vendedor=vendedor;
 }


public String getNombre() {
	return nombre;
}


public void setNombre(String nombre) {
	this.nombre = nombre;
}


public String getPrecioActual() {
	return precioActual;
}


public void setPrecioActual(String precioActual) {
	this.precioActual = precioActual;
}


public String getPrecioAnterior() {
	return precioAnterior;
}


public void setPrecioAnterior(String precioAnterior) {
	this.precioAnterior = precioAnterior;
}


public String getVendedor() {
	return vendedor;
}


public void setVendedor(String vendedor) {
	this.vendedor = vendedor;
}
}
