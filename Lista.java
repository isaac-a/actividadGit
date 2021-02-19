package com.cip.prog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import javax.swing.*;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;


public class Lista {
	
public ArrayList<Persona> personas;


public void cargar() throws CsvValidationException  {
	
	String csvFile = "/Users/arias/eclipse-workspace/actividad_csv/src/com/cip/prog/personas.csv";  
	BufferedReader reader = null;
	String[] nextPersona;
	
	try {
		reader = Files.newBufferedReader(Paths.get(csvFile));
		CSVReader csvReader = new CSVReader(reader);
		
		while ((nextPersona = csvReader.readNext()) != null) {
			
			Persona persona = new Persona(nextPersona);
			
			this.personas.add(persona);
			
			
		}
	
	csvReader.close();
		
	} 
	catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	catch (IOException e) {
	e.printStackTrace();
	}	
	
	JOptionPane.showMessageDialog(null, "Se han cargado los Elementos");

}
	
	
	public void listar() {
		
		if(personas.isEmpty())  {
			JOptionPane.showMessageDialog(null, "La lista esta vacia");
		}
		else {
			JOptionPane.showMessageDialog(null, personas.toArray());
		}
    }
	
	
	public void insertar() {
		
		String nombre = JOptionPane.showInputDialog("Introduce Nombre");
		String apellidos = JOptionPane.showInputDialog("Introduce Apellidos");
		int dni = Integer.parseInt(JOptionPane.showInputDialog("Introduce dni"));
		int edad = Integer.parseInt(JOptionPane.showInputDialog("Introduce edad"));
		String calle = JOptionPane.showInputDialog("Introduce calle");
		int num = Integer.parseInt(JOptionPane.showInputDialog("Introduce numero de la casa"));
		int cp = Integer.parseInt(JOptionPane.showInputDialog("Introduce codigo postal"));
		String prov = JOptionPane.showInputDialog("Introduce Provincia");
		
		personas.add(new Persona(nombre, apellidos, dni, edad, calle, num, cp, prov));
	}
	
	
	public void eliminar() {
		
		String nombres[] = new String[personas.size()];
		
		for(int i = 0; i < personas.size(); i++) {
			
			nombres[i] = personas.get(i).getNombre();
			
		}
		
		int eliminar = JOptionPane.showOptionDialog(null, 
				"Seleccione el Elemento que desea eliminar", 
				"Eliminar", 0, JOptionPane.QUESTION_MESSAGE, 
				null, nombres, null);
		
		personas.remove(eliminar);
		
	}
	
	public void contar() {
		
		JOptionPane.showMessageDialog(null, "La lista tiene " + personas.size() + " elementos");
		
	}
	
	public Persona buscar(int dni) {
		for(Persona p: personas) {
			if(p.getDni() == dni) {
				JOptionPane.showMessageDialog(null, "La persona que buscas es " + p.toString());
				return p;
			}
		}
		
		JOptionPane.showMessageDialog(null, "No se encontraron coincidencias");
		return null;
		
	}
	
	public ArrayList<Persona> buscar(String nombre) {
		
		ArrayList<Persona> coincidencias= new ArrayList<Persona>(); 
		
		for(Persona p: personas) {
			if (p.getNombre().equalsIgnoreCase(nombre)) {
				coincidencias.add(p);
			}
		}
		
		if(coincidencias.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No se encontraron coincidencias");
			return null;
		}
		
		return coincidencias;
		
	}
	
	public void ordenar() {
		
		String[] botones = {"Apellidos", "DNI"};
		int ventana = JOptionPane.showOptionDialog(null, 
				"Escoja el atributo por el cual desea ordenar la tabla", 
				"Ordenar", 0, JOptionPane.QUESTION_MESSAGE,
				null, botones, null);

		switch(ventana) {
		
		case 0: Collections.sort(personas, new OrdenarPorApellidos()); break;
		case 1: Collections.sort(personas, new OrdenarDni()); break;
		}
		
	}
	
	
	public void guardar()  {
		
		String csvFile = "/Users/arias/eclipse-workspace/actividad_csv/src/com/cip/prog/copia_guardar.csv";
		
		try (var fos = new FileOutputStream(csvFile); 
	         var osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
	         var writer = new CSVWriter(osw)){			
			
			for(Persona p: personas) {
				
			String[] personaToArray = {p.aCsv()};
				
			writer.writeNext(personaToArray);
				
			}
			
			
			
			}
		
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido escribir en el fichero");
		}
		
		JOptionPane.showMessageDialog(null, "Se han guardado los cambios en el fichero");
		
	}


	public ArrayList<Persona> getPersonas() {
		return personas;
	}


	public void setPersonas(ArrayList<Persona> personas) {
		this.personas = personas;
	}


	@Override
	public String toString() {
		return "Lista [personas=" + personas + "]";
	}
	
		
	
}
