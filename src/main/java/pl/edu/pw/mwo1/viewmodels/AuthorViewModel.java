package pl.edu.pw.mwo1.viewmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import pl.edu.pw.mwo1.models.AuthorDto;
import pl.edu.pw.mwo1.services.AuthorService;

import java.util.ArrayList;
import java.util.List;

public class AuthorViewModel {
    private final static int DATA_PER_PAGE = 10;
    private final AuthorService service;
    @Getter
    private final IntegerProperty pageQuantProperty;
    private List<AuthorDto> authors;
    @Getter
    private final ListProperty<AuthorDto> authorsOnPage;

    public AuthorViewModel() {
        this.service = new AuthorService();
        this.authorsOnPage = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.pageQuantProperty = new SimpleIntegerProperty(1);
        this.authors = new ArrayList<>();
    }

    public void get() {
        var data = service.getAll();

        if (data != null && !data.isEmpty()) {
            authors = data;
            int pageCount = data.size() / DATA_PER_PAGE;
            if (data.size() % DATA_PER_PAGE != 0) pageCount++;

            pageQuantProperty.setValue(pageCount);

            changePage(0);
        } else {
            authors.clear();
            authorsOnPage.clear();
            pageQuantProperty.setValue(1);
        }
    }

    public void create(String name, String surname, String mail) {
        service.create(AuthorDto.builder().name(name).surname(surname).email(mail).build());
        get();
    }

    public void update(String id, String name, String surname, String mail) {
        int dataId;
        try {
            dataId = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("ERROR: Wrong data in ID field.");
            return;
        }

        service.update(dataId, AuthorDto.builder().name(name).surname(surname).email(mail).build());
        get();
    }

    public void delete(String id) {
        int dataId;
        try {
            dataId = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("ERROR: Wrong data in ID field.");
            return;
        }

        service.delete(dataId);
        get();
    }

    public void changePage(int currentPageIndex) {
        var pageData = new ArrayList<AuthorDto>();

        for (int i = currentPageIndex * DATA_PER_PAGE; i < Math.min(authors.size(), (currentPageIndex + 1) * DATA_PER_PAGE); i++) {
            pageData.add(authors.get(i));
        }

        authorsOnPage.clear();
        authorsOnPage.addAll(pageData);
    }
}
