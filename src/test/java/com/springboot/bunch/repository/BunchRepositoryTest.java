package com.springboot.bunch.repository;

import com.springboot.bunch.entity.Bunch;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class BunchRepositoryTest {

    @Autowired
    private BunchRepository bunchRepository;

    @DisplayName("JUnit test for save bunch operation")
    @Test
    public void givenBunchObject_whenSave_thenReturnSavedBunch() {
        Bunch bunch = new Bunch();
        bunch.setTitle("dummyTitle");
        bunch.setSubtitle("dummySubtitle");
        bunch.setDescription("dummyDescription");
        bunch.setImageUrl("dummyImageUrl");
        bunch.setAddress("address");
        bunch.setEmail("validEmail@hotmail.com");

        Bunch savedBunch = bunchRepository.save(bunch);

        assertThat(savedBunch).isNotNull();
        assertThat(savedBunch.getId()).isGreaterThan(0);
    }
}
