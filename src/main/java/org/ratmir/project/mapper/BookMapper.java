package org.ratmir.project.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.ratmir.project.dto.author.AuthorShortDTO;
import org.ratmir.project.dto.book.BookDetailDTO;
import org.ratmir.project.dto.book.BookPublicDTO;
import org.ratmir.project.dto.book.CreateBookDTO;
import org.ratmir.project.dto.book.UpdateBookDTO;
import org.ratmir.project.dto.genre.GenreDTO;
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
    default BookDetailDTO toBookDetailDTO(Book book) {
        BookDetailDTO dto = new BookDetailDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setDescription(book.getDescription());
        dto.setStatus(book.getStatus());
        dto.setRating(book.getRating());
        dto.setPhotoUrl(book.getPhotoUrl());
        dto.setPublicationDate(book.getPublishingDate());
        dto.setInventoryNumber(book.getInventoryNumber());
        dto.setGenres(toGenreDTOList(book.getGenres()));
        dto.setAuthors(toAuthorDTOList(book.getAuthors()));

        if (book.getOrigin() != null) {
            dto.setOriginId(book.getOrigin().getId());
            dto.setOriginUsername(book.getOrigin().getUsername());
        }

        return dto;
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
