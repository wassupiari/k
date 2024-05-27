package it.unisa.control;
import java.io.IOException; 
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.ProdottoBean;
import it.unisa.model.ProdottoDao;

@WebServlet("/catalogo")
public class CatalogoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProdottoDao prodDao = new ProdottoDao();
        ProdottoBean bean = new ProdottoBean();
        String sort = request.getParameter("sort");
        String action = request.getParameter("action");
        String redirectedPage = request.getParameter("page");

        try {
            if (action != null) {
                if (action.equalsIgnoreCase("add")) {
                    bean.setNome(sanitize(request.getParameter("nome")));
                    bean.setDescrizione(sanitize(request.getParameter("descrizione")));
                    bean.setIva(sanitize(request.getParameter("iva")));
                    bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
                    bean.setQuantità(Integer.parseInt(request.getParameter("quantità")));
                    bean.setPiattaforma(sanitize(request.getParameter("piattaforma")));
                    bean.setGenere(sanitize(request.getParameter("genere")));
                    bean.setImmagine(sanitize(request.getParameter("img")));
                    bean.setDataUscita(sanitize(request.getParameter("dataUscita")));
                    bean.setDescrizioneDettagliata(sanitize(request.getParameter("descDett")));
                    bean.setInVendita(true);
                    prodDao.doSave(bean);
                } else if (action.equalsIgnoreCase("modifica")) {
                    bean.setIdProdotto(Integer.parseInt(request.getParameter("id")));
                    bean.setNome(sanitize(request.getParameter("nome")));
                    bean.setDescrizione(sanitize(request.getParameter("descrizione")));
                    bean.setIva(sanitize(request.getParameter("iva")));
                    bean.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
                    bean.setQuantità(Integer.parseInt(request.getParameter("quantità")));
                    bean.setPiattaforma(sanitize(request.getParameter("piattaforma")));
                    bean.setGenere(sanitize(request.getParameter("genere")));
                    bean.setImmagine(sanitize(request.getParameter("img")));
                    bean.setDataUscita(sanitize(request.getParameter("dataUscita")));
                    bean.setDescrizioneDettagliata(sanitize(request.getParameter("descDett")));
                    bean.setInVendita(true);
                    prodDao.doUpdate(bean);
                }

                request.getSession().removeAttribute("categorie");
            }

        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        try {
            request.getSession().removeAttribute("products");
            request.getSession().setAttribute("products", prodDao.doRetrieveAll(sort));
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/" + redirectedPage);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    // Metodo per sanificare i dati forniti dall'utente
    private String sanitize(String input) {
        // Sostituisci < con &lt; e > con &gt;
        return input != null ? input.replaceAll("<", "&lt;").replaceAll(">", "&gt;") : null;
    }
}
