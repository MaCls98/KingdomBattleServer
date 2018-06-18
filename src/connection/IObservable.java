package connection;

public interface IObservable {
	
	void addObserver(IObserver iObserver);
	void removeObserver();
}
