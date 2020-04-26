package org.agoncal.fascicle.quarkus.data.jpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
// tag::adocSnippet[]
@Entity
public class Publisher {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(length = 30)
  private String name;

//  public static Publisher findByName(String name) {
//    Publisher publisher = find("name", name).firstResult();
//    return publisher;
//  }
//
//  public static void deleteAPress() {
//    delete("name", "APress");
//  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
// end::adocSnippet[]
