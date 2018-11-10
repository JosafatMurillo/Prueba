/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.inbo.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author BODEGA
 */
@Entity
@Table(name = "answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Answer.findAll", query = "SELECT a FROM Answer a")
    , @NamedQuery(name = "Answer.findByIdAnswer", query = "SELECT a FROM Answer a WHERE a.idAnswer = :idAnswer")
    , @NamedQuery(name = "Answer.findByRespuesta", query = "SELECT a FROM Answer a WHERE a.respuesta = :respuesta")
    , @NamedQuery(name = "Answer.findByImagen", query = "SELECT a FROM Answer a WHERE a.imagen = :imagen")
    , @NamedQuery(name = "Answer.findByEsCorrecta", query = "SELECT a FROM Answer a WHERE a.esCorrecta = :esCorrecta")})
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idAnswer")
    private Integer idAnswer;
    @Column(name = "respuesta")
    private String respuesta;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "esCorrecta")
    private Boolean esCorrecta;
    @JoinColumn(name = "idQuestion", referencedColumnName = "idQuestion")
    @ManyToOne(optional = false)
    private Question idQuestion;

    public Answer() {
    }

    public Answer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    public Integer getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(Integer idAnswer) {
        this.idAnswer = idAnswer;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Boolean getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(Boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public Question getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(Question idQuestion) {
        this.idQuestion = idQuestion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnswer != null ? idAnswer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answer)) {
            return false;
        }
        Answer other = (Answer) object;
        if ((this.idAnswer == null && other.idAnswer != null) || (this.idAnswer != null && !this.idAnswer.equals(other.idAnswer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.inbo.entities.Answer[ idAnswer=" + idAnswer + " ]";
    }
    
}
