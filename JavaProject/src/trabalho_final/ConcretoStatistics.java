/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import estg.ipp.pt.tp02_conferencesystem.io.interfaces.Statistics;



/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class ConcretoStatistics implements Statistics{

    /**
     * @param description pequena descrição dos resultados 
     */
    private String description;
    
    /**
     * @param valor valor dos resultados
     */
    private double value;


    
    /**
     * Método construtor da classe ConcretoStatistics
     * @param description  pequena descrição dos resultados 
     * @param value valor dos resultados
     */
    public ConcretoStatistics(String description, double value) {
        this.description = description;
        this.value = value;
    }
    
    
    /**
     * Getter para a descrição dos resultados estatísticos
     * @return a descrição dos resultados estatisticos 
     */
    @Override
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Getter para o valor dos dados estatísticos
     * @return o valor dos dados estatisticos
     */
    @Override
    public double getValue() {
        return this.value;
    }
    
}
