package transactions;

class UpdateIncomingThread implements Runnable {
	private IncomingTransaction tr;
	
	public UpdateIncomingThread(IncomingTransaction inTr) {
		this.tr = inTr;
	}
	
	@Override
	public void run() {
		tr.executeTransaction();
	}
}