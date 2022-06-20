package hac.ex4.repos.Payments;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    public static final int RESULT_PER_PAGE = 10;

    private String repoName = "payments";

    public PaymentRepository getRepo() {
        return repo;
    }

    public String getRepoName() {
        return repoName;
    }

    public ModelMap getPageAll(int pageNum) {
        Page<Payment> page = repo.findAll(PageRequest.of(pageNum - 1, RESULT_PER_PAGE));
        return getBookPaging(page, pageNum);
    }

    public ModelMap getBookPaging(Page<Payment> page, int pageNum) {

        List<Payment> listProducts = page.getContent();
        ModelMap model = new ModelMap();

        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listProducts", listProducts);

        return model;
    }

}
