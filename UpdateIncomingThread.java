package transactions;


class UpdateIncomingThread extends IncomingTransaction implements Runnable{
	
	private IncomingTransaction tr;
	
	public UpdateIncomingThread(IncomingTransaction inTr)
	{
		this.tr = inTr;
	}
	
	@Override
	public void run() {
		tr.updateProductStock();
	}
}