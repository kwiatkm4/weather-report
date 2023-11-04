package pl.edu.pw.mwo1.viewmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import pl.edu.pw.mwo1.models.PublisherDto;
import pl.edu.pw.mwo1.services.APIService;
import pl.edu.pw.mwo1.utils.ConfigHandler;

import java.util.ArrayList;
import java.util.List;

public class PublisherViewModel {
    private final static int DATA_PER_PAGE = 10;
    private final APIService<PublisherDto> service;
    @Getter
    private final IntegerProperty pageQuantProperty;
    private List<PublisherDto> publishers;
    @Getter
    private final ListProperty<PublisherDto> publishersOnPage;

    public PublisherViewModel() {
        this.service = new APIService<>(ConfigHandler.getConfig().getPublisher());
        this.publishersOnPage = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.pageQuantProperty = new SimpleIntegerProperty(1);
        this.publishers = new ArrayList<>();
    }

    public void get() {
        var data = service.getAll();

        if (data != null && !data.isEmpty()) {
            publishers = data;
            int pageCount = data.size() / DATA_PER_PAGE;
            if (data.size() % DATA_PER_PAGE != 0) pageCount++;

            pageQuantProperty.setValue(pageCount);

            changePage(0);
        } else {
            publishers.clear();
            publishersOnPage.clear();
            pageQuantProperty.setValue(1);
        }
    }

    public void create(String name) {
        service.create(PublisherDto.builder().name(name).build());
        get();
    }

    public void update(String id, String name) {
        int dataId;
        try {
            dataId = Integer.parseInt(id);
        } catch (Exception e) {
            System.out.println("ERROR: Wrong data in ID field.");
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
            System.out.println("ERROR: Wrong data in ID field.");
            return;
        }

        service.delete(dataId);
        get();
    }

    public void changePage(int currentPageIndex) {
        var pageData = new ArrayList<PublisherDto>();

        for (int i = currentPageIndex * DATA_PER_PAGE; i < Math.min(publishers.size(), (currentPageIndex + 1) * DATA_PER_PAGE); i++) {
            pageData.add(publishers.get(i));
        }

        publishersOnPage.clear();
        publishersOnPage.addAll(pageData);
    }
}
