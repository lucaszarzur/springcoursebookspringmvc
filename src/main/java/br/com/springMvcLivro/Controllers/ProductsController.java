package br.com.springMvcLivro.Controllers;

import br.com.springMvcLivro.DAO.ProductDAO;
import br.com.springMvcLivro.models.Price;
import br.com.springMvcLivro.models.Product;
import br.com.springMvcLivro.validation.ProductValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@Transactional
@RequestMapping("/produtos")
public class ProductsController {

    @Autowired
    private ProductDAO productDAO;

    /* Indica para o Spring que esse método deve ser chamado sempre que um request cair no controller em questão
       utilizado para validações */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // não precisa por enquanto, utilizaremos os padroes do Hibernate
        //binder.setValidator(new ProductValidator());
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("products/list");
        modelAndView.addObject("products", productDAO.list());

        return modelAndView;
    }

    @RequestMapping(method=RequestMethod.POST)
    public ModelAndView save(@Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return form(product);
        }

        productDAO.save(product);
        redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
        return new ModelAndView("redirect:produtos");
    }

    @RequestMapping("/form")
    public ModelAndView form(@ModelAttribute Product product){
        ModelAndView modelAndView = new ModelAndView("products/form");
        modelAndView.addObject("bookTypes", Price.BookType.values());

        return modelAndView;
    }
}
