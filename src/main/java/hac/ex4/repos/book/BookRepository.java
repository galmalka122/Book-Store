package hac.ex4.repos.book;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * finds books by substrings
     * @param name - the substring
     * @param pageable - the pageable
     * @return - a page entity with all founded books
     */
    @Query("SELECT u FROM Book u WHERE u.name like %:name%")
    Page<Book> findBookByName(
            @Param("name") String name, Pageable pageable);

    /**
     * @return - 5 books with the biggest discount
     */
    List<Book> findTop5ByOrderByDiscountDesc();

}
