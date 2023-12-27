/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trabalho_final;

import Enumerations.ParticipantType;


/**
 * Nome: Bruno Ferreira Rodrigues
 * Número: 8210455
 * Turma: LEI1T3
 * 
 * Nome: Ângelo Patrício Teixeira Mendes
 * Número: 8180655
 * Turma: LEI2T3
 */
public class Student extends ConcretoParticipant{
    
    /**
     * curso que Studend frequenta
     */
    private String curso;
    
    /**
     * ano letivo que Studend frequenta no momento
     */
    private int anoLetivo;
    
    
    /**
     * Método construtor da classe Student
     * @param curso Curso frequenta
     * @param anoLetivo ano letivo que frequenta no momento
     * @param id id do aluno
     * @param name nome do aluno
     * @param bio biografia do aluno
     * @param tipoParticipante Tipo do participante
     */
    public Student(String curso, int anoLetivo, int id, String name, String bio, ParticipantType tipoParticipante) {
        super(id, name, bio, tipoParticipante);
        this.curso = curso;
        this.anoLetivo = anoLetivo;
    }
    

    /**
     * Getter para o curso do aluno
     * @return o curso do aluno
     */
    public String getCurso() {
        return curso;
    }
    
    /**
     * Setter para o curso do estudante
     * @param curso novo curso do estudante
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Getter para o ano letivo do estudante
     * @return o ano letivo do estudante
     */
    public int getAnoLetivo() {
        return anoLetivo;
    }

    /**
     * Setter para o ano letivo do estudante
     * @param anoLetivo novo ano letivo do estudante
     */
    public void setAnoLetivo(int anoLetivo) {
        this.anoLetivo = anoLetivo;
    }
    
    
    
    

    
}
