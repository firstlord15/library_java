package org.ratmir.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ratmir.project.dto.*;
import org.ratmir.project.models.Author;
import org.ratmir.project.models.Book;
import org.ratmir.project.models.Genre;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {
    // Для публичного каталога
    default BookPublicDTO toBookPublicDTO(Book book) {
        return new BookPublicDTO(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getRating(),
                book.getPhotoUrl(),
                toGenreDTOList(book.getGenres()),
                toAuthorDTOList(book.getAuthors()),
                book.getPublishingDate()
        );
    }

    // Для админки/автора (со статусом модерации)
    default BookAuthorDTO toBookAuthorDTO(Book book) {
        return new BookAuthorDTO(
                book.getId(),
                book.getTitle(),
                book.getDescription(),
                book.getStatus(),
                book.getRating(),
                book.getPhotoUrl(),
                book.getPublishingDate(),
                book.getInventoryNumber(),
                toGenreDTOList(book.getGenres()),
                toAuthorDTOList((book.getAuthors())),
                book.getOrigin().getId(),
                book.getOrigin().getUsername()
        );
    }

    default List<BookPublicDTO>  toBookPublicDTOList(List<Book> books) {
        return books.stream()
                .map(this::toBookPublicDTO)
                .toList();
    }

    // Из DTO в Entity (при создании)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "origin", ignore = true)
    Book fromCreateDTO(CreateBookDTO dto);

    // Обновление Book
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "genres", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "rating", ignore = true)
    @Mapping(target = "origin", ignore = true)
    void updateFromDTO(UpdateBookDTO dto, @MappingTarget Book book);

    // Вспомогательные методы
    private List<GenreDTO> toGenreDTOList(List<Genre> genres) {
        return genres == null ? List.of() : genres.stream()
                .map(g -> new GenreDTO(g.getId(), g.getName()))
                .toList();
    }

    private List<AuthorShortDTO> toAuthorDTOList(List<Author> authors) {
        return authors == null ? List.of() : authors.stream()
                .map(a -> new AuthorShortDTO(a.getId(), a.getName(), a.getSurname()))
                .toList();
    }
}
