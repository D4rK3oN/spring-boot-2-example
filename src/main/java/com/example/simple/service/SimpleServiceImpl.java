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

    private static final String FIND_ALL = "ALL";
    private static final String FIND_BY_ALL_FILTERS = "ALL_FILTERS";
    private static final String FIND_BY_NAME = "NAME";
    private static final String FIND_BY_AGE = "AGE";

    private final SimpleRepository simpleRepository;

    @Override
    public List<Simple> findAllSimple(Optional<String> name, Optional<Integer> initialAge, Optional<Integer> finalAge) {
        final List<Simple> simpleList;

        switch (evaluateFindRequest(name, initialAge, finalAge)) {
            case FIND_ALL:
                simpleList = simpleRepository.findAll();
                break;
            case FIND_BY_ALL_FILTERS:
                simpleList = simpleRepository.findAllByCustomFilters(name.get(), initialAge.orElse(0), finalAge.orElse(100));
                break;
            case FIND_BY_NAME:
                simpleList = simpleRepository.findAllByNameIgnoreCaseLike(name.get());
                break;
            case FIND_BY_AGE:
                simpleList = simpleRepository.findAllByAgeBetween(initialAge.orElse(0), finalAge.orElse(100));
                break;
            default:
                return throwErrorEvalFindRequest(name.toString(), initialAge.toString(), finalAge.toString());
        }

        return simpleList != null ? simpleList : List.of();
    }

    private String evaluateFindRequest(Optional<String> name, Optional<Integer> initialAge, Optional<Integer> finalAge) {
        if (name.isPresent() && (initialAge.isPresent() || finalAge.isPresent()))
            return FIND_BY_ALL_FILTERS;

        if (name.isPresent())
            return FIND_BY_NAME;

        if (initialAge.isPresent() || finalAge.isPresent())
            return FIND_BY_AGE;

        return FIND_ALL;
    }

    private List<Simple> throwErrorEvalFindRequest(String name, String initialAge, String finalAge) {
        throw new FunctionalException(
                "Error evaluating find request",
                ExceptionEnum.INTERNAL_SERVER_ERROR,
                "Find by: name ["
                        .concat(name)
                        .concat("] initialAge [")
                        .concat(initialAge)
                        .concat("] finalAge [")
                        .concat(finalAge)
                        .concat("]")
        );
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
    public void deleteSimple(String simpleId) throws FunctionalException {
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
