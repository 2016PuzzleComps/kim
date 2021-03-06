package rushhour.generation;

import rushhour.core.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class UniformBoardGenerator implements BoardGenerator {

	// vip in solved position
	private Car vip;
	// all other cars
	private List<Car> carOptions;
	// temp vars (to save memory)
	private List<Car> cars;
	private Board board;
	// rng
	private Random rng;
	
	public UniformBoardGenerator() {
		this.rng = new Random();
		this.vip = null;
		this.carOptions = new ArrayList<Car>();
		this.board = new Board(6, 6);
		this.cars = new ArrayList<Car>();
	}

	private void init(int maxVipX) {
		this.carOptions.clear();
		this.vip = new Car(rng.nextInt(maxVipX + 1), 2, 2, true);
		this.board.clear();
		this.board.addCar(this.vip);
		for(int x=0; x<6; x++) {
			for(int y=0; y<6; y++) {
				// horiz
				for(int length=2; length<=3 && x+length-1<6; length++) {
					Car toAdd = new Car(x, y, length, true);
					carOptions.add(toAdd);
				}
				// vert
				for(int length=2; length<=3 && y+length-1<6; length++) {
					Car toAdd = new Car(x, y, length, false);
					carOptions.add(toAdd);
				}
			}
		}
		this.cars.clear();
		for(Car c : this.carOptions) {
			this.cars.add(c);
		}
		Collections.shuffle(this.cars);
	}

	public Board generate(int targetNumCars, int maxVipX) {
		this.init(maxVipX);
		while(this.board.numCars() < targetNumCars) {
			if(this.cars.isEmpty()) {
				this.init(maxVipX); // start over
			}
			for(int i=0; i<this.cars.size(); i++) {
				Car c = this.cars.get(i);
				if(this.board.canAddCar(c)) { 
					this.updateCars(c);
					break;
				} else {
					this.cars.remove(c);
				}
			}
		}
		return this.board;
	}

	private void updateCars(Car toAdd) {
		this.board.addCar(toAdd);
		this.cars.remove(toAdd);
		if(toAdd.horizontal) {
			for(int i=0; i<this.cars.size(); i++) { // can't use iterator b/c concurrency
				Car c = this.cars.get(i);
				for(int x=toAdd.x; x<toAdd.x+toAdd.length; x++) {
					if(c.occupiesPos(x, toAdd.y)) {
						this.cars.remove(c);
						break;
					}
				}
			}
		} else {
			for(int i=0; i<this.cars.size(); i++) { // can't use iterator b/c concurrency
				Car c = this.cars.get(i);
				for(int y=toAdd.y; y<toAdd.y+toAdd.length; y++) {
					if(c.occupiesPos(toAdd.x, y)) {
						this.cars.remove(c);
						break;
					}
				}
			}
		}
	}

}
