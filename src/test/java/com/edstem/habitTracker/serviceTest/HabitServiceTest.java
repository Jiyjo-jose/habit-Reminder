package com.edstem.habitTracker.serviceTest;

import static org.junit.jupiter.api.Assertions.*;

import com.edstem.habitTracker.repository.HabitRepository;
import com.edstem.habitTracker.service.HabitService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HabitServiceTest {

    private HabitRepository habitRepository;
    private ModelMapper modelMapper;
    private HabitService habitService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        habitRepository = Mockito.mock(HabitRepository.class);
        modelMapper = new ModelMapper();
        //        habitService = new HabitService(modelMapper, habitRepository);
    }
}
