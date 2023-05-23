package com.elibrary.group4.Specification;

import com.elibrary.group4.model.Book;
import com.elibrary.group4.model.Category;
import com.sun.tools.jconsole.JConsoleContext;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

@Data
@Builder
public class BookSpecification implements Specification<Book> {

    private String searchTerm;
    private String title;
    private String authorName;
    private String publisher;
    private Integer publicationYear;
    private String category;



    @Override
    public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        Predicate titlePred = ofNullable(title)
                .map(b -> like(cb, root.get("title"), title))
                .orElse(null);
        Predicate authorNamePred = ofNullable(authorName)
                .map(h -> like(cb, root.get("authorName"), authorName))
                .orElse(null);
        Predicate publisherPred = ofNullable(publisher)
                .map(h -> like(cb, root.get("publisher"), publisher))
                .orElse(null);

//        Predicate publicationYearPred = ofNullable(publicationYear)
//                .map(h -> like(cb, root.get("publicationYear").toString(), publicationYear.toString()))
//                .orElse(null);

        Predicate publicf = cb.and(like(cb, root.get("publisher"), publisher));
        Predicate categoryPred = categoryPredicate(root, cb);

        List<Predicate> predicates = new ArrayList<>();
        ofNullable(titlePred).ifPresent(predicates::add);
        ofNullable(authorNamePred).ifPresent(predicates::add);
        ofNullable(publicf).ifPresent(predicates::add);
//        ofNullable(publicationYearPred).ifPresent(predicates::add);
        ofNullable(categoryPred).ifPresent(predicates::add);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
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


}
