package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class VisitorController {

	private List<Visitor> visitors = new ArrayList<>();
	private Map<String, Visitor> cache = new HashMap<>();

	@PostMapping(value = "v1/visitors")
	public ResponseEntity<String> addVisitor(@RequestBody Visitor visitor) {

		if (!this.cache.containsKey(String.valueOf(visitor.hashCode()))) {
			this.cache.put(String.valueOf(visitor.hashCode()), visitor);
			this.visitors.add(visitor);
		}

		return new ResponseEntity<>(String.valueOf(visitor.hashCode()), HttpStatus.CREATED);
	}

	@PutMapping(value = "v1/visitors/{id}")
	public ResponseEntity<String> signOut(@PathVariable String id, @RequestBody String date) throws InterruptedException {

		if(cache.containsKey(id)){
			cache.get(id).setSignOutTime(Long.valueOf(date));
			return ResponseEntity.status(HttpStatus.OK).build();
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@RequestMapping(value = "v1/visitors", method = RequestMethod.GET)
	public List<Visitor> getVisitors() throws InterruptedException {
		buildInitialVisitors();

		return visitors;

	}
	
	@RequestMapping(value = "v1/visitors/{id}", method = RequestMethod.GET)
	public ResponseEntity<Visitor> getVisitor(@PathVariable String id) throws InterruptedException {

		if(cache.containsKey(String.valueOf(id))){
			return new ResponseEntity<>(cache.get(String.valueOf(id)), HttpStatus.OK);
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
 
	}

	private void buildInitialVisitors() {

		Visitor vis1 = new Visitor("", "Esteban", "Arango", "Frisbee and Vegan food", 0);
		vis1.setId(String.valueOf(vis1.hashCode()));
		Visitor vis2 = new Visitor("", "Ryan", "Labouve", "Everything about Oklahoma", 0);
		vis2.setId(String.valueOf(vis2.hashCode()));

		if (!this.cache.containsKey(vis1.getId())) {
			this.cache.put(vis1.getId(), vis1);
			this.visitors.add(vis1);
		}

		if (!this.cache.containsKey(vis2.getId())) {
			this.cache.put(vis2.getId(), vis2);
			this.visitors.add(vis2);
		}

	}
}
