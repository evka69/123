package ru.flamexander.spring.security.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.flamexander.spring.security.jwt.dtos.CategoriesDTO;
import ru.flamexander.spring.security.jwt.dtos.VacancyDto;
import ru.flamexander.spring.security.jwt.entities.Categories;
import ru.flamexander.spring.security.jwt.entities.Vacancy;
import ru.flamexander.spring.security.jwt.repositories.CategoryRepository;
import ru.flamexander.spring.security.jwt.repositories.VacancyRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VacancyService {

    private final VacancyRepository vacancyRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public VacancyDto createVacancy(VacancyDto vacancyDto) {
        if (vacancyRepository.existsByName(vacancyDto.getName())) {
            throw new IllegalStateException("Vacancy with name '" + vacancyDto.getName() + "' already exists");
        }
        Categories category = null;
        if (vacancyDto.getCategories() != null) {
            category = categoryRepository.findByTitle(vacancyDto.getCategories().getTitle())
                    .orElseGet(() -> {
                        Categories newCategory = new Categories();
                        newCategory.setTitle(vacancyDto.getCategories().getTitle());
                        newCategory.setDescription(vacancyDto.getCategories().getDescription());
                        return categoryRepository.save(newCategory);
                    });

        }

        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategories(category);

        Vacancy savedVacancy = vacancyRepository.save(vacancy);
        return mapToDTO(savedVacancy);
    }
    @Transactional
    public VacancyDto updateVacancy(Long id, VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
        if (vacancyDto.getCategories() != null) {
            Categories category = categoryRepository.findByTitle(vacancyDto.getCategories().getTitle())
                    .orElseGet(() -> {
                        Categories newCategory = new Categories();
                        newCategory.setTitle(vacancyDto.getCategories().getTitle());
                        newCategory.setDescription(vacancyDto.getCategories().getDescription());
                        return categoryRepository.save(newCategory);
                    });
            vacancy.setCategories(category);
        }
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());

        Vacancy updatedVacancy = vacancyRepository.save(vacancy);
        return mapToDTO(updatedVacancy);
    }
    public List<VacancyDto> getAllVacancy() {
        return vacancyRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    public VacancyDto getVacancyById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vacancy not found with id: " + id));
        return mapToDTO(vacancy);
    }
    @Transactional
    public void deleteVacancy(Long id) {
        if (!vacancyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vacancy not found with id: " + id);
        }
        vacancyRepository.deleteById(id);
    }
    public List<VacancyDto> searchVacancy(String name) {
        return vacancyRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    private VacancyDto mapToDTO(Vacancy vacancy) {
        CategoriesDTO categoryDTO = null;
        if (vacancy.getCategories() != null) {
            categoryDTO = new CategoriesDTO(
                    vacancy.getCategories().getTitle(),
                    vacancy.getCategories().getDescription()
            );
        }
        return new VacancyDto(
                vacancy.getId(),
                vacancy.getName(),
                vacancy.getDescription(),
                categoryDTO
        );
    }

}