package hac.ex4.repos.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class BookService {

    @Autowired
    private BookRepository repo;//the book repository

    public static final int RESULT_PER_PAGE = 10; //number of books per page

    private String repoName = "books"; // the repository name

    /**
     * @return the repository
     */
    public BookRepository getRepo() {
        return repo;
    }

    /**
     * @return - the repository name
     */
    public String getRepoName() {
        return repoName;
    }

    /**
     * @param pageNum - the requested page number
     * @return - the requested page with books
     */
    public ModelMap getPageAll(int pageNum) {
        Page<Book> page = repo.findAll(PageRequest.of(pageNum - 1, RESULT_PER_PAGE));
        return getBookPaging(page, pageNum);
    }

    /**
     *
     * @param bookName - the substring to find
     * @param pageNum - the requested page number
     * @return - the requested page with books
     */
    public ModelMap getPageByName(String bookName, int pageNum) {
        Page<Book> page = repo.findBookByName(bookName, PageRequest.of(pageNum - 1, RESULT_PER_PAGE));
        return getBookPaging(page, pageNum);
    }

    /**
     *
     * @param page
     * @param pageNum
     * @return
     */
    public ModelMap getBookPaging(Page<Book> page, int pageNum) {

        List<Book> listProducts = page.getContent();
        ModelMap model = new ModelMap();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProducts", listProducts);

        return model;
    }

    public List<Book> findBookTop5ByDiscount() {
        return repo.findTop5ByOrderByDiscountDesc();
    }

}
