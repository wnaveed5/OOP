package transactions;

import java.util.Date;

import stocks.Store;

class UpdateOutgoingThread extends OutgoingTransaction implements Runnable{

	public UpdateOutgoingThread(Store s) {
		super(s);
	}


	private OutgoingTransaction tr;
	
	public void setOutgoingTransaction(OutgoingTransaction outTr)
	{
		this.tr = outTr;
	}
	
	

	
	@Override
	public void run() {
		tr.updateProductStock();
	}
}