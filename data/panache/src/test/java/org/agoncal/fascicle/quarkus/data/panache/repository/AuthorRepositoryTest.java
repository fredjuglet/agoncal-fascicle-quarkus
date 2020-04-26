package org.agoncal.fascicle.quarkus.data.panache.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.agoncal.fascicle.quarkus.data.panache.model.Author;
import org.agoncal.fascicle.quarkus.data.panache.model.Language;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("https://github.com/quarkusio/quarkus/issues/7188")
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorRepositoryTest {

  @Inject
  AuthorRepository authorRepository;

  private static final String DEFAULT_FIRST_NAME = "First Name";
  private static final String UPDATED_FIRST_NAME = "First Name (updated)";
  private static final String DEFAULT_LAST_NAME = "Last Name";
  private static final String UPDATED_LAST_NAME = "Last Name (updated)";
  private static final String DEFAULT_BIO = "Bio";
  private static final String UPDATED_BIO = "Bio (updated)";
  private static final LocalDate DEFAULT_DATE_OF_BIRTH = LocalDate.of(1970, Month.FEBRUARY, 1);
  private static final LocalDate UPDATED_DATE_OF_BIRTH = LocalDate.of(1980, Month.APRIL, 2);
  private static final Language DEFAULT_LANGUAGE = Language.ENGLISH;
  private static final Language UPDATED_LANGUAGE = Language.CHINESE;

  private static int nbAuthors;
  private static long authorId;

  @Test
  void shouldNotGetUnknownAuthor() {
    Long randomId = new Random().nextLong();
    Optional<Author> author = authorRepository.findByIdOptional(randomId);
    assertFalse(author.isPresent());
  }

  @Test
  @Order(1)
  void shouldGetInitialAuthors() {
    nbAuthors = authorRepository.findAll().list().size();
    assertTrue(nbAuthors > 0);
  }

  @Test
  @Order(2)
  void shouldAddAnAuthor() {
    // Persists an author
    Author author = new Author();
    author.firstName = DEFAULT_FIRST_NAME;
    author.lastName = DEFAULT_LAST_NAME;
    author.bio = DEFAULT_BIO;
    author.dateOfBirth = DEFAULT_DATE_OF_BIRTH;
    author.preferredLanguage = DEFAULT_LANGUAGE;

    assertFalse(author.isPersistent());
    authorRepository.persist(author);

    // Checks the author has been created
    assertNotNull(authorId);
    assertEquals(DEFAULT_FIRST_NAME, author.firstName);
    assertEquals(DEFAULT_LAST_NAME, author.lastName);
    assertEquals(DEFAULT_BIO, author.bio);
    assertEquals(DEFAULT_DATE_OF_BIRTH, author.dateOfBirth);
    assertEquals(DEFAULT_LANGUAGE, author.preferredLanguage);
    assertTrue(author.age > 45);

    // Checks there is an extra author in the database
    assertEquals(nbAuthors + 1, authorRepository.findAll().list().size());

    authorId = author.id;
  }

  @Test
  @Order(3)
  void shouldFindTheAuthorByName() {
    Author author = authorRepository.findByName(DEFAULT_LAST_NAME).get();

    // Checks the author has been created
    assertNotNull(authorId);
    assertEquals(DEFAULT_FIRST_NAME, author.firstName);
    assertEquals(DEFAULT_LAST_NAME, author.lastName);
    assertEquals(DEFAULT_BIO, author.bio);
    assertEquals(DEFAULT_DATE_OF_BIRTH, author.dateOfBirth);
    assertEquals(DEFAULT_LANGUAGE, author.preferredLanguage);
    assertTrue(author.age > 45);
  }

  @Test
  @Order(4)
  void shouldUpdateAnAuthor() {
    Author author = new Author();
    author.id = authorId;
    author.firstName = UPDATED_FIRST_NAME;
    author.lastName = UPDATED_LAST_NAME;
    author.bio = UPDATED_BIO;
    author.dateOfBirth = UPDATED_DATE_OF_BIRTH;
    author.preferredLanguage = UPDATED_LANGUAGE;

    // Updates the previously created author
    authorRepository.update(author);

    // Checks the author has been updated
    author = authorRepository.findByIdOptional(authorId).get();
    assertTrue(author.isPersistent());
    assertEquals(UPDATED_FIRST_NAME, author.firstName);
    assertEquals(UPDATED_LAST_NAME, author.lastName);
    assertEquals(UPDATED_BIO, author.bio);
    assertEquals(UPDATED_DATE_OF_BIRTH, author.dateOfBirth);
    assertEquals(UPDATED_LANGUAGE, author.preferredLanguage);
    assertTrue(author.age < 45);

    // Checks there is no extra author in the database
    assertEquals(nbAuthors + 1, authorRepository.findAll().list().size());
  }

  @Test
  @Order(5)
  void shouldRemoveAnAuthor() {
    // Deletes the previously created author
    authorRepository.deleteById(authorId);

    // Checks there is less a author in the database
    assertEquals(nbAuthors, authorRepository.findAll().list().size());
  }
}
