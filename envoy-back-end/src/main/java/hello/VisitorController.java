package hello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class VisitorController {

	private List<Visitor> visitors = new ArrayList<>();
	private Map<Integer, Visitor> cache = new HashMap<>();

	@PostMapping(value = "/addVisitor")
	public List<Visitor> addVisitor(@RequestBody Visitor visitor) {

		if (!this.cache.containsKey(visitor.hashCode())) {
			this.cache.put(visitor.hashCode(), visitor);
			this.visitors.add(visitor);
		}

		return visitors;
	}

	@PostMapping(value = "/signOut")
	public List<Visitor> signOut(@RequestBody Visitor visitor) throws InterruptedException {

		cache.get(visitor.hashCode()).setSignOutTime(visitor.getSignOutTime());

		return visitors;
	}

	@RequestMapping(value = "/getVisitors", method = RequestMethod.GET)
	public List<Visitor> getVisitors() throws InterruptedException {
		buildInitialVisitors();

		return visitors;

	}

	private void buildInitialVisitors() {

		Visitor vis1 = new Visitor("Esteban", "Arango", "Frisbee and Vegan food", 0);
		Visitor vis2 = new Visitor("Ryan", "Labouve", "Everything about Oklahoma", 0);

		if (!this.cache.containsKey(vis1.hashCode())) {
			this.cache.put(vis1.hashCode(), vis1);
			this.visitors.add(vis1);
		}

		if (!this.cache.containsKey(vis2.hashCode())) {
			this.cache.put(vis2.hashCode(), vis2);
			this.visitors.add(vis2);
		}

	}
}
