package com.example.simple.service;

import com.example.simple.domain.Simple;
import com.example.simple.repository.SimpleRepository;
import com.example.simple.util.ExceptionEnum;
import com.example.simple.util.FunctionalException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SimpleServiceImpl implements SimpleService {

    private final SimpleRepository simpleRepository;

    @Override
    public List<Simple> findAllSimple(Optional<String> name) {
        final var simpleList = name.isPresent()
                ? simpleRepository.findAllByNameIgnoreCaseLike(name.get())
                : simpleRepository.findAll();

        return simpleList != null ? simpleList : List.of();
    }

    @Override
    public Simple findSimpleById(String simpleId) throws FunctionalException {
        final var simple = simpleRepository.findBySimpleId(simpleId).orElse(null);

        if (simple == null || simple.isEmpty())
            throw new FunctionalException(
                    "Not valid findBySimpleId response",
                    ExceptionEnum.NO_DATA_FOUND,
                    "ID [".concat(simpleId).concat("] not exist"));

        return simple;
    }

    @Override
    public void saveSimple(String simpleId, Simple simple) throws FunctionalException {
        try {
            simpleRepository.save(simple.toBuilder().simpleId(simpleId).build());
        } catch (DuplicateKeyException ex) {
            throw new FunctionalException(
                    ex.getMessage(),
                    ExceptionEnum.CONFLICT,
                    "Index <simpleId> : duplicate key [".concat(simpleId).concat("]")
            );
        }
    }

    @Override
    public void deleteSimpleById(String simpleId) throws FunctionalException {
        simpleRepository.delete(findSimpleToDeleteBySimpleId(simpleId));
    }

    private Simple findSimpleToDeleteBySimpleId(String simpleId) throws FunctionalException {
        final var simple = simpleRepository.findBySimpleId(simpleId).orElse(null);

        if (simple == null || simple.isEmpty())
            throw new FunctionalException(
                    "Resource to delete not found",
                    ExceptionEnum.NO_DATA_FOUND,
                    "ID [".concat(simpleId).concat("] not exist"));

        return simple;
    }
}
