package opg9x5;

public class Fight<T> {

	T one;
	T two;
	T winner;

	Fight(T one, T two, T winner){
		this.one = one;
		this.two = two;
		this.winner = winner;
	}

	public T getWinner(){
		return winner;
	}

	public String toString(){
		return ("Fight between " + one + " and " + two + ". " + winner + " wins");
	}

}
