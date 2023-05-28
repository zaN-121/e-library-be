package com.elibrary.group4.Specification;

import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.Category;
import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Data
@Builder
public class BookSpecification implements Specification<Book> {
    private String name;
    private String author;
    private String category;
    private String releaseYear;
    private String language;

    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if(name.split(",").length == 1) {
            Predicate namePred = ofNullable(name)
                    .map(b -> like(cb, root.get("name"), name))
                    .orElse(null);
            ofNullable(namePred).ifPresent(predicates::add);
        }
        else{
            name = name.split(",")[1];
            Predicate namePred = ofNullable(name)
                    .map(b -> like(cb, root.get("name"), name))
                    .orElse(null);
            ofNullable(namePred).ifPresent(predicates::add);

        }


        if(author.split(",").length == 1) {
            Predicate authorPred = ofNullable(author)
                    .map(b -> like(cb, root.get("author"), author))
                    .orElse(null);
            ofNullable(authorPred).ifPresent(predicates::add);
        }
        else{
            author = author.split(",")[1];
            Predicate authorPred = ofNullable(author)
                    .map(b -> like(cb, root.get("author"), author))
                    .orElse(null);
            ofNullable(authorPred).ifPresent(predicates::add);

        }

        if(language.split(",").length == 1) {
            Predicate languagePred = ofNullable(language)
                    .map(b -> like(cb, root.get("language"), language))
                    .orElse(null);
            ofNullable(languagePred).ifPresent(predicates::add);
        }
        else{
            language = language.split(",")[1];
            Predicate languagePred = ofNullable(language)
                    .map(b -> like(cb, root.get("language"), language))
                    .orElse(null);
            ofNullable(languagePred).ifPresent(predicates::add);

        }

        if(releaseYear.split(",").length == 1) {
            Predicate releaseYearPred = ofNullable(releaseYear)
                    .map(b -> like(cb, root.get("releaseYear"), releaseYear))
                    .orElse(null);
            ofNullable(releaseYearPred).ifPresent(predicates::add);
        }
        else{
            releaseYear = releaseYear.split(",")[1];
            Predicate releaseYearPred = ofNullable(releaseYear)
                    .map(b -> like(cb, root.get("releaseYear"), releaseYear))
                    .orElse(null);
            ofNullable(releaseYearPred).ifPresent(predicates::add);

        }

        Predicate categoryPred = categoryPredicate(root, cb);
        ofNullable(categoryPred).ifPresent(predicates::add);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));

    }


    private Predicate categoryPredicate(Root<Book> root, CriteriaBuilder cb) {
        if (isNull(category)) {
            return null;
        }

        Join<Book, Category> categoryJoin = root.join("category", JoinType.INNER);

        return cb.and(
                like(cb, categoryJoin.get("name"), category));

    }


    @Override
    public Specification<Book> and(Specification<Book> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Book> or(Specification<Book> other) {
        return Specification.super.or(other);
    }

    private Predicate equals(CriteriaBuilder cb, Path<Object> field, Object value) {
        return cb.equal(field, value);
    }

    private Predicate like(CriteriaBuilder cb, Path<String> field, String searchTerm) {
        return cb.like(cb.lower(field), "%" + searchTerm.toLowerCase() + "%");
    }


    private Predicate between(CriteriaBuilder cb, Path<Integer> field, int min, int max) {
        return cb.between(field, min, max);
    }




}
