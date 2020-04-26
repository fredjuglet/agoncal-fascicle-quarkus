package org.agoncal.fascicle.quarkus.data.panache.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Optional;

/**
 * @author Antonio Goncalves
 * http://www.antoniogoncalves.org
 * --
 */
// tag::adocSnippet[]
@Entity
public class Publisher extends PanacheEntity {

  @Column(length = 30)
  public String name;

  public static Optional<Publisher> findByName(String name) {
    Publisher publisher = find("name", name).firstResult();
    return publisher != null ? Optional.of(publisher) : Optional.empty();
  }

  public static long deleteByName(String name) {
    return delete("name", name);
  }
}
// end::adocSnippet[]
