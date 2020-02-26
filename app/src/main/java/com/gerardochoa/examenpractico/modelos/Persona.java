package com.gerardochoa.examenpractico.modelos;

import java.util.UUID;

import io.realm.RealmObject;

public class Persona extends RealmObject {

    private String nombre;
    private int edad;
    private String NSS;
    private String sexo;
    private double peso;
    private double altura;
    private String estado;

    public Persona(String nombre, int edad, String NSS, String sexo, double peso, double altura,
                   String estado) {
        this.nombre = nombre;
        this.edad = edad;
        this.NSS = UUID.randomUUID().toString().toUpperCase().substring(0, 8);
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura;
        this.estado = estado;
    }

    public Persona(){}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNSS() {
        return NSS;
    }

    public void setNSS(String NSS) {
        this.NSS = NSS;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
