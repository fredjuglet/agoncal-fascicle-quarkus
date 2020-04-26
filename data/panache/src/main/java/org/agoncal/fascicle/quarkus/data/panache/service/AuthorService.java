package org.agoncal.fascicle.quarkus.data.panache.service;

import io.quarkus.hibernate.orm.panache.Panache;
import org.agoncal.fascicle.quarkus.data.panache.model.Author;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(SUPPORTS)
public class AuthorService {

  @Transactional(REQUIRED)
  public Author persist(Author author) {
    Author.persist(author);
    return author;
  }

  public List<Author> findAll() {
    return Author.listAll();
  }

  public Optional<Author> findByIdOptional(Long id) {
    return Author.findByIdOptional(id);
  }

  @Transactional(REQUIRED)
  public Author update(Author author) {
    return Panache.getEntityManager().merge(author);
  }

  @Transactional(REQUIRED)
  public void deleteById(Long id) {
    Author.deleteById(id);
  }

  public Optional<Author> findByName(String name) {
    Author author = Panache.getEntityManager().createQuery("SELECT a FROM Author a WHERE a.lastName = :name", Author.class)
      .setParameter("name", name)
      .getSingleResult();
    return author != null ? Optional.of(author) : Optional.empty();
  }
}
