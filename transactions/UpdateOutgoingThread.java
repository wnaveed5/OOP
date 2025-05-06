package transactions;

class UpdateOutgoingThread implements Runnable {
	private OutgoingTransaction tr;
	
	public UpdateOutgoingThread(OutgoingTransaction outTr) {
		this.tr = outTr;
	}
	
	@Override
	public void run() {
		tr.executeTransaction();
	}
}