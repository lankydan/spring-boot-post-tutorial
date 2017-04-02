package lankydan.tutorial.springboot.repository;

import lankydan.tutorial.springboot.dto.PersonDTO;

public interface PersonRepository {

	void persist(PersonDTO personDTO);

}
