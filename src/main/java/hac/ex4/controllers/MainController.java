package hac.ex4.controllers;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import hac.ex4.beans.Basket;
import hac.ex4.repos.book.BookService;

/**
 * a controller to manage the site
 */
@Controller
@RequestMapping("/")
public class MainController {

    @Resource(name = "basket")
    private Basket basket; // the cart

    @Autowired
    private BookService service; //the book repository

    /**
     * a route to manage the landing page
     * @param model - the view
     * @return - the index page
     */
    @GetMapping("")
    public String main(Model model) {
        //add to the view the 5 books with highest discount
        model.addAttribute("listProducts", service.findBookTop5ByDiscount());
        return "index";
    }

    /**
     * @param model - the view
     * @param src - the source route
     * @param pageNum - the page number
     * @return - view with books divided to separate pages
     */
    @GetMapping("/{src}/page/{pageNum}")
    public String getPage(Model model, @PathVariable(name = "src") String src,
            @PathVariable(name = "pageNum") int pageNum) {
        model.mergeAttributes(service.getPageAll(pageNum));
        return src;
    }

    /**
     *
     * @param model - the view
     * @param src - the source route
     * @param pageNum - the page number
     * @param keyword - the keyword to search
     * @return - view with books founded in search divided to separate pages
     */
    @GetMapping("/{src}/search/{pageNum}")
    public String getSearchPage(Model model, @PathVariable(name = "src") String src,
            @PathVariable(name = "pageNum") int pageNum, @RequestParam String keyword) {
        if (keyword == null || keyword.isBlank())
            return "redirect:/" + src;
        model.mergeAttributes(service.getPageByName(keyword, pageNum));
        return src;
    }
}
