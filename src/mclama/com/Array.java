package mclama.com;

public class Array implements ArrayInterface {
	// data members
	int size;
	int objectO;

	// Constructor
	public Array() {
		this.size = 0;
		this.objectO = 0;

	}

	public void setSize(int size) {
		this.size = size;

	}

	public void setObjectO(int objectO) {
		this.objectO = objectO;

	}

	public int getSize(int size) {
		return size;
	}

	
	public int getObjectO(int objectO) {
		return objectO;
	}

	@Override
	public int addObjectO() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getObjectO() {
		// TODO Auto-generated method stub
		return objectO;
	}

	@Override
	public int remove() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}