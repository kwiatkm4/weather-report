package pl.edu.pw.mwo1.viewmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import pl.edu.pw.mwo1.models.PublisherDto;
import pl.edu.pw.mwo1.services.PublisherService;

import java.util.ArrayList;
import java.util.List;

public class PublisherViewModel {
    private final static int DATA_PER_PAGE = 10;
    private final PublisherService service;
    @Getter
    private final IntegerProperty pageQuantProperty;
    private List<PublisherDto> allPubs;
    @Getter
    private final ListProperty<PublisherDto> pubsOnPage;

    public PublisherViewModel() {
        this.service = new PublisherService();
        this.pubsOnPage = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.pageQuantProperty = new SimpleIntegerProperty(1);
        this.allPubs = new ArrayList<>();
    }

    public void get() {
        var data = service.getAll();

        if (data != null && !data.isEmpty()) {
            allPubs = data;
            int pageCount = data.size() / DATA_PER_PAGE;
            if (data.size() % DATA_PER_PAGE != 0) pageCount++;

            pageQuantProperty.setValue(pageCount);

            changePage(0);
        } else {
            allPubs.clear();
            pubsOnPage.clear();
            pageQuantProperty.setValue(1);
        }
    }

    public void create(String name) {
        if (name == null || name.length() < 2 || name.length() > 50) return;

        service.create(PublisherDto.builder().name(name).build());
        get();
    }

    public void update(String id, String name) {
        if (name == null || name.length() < 2 || name.length() > 50) return;

        int dataId;
        try {
            dataId = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("Wrong data in ID field.");
            return;
        }

        service.update(dataId, PublisherDto.builder().name(name).build());
        get();
    }

    public void delete(String id) {
        int dataId;
        try {
            dataId = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("Wrong data in ID field.");
            return;
        }

        service.delete(dataId);
        get();
    }

    public void changePage(int currentPageIndex) {
        pubsOnPage.clear();

        for (int i = currentPageIndex * DATA_PER_PAGE; i < Math.min(allPubs.size(), (currentPageIndex + 1) * DATA_PER_PAGE); i++) {
            pubsOnPage.add(allPubs.get(i));
        }
    }
}
