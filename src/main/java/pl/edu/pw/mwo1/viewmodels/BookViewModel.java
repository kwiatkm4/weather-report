package pl.edu.pw.mwo1.viewmodels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import lombok.Getter;
import pl.edu.pw.mwo1.models.BookDto;
import pl.edu.pw.mwo1.services.BookService;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BookViewModel {
    private final static int DATA_PER_PAGE = 9;
    private final BookService service;
    @Getter
    private final IntegerProperty pageQuantProperty;
    private List<BookDto> books;
    @Getter
    private final ListProperty<BookDto> booksOnPage;

    public BookViewModel() {
        this.service = new BookService();
        this.booksOnPage = new SimpleListProperty<>(FXCollections.observableList(new ArrayList<>()));
        this.pageQuantProperty = new SimpleIntegerProperty(1);
        this.books = new ArrayList<>();
    }

    public void get() {
        var data = service.getAll();

        if (data != null && !data.isEmpty()) {
            books = data;
            int pageCount = data.size() / DATA_PER_PAGE;
            if (data.size() % DATA_PER_PAGE != 0) pageCount++;

            pageQuantProperty.setValue(pageCount);

            changePage(0);
        } else {
            books.clear();
            booksOnPage.clear();
            pageQuantProperty.setValue(1);
        }
    }

    public void create(String title, String author, String publisher, String page, Date date, String isbn) {
        service.create(BookDto.builder()
                .title(title)
                .authorId(Integer.parseInt(author))
                .publisherId(Integer.parseInt(publisher))
                .pageCount(Integer.parseInt(page))
                .releaseDate(date)
                .isbn(isbn)
                .build());
        get();
    }

    public void update(String id, String title, String author, String publisher, String page, Date date, String isbn) {
        service.update(Integer.parseInt(id),
                BookDto.builder()
                        .title(title)
                        .authorId(Integer.parseInt(author))
                        .publisherId(Integer.parseInt(publisher))
                        .pageCount(Integer.parseInt(page))
                        .releaseDate(date)
                        .isbn(isbn)
                        .build());
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
        var pageData = new ArrayList<BookDto>();

        for (int i = currentPageIndex * DATA_PER_PAGE; i < Math.min(books.size(), (currentPageIndex + 1) * DATA_PER_PAGE); i++) {
            pageData.add(books.get(i));
        }

        booksOnPage.clear();
        booksOnPage.addAll(pageData);
    }
}
