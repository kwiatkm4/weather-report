package pl.edu.pw.mwo1.viewmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import pl.edu.pw.mwo1.models.BookDto;
import pl.edu.pw.mwo1.models.PublisherDto;
import pl.edu.pw.mwo1.services.BookService;
import pl.edu.pw.mwo1.services.PublisherService;

import java.util.ArrayList;
import java.util.List;

public class PublisherViewModel {
    private final static int DATA_PER_PAGE = 5;
    private final PublisherService service;
    @Getter
    private final IntegerProperty pageQuantProperty;
    private List<PublisherDto> allPubs;
    @Getter
    private final ListProperty<PublisherDto> pubsOnPage;

    public PublisherViewModel() {
        this.service = new PublisherService();
        this.pubsOnPage = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.pageQuantProperty = new SimpleIntegerProperty(3);
        this.allPubs = new ArrayList<>();
    }

    public void get() {
        var data = service.getAll();

        if (data != null && !data.isEmpty()) {
            allPubs = data;
            pageQuantProperty.setValue(data.size() / DATA_PER_PAGE + 1);
            pubsOnPage.clear();

            for (int i=0; i<Math.min(data.size(),DATA_PER_PAGE); i++) {
                pubsOnPage.add(data.get(i));
            }
        } else {
            allPubs.clear();
            pubsOnPage.clear();
            pageQuantProperty.setValue(1);
        }
    }

    public void create(String text) {
    }

    public void update(String text, String text1) {
    }

    public void delete(String text) {
    }

    public void changePage(int currentPageIndex) {

    }
}
