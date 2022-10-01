package me.seantwiehaus.zbbp.dao.repository;

import me.seantwiehaus.zbbp.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
  @QueryHints({ @QueryHint(name = "hibernate.query.passDistinctThrough", value = "false") })
  @EntityGraph("category.lineItems.transactions")
  List<CategoryEntity> findAllByBudgetDateBetween(LocalDate startDate, LocalDate endDate);
}
