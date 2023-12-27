/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import estg.ipp.pt.tp02_conferencesystem.interfaces.Room;


/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class ConcretoRoom implements Room{
 
    /** 
     * id únido da sala
     */
    private int id;
    
    /**
     * nome da sala
     */
    private String name;
    
    /**
     * número de lugares da sala
     */
    private int numberOfSeats;
    
    
     /**
     * Método construtor da classe ConcretoRoom
     * @param id id únido da sala
     * @param name nome da sala
     * @param numberOfSeats número de lugares da sala
     */
    public ConcretoRoom(int id, String name, int numberOfSeats) {
        this.id = id;
        this.name = name;
        this.numberOfSeats = numberOfSeats;
    }
    
    
    /**
     * Getter para o id da sala
     * @return id da sala 
     */
    @Override
    public int getId() {
        return this.id;
    }
    
    /**
     * Getter para o nome da sala
     * @return nome da sala 
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Getter para o número de lugares da sala
     * @return Numero de lugares na sala 
     */
    @Override
    public int getNumberOfSeats() {
        return this.numberOfSeats;
    }
    
}
