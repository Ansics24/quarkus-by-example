package de.schulte.smartbar.backoffice;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public abstract class CrudService<E extends BaseEntity> {

    private final EntityManager entityManager;

    protected CrudService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public E persit(E entity) {
        entityManager.persist(entity);
        return entity;
    }

    public E update(E entity) {
        return entityManager.merge(entity);
    }

    public List<E> listAll() {
        final CriteriaQuery<E> query = entityManager.getCriteriaBuilder().createQuery(getEntityClass());
        final Root<E> root = query.from(getEntityClass());
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public Optional<E> getById(Long id) {
        return Optional.ofNullable(entityManager.find(getEntityClass(), id));
    }

    public Optional<E> deleteById(Long id) {
        final Optional<E> entity = getById(id);
        entity.ifPresent(e -> entityManager.remove(e));
        return entity;
    }

    protected abstract Class<E> getEntityClass();

}
