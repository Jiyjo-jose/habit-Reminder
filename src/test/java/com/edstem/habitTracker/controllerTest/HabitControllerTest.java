package com.edstem.habitTracker.controllerTest;

import com.edstem.habitTracker.controller.HabitController;
import com.edstem.habitTracker.service.HabitService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HabitControllerTest {

    @Mock private HabitService habitService;

    @Mock private ModelMapper modelMapper;
    @InjectMocks private HabitController habitController;
}
