package com.example.simple.service;

import com.example.simple.config.exception.FunctionalException;
import com.example.simple.config.util.ExceptionEnum;
import com.example.simple.domain.Simple;
import com.example.simple.repository.SimpleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SimpleServiceImpl implements SimpleService {

    private final SimpleRepository simpleRepository;

    @Override
    public List<Simple> findAllSimple() {
        final var simpleList = simpleRepository.findAll();

        return simpleList != null ? simpleList : List.of();
    }

    @Override
    public Simple findSimpleById(String simpleId) throws FunctionalException {
        final var simple = simpleRepository.findBySimpleId(simpleId);

        return validateFindSimpleByIdResponse(simple.orElse(null), simpleId);
    }

    private Simple validateFindSimpleByIdResponse(Simple simple, String simpleId) throws FunctionalException {
        if (simple == null || simple.isEmpty())
            throw new FunctionalException(
                    "Not valid findBySimpleId response",
                    ExceptionEnum.NO_DATA_FOUND,
                    "ID [".concat(simpleId).concat("] not exist"));
        return simple;
    }
}
