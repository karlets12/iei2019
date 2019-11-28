package application;

import java.util.List;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Predicate;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ControllerInicio {
	@FXML
	private ComboBox<String> comboBox;
	@FXML
	private TextField input;
	@FXML
	private CheckBox amazonCheck;
	@FXML
	private CheckBox fnacCheck;
	@FXML
	private CheckBox pcComponentsCheck;
	@FXML
	private Button buttonBuscar;

	protected Stage stage;

	public String marca = "";
	public String modelo = "";
	public String telf = "teléfono móvil";
	public Double precio;
	public ArrayList<Smartphone> smartphones = new ArrayList<Smartphone>();
	public double precioActAmazon;
	public double precioAntAmazon;
	public double precioActPcComponentes;
	public double precioAntPcComponentes;
	public double precioFNAC;

	public void initialize() {
		stage = new Stage(StageStyle.DECORATED);
		stage.setTitle("BUSCADOR");
		cargarComboMarcas();
	}

	private void cargarComboMarcas() {
		ArrayList<String> marcas = new ArrayList<String>();
		marcas.add("Samsung");
		marcas.add("LG");
		marcas.add("Sony");
		marcas.add("Huawei");
		marcas.add("Motorola");
		marcas.add("Apple");
		marcas.add("One Plus");
		marcas.add("Lenovo");
		marcas.add("Xiaomi");
		ObservableList<String> obsA = FXCollections.observableArrayList(marcas);
		comboBox.setItems(obsA);
	}

	@FXML
	private void botonBuscar(ActionEvent event) throws InterruptedException {
		marca = comboBox.getValue();
		modelo = input.getText();
		compruebaCheckBox(marca, modelo);

	}


	 public void compruebaCheckBox(String marca, String modelo) throws InterruptedException {
		 int contAmazon=1;
		 if (amazonCheck.isSelected()) {
				String exePath = "C:\\firefox\\geckodriver.exe";
				System.setProperty("webdriver.gecko.driver", exePath);
				WebDriver driver = new FirefoxDriver();
				driver.get("https://www.amazon.es/");
				// BUSCADOR
				WebElement buscadorAmazon = driver.findElement(By.id("twotabsearchtextbox"));
				// CADENA DE BUSQUEDA
				String busqueda = marca + " " + modelo;
				buscadorAmazon.sendKeys(busqueda);
				buscadorAmazon.submit();
				// ESPERA
				WebDriverWait waiting = new WebDriverWait(driver, 10);
				waiting.until(ExpectedConditions.presenceOfElementLocated(By.className("a-last")));
				// TITULO DE RESPUESTA
				System.out.println("Titulo de la pagina " + driver.getTitle());

				//ELEMENTOS
				List<WebElement> listaElementos = driver
						.findElements(By.xpath("//*[contains(@class, 's-result-item')]"));

				System.out.println("Numero de elementos de la lista: " + listaElementos.size());
				String nombre="";
				String precio="";
				String precioAux="";
				String vendedor= "Amazon";
				int j = 1;
				for (int i = 0; i < listaElementos.size(); i++) {
					try {
						nombre = listaElementos.get(i).findElement(By.cssSelector("span.a-text-normal")).getText();
						precio = listaElementos.get(i).findElement(By.cssSelector("span[class='a-price']")).getText();
						precioActAmazon= Double.parseDouble(precio.replace("€", "").replace(",","."));
						try {
							nombre = listaElementos.get(i).findElement(By.cssSelector("span.a-text-normal")).getText();
							precio = listaElementos.get(i).findElement(By.cssSelector("span[class='a-price a-text-price']")).getText();
						}catch(Exception e) {
							nombre = listaElementos.get(i).findElement(By.cssSelector("span.a-text-normal")).getText();
							precioAux=precio;
							precioAntAmazon= Double.parseDouble(precioAux.replace("€", "").replace(",","."));
						}
					}catch(Exception e) {
						precio=listaElementos.get(i).findElement(By.cssSelector("span[class='a-color-base']")).getText();
						precioAux=precio;
						precioAntAmazon= Double.parseDouble(precioAux.replace("€", "").replace(",","."));
					}
						if(nombre.trim().toLowerCase().contains(modelo.toLowerCase()) && nombre.trim().toLowerCase().contains(marca.toLowerCase())) {
							if(precioAntAmazon > 50 && precioActAmazon>50) {
								Smartphone smart = new Smartphone(nombre, precio, vendedor);
								smartphones.add(smart);
								//System.out.println(contAmazon + ": " + "Nombre: " + nombre + ", Precio: " + precio);
								//contAmazon++;
							}
						}
						j++;
				}

			}
		int contFNAC=1;
		if (fnacCheck.isSelected()) {
			String exePath = "C:\\firefox\\geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", exePath);
			WebDriver driver = new FirefoxDriver();
			driver.get("https://www.fnac.es/");
			//CERRAR COOKIES
			WebElement ventanaCookies =	driver.findElement(By.xpath("/html/body/aside/div/button"));
			if (ventanaCookies != null)	{System.out.println("Detectado caja de cookies");
			ventanaCookies.click();}
			//BUSCADOR
			WebElement buscadorFnac = driver.findElement(By.id("Fnac_Search"));
			//CADENA DE BUSQUEDA
			String busqueda = "smartphone "  +  marca + " " + modelo;
			buscadorFnac.sendKeys(busqueda);
			buscadorFnac.submit();
			//ESPERA
			WebDriverWait waiting;
			waiting = new WebDriverWait(driver, 10);
			waiting.until( ExpectedConditions.presenceOfElementLocated(
		    By.xpath("/html/body/div[3]/div/div[16]/footer/div[2]/div[1]/div[4]/div/ul[2]")));
			//TITULO PAGINA
			System.out.println("Titulo de la pagina " + driver.getTitle());
			//BUSCANDO ELEMENTOS
			List<WebElement> listaElementos = driver.findElements(By.xpath("//*[contains(@class, 'Article-itemGroup')]"));
			System.out.println("Numero de elementos de la lista: " + listaElementos.size());
			//DATOS
			String nombre = "";
			String precio = "";
			String vendedor = "FNAC";
			int j = 1;
			Thread.sleep(1000);
			for (int i = 0; i < listaElementos.size(); i++) {
				try {
				precio = listaElementos.get(i).findElement(By.xpath(
						"/html/body/div[3]/div/div[7]/div/div[" + j + "]/article/div[3]/div/div/div/div/div[1]/a/strong"))
						.getText();
				nombre= listaElementos.get(i).findElement(By.xpath("/html/body/div[3]/div/div[7]/div/div[" + j + "]/article/div[2]/div/p[1]/a")).getText();
				precioFNAC= Double.parseDouble(precio.replace("€", "").replace(",","."));
				}catch(Exception e) {
					
					precio = listaElementos.get(i).findElement(By.xpath(
							"/html/body/div[3]/div/div[7]/div/div[" + j + "]/article/div[3]/div/div/div/div[1]/a/strong"))
							.getText();
					nombre= listaElementos.get(i).findElement(By.xpath("/html/body/div[3]/div/div[7]/div/div[" + j + "]/article/div[2]/div/p[1]/a")).getText();
					precioFNAC= Double.parseDouble(precio.replace("€", "").replace(",","."));
					
				}
				
				if(nombre.trim().toLowerCase().contains(modelo.toLowerCase()) && nombre.trim().toLowerCase().contains(marca.toLowerCase())) {
					if(precioFNAC > 50) {
						Smartphone smart = new Smartphone(nombre, precio, vendedor);
						smartphones.add(smart);
						//System.out.println(contFNAC + ": " + "Nombre: " + nombre + ", Precio: " + precio);
						//contFNAC++;
					}
				}
				
				j++;
			}

		}
		int contPc=1;
		if (pcComponentsCheck.isSelected()) {
			String exePath = "C:\\firefox\\geckodriver.exe";
			System.setProperty("webdriver.gecko.driver", exePath);
			WebDriver driver = new FirefoxDriver();
			driver.get("https://www.pccomponentes.com/");
			// BUSCADOR
			WebElement buscadorGoogle = driver.findElement(By.name("query"));
			// CADENA
			String busqueda = "móvil " + marca + " " + modelo + " libre";
			buscadorGoogle.sendKeys(busqueda);
			buscadorGoogle.submit();
			// CONDICION ESPERA
			WebDriverWait waiting = new WebDriverWait(driver, 4000);
			waiting.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(@class, 'ais-Hits')]")));

			waitForPageLoaded(driver);

			// DATOS
			Thread.sleep(4000);
			List<WebElement> listaElementos = driver.findElements(By.xpath("//*[contains(@class, 'ais-Hits')]"));

			System.out.println("Número de elementos de la lista: " + listaElementos.size());
			// ARTICULOS
			String precio = "";

			int k;
			if (listaElementos.size() > 30) {
				k = 30;
			} else {
				k = listaElementos.size();
			}

			int j = 1;
			String nombre = "";
			String vendedor = "Pc Componentes";
			for (int i = 0; i < k; i++) {
				precio = listaElementos.get(i).findElement(By.xpath(
						"/html/body/header/div[3]/div[2]/section/div[2]/div[2]/ol/li[" + j + "]/div/div/div[3]"))
						.getText();
				precioActPcComponentes = Double.parseDouble(precio.replace("€", "").replace(",", "."));
				nombre = listaElementos.get(i).findElement(By.xpath("/html/body/header/div[3]/div[2]/section/div[2]/div[2]/ol/li[" + j + "]/div/div/div[1]")).getText();
				
				
				if (nombre.trim().toLowerCase().contains(modelo.toLowerCase())
						&& nombre.trim().toLowerCase().contains(marca.toLowerCase())) {
					if (precioActPcComponentes > 50.0) {
						Smartphone smart = new Smartphone(nombre, precio, vendedor);
						smartphones.add(smart);
						System.out.println(contPc + ": " + "Nombre: " + nombre + ", Precio: " + precio);
						contPc++;
					}
				}
				j++;
			}

		}
	}

	public static void waitForPageLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("returndocument.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			//System.out.println("Timeout waiting for Page Load Request to complete.");
		}
	}
}
