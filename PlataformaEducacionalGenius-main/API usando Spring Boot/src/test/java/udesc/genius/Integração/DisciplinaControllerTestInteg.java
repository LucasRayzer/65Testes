package udesc.genius.Integração;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;
import java.util.UUID;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import udesc.genius.controllers.DisciplinaController;
import udesc.genius.models.DisciplinaModel;
import udesc.genius.models.UsuarioModel;
import udesc.genius.repositories.DisciplinaRepository;
import udesc.genius.repositories.UsuarioRepository;

@SpringBootTest
public class DisciplinaControllerTestInteg {

    @Autowired
    private DisciplinaController disciplinaController;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private DisciplinaRepository disciplinaRepository;

  
}