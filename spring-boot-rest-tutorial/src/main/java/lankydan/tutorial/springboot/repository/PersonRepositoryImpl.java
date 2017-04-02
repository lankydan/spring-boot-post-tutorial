package lankydan.tutorial.springboot.repository;

import lankydan.tutorial.springboot.dto.PersonDTO;
import org.springframework.stereotype.Service;

@Service
public class PersonRepositoryImpl implements PersonRepository {

	@Override
	public void persist(final PersonDTO personDTO) {
		// implementation of persisting to database
	}
}
