package rushhour.generation;

import rushhour.core.*;

import java.util.Random;

public class FastBoardGenerator implements BoardGenerator {

	private Board board;
	private Random random;

	public FastBoardGenerator(){
		this.board = new Board(6, 6);
		this.random = new Random();
	}

	private void init(int maxVipX){
		this.board.clear();
		this.board.addCar(new Car(random.nextInt(maxVipX), 2, 2, true));
	}

	public Board generate(int targetNumCars, int maxVipX) {
		this.init(maxVipX);
		while(!this.tryGenerate(targetNumCars, maxVipX));
		return this.board;
	}

	/**
	 * Generates board with n cars
	 * If fails to make board after 100 tries, stops
	 * @return true is success, false if fails after 100 tries
	 */
	private boolean tryGenerate(int targetNumCars, int maxVipX) {
		int i = 0;
		boolean succ;
		while(i < 100){
			succ = addNRCars(targetNumCars);
			if (succ == true){
				return true;
			} else {
				this.init(maxVipX);
				i++;
			}
		}
		return false;
	}

	/**
	 * Add N random Cars. If fails, clears boards
	 * @return true if succ, false if fail
	 */
	private boolean addNRCars(int targetNumCars){
		int numCars = 1; // b/c we already have the vip
		while(numCars < targetNumCars){
			//if there is no space to put in a car
			if (!this.board.hasEmpty()){
				return false;
			}
			if(this.addRandomCar()) {
				numCars++;
			}
		}
		return true;
	}

	/**
	 * Tries to add random car.
	 * @param board
	 * @return true if successful, false if not
	 */
	public boolean addRandomCar(){
		int len = random.nextInt(2);
		len += 2;
		int hor = random.nextInt(2);
		int x = 0;
		int y = 0;

		if(len == 2 && hor == 0){
			y = random.nextInt(this.board.getHeight()-1);
			x = random.nextInt(this.board.getWidth());
		} else if (len == 3 && hor == 0){
			y = random.nextInt(this.board.getHeight()-2);
			x = random.nextInt(this.board.getWidth());
		} else if (len == 2 && hor == 1){
			y = random.nextInt(this.board.getHeight());
			x = random.nextInt(this.board.getWidth()-1);
		} else {
			y = random.nextInt(this.board.getHeight());
			x = random.nextInt(this.board.getWidth()-2);
		}


		boolean hori = true;
		if (hor == 0){ hori = false;}

		Car car = new Car(x,y,len,hori);
		return this.board.addCar(car);
	}

}
