package ebook.controller;

public class EbookingControl {
	enum Status {IDLE, LOOKINGUPRESERVATION, DISPLAYINGFLIGHT, WAITFORRESPONSE, 
		WAITFORBAGGAGENUMBERS,WAITFORDOCUMENTSWITHRAWAL, SOUNDINGALARM}
	
	private Status current;
	private IEbookingReaction reactions;
	
	public EbookingControl(IEbookingReaction react) {
		setReactions(react);
		// set initial state
		setCurrent(Status.IDLE); 
	}
	
	public Status getCurrent() {
		return current;
	}
	public void setCurrent(Status current) {
		this.current = current;
	}
	
	public IEbookingReaction getReactions() {
		return reactions;
	}

	public void setReactions(IEbookingReaction reactions) {
		this.reactions = reactions;
	}
	
	public void reservationNumber() throws EbookingEventNotDefineException {
		if (current == Status.IDLE) {
			reactions.lookupReservation();
			setCurrent(Status.LOOKINGUPRESERVATION);
		} else {
			throw new EbookingEventNotDefineException("reservationNumber");
		}
	}
	
	public void found() throws EbookingEventNotDefineException {
		if (current == Status.LOOKINGUPRESERVATION) {
			reactions.displayFlight();
			setCurrent(Status.DISPLAYINGFLIGHT);
		} else {
			throw new EbookingEventNotDefineException("found");
		}
	}
	
	public void notFound() throws EbookingEventNotDefineException {
		if (current == Status.LOOKINGUPRESERVATION) {
			reactions.errorMessage();
			reactions.askForReservationNumber();
			setCurrent(Status.IDLE);
		} else {
			throw new EbookingEventNotDefineException("notFound");
		}
	}
	
	public void confirm() throws EbookingEventNotDefineException {
		if (current == Status.DISPLAYINGFLIGHT) {
			reactions.askForBaggages();
			setCurrent(Status.WAITFORRESPONSE);
		} else {
			throw new EbookingEventNotDefineException("confirm");
		}
	}
	
	public void change() throws EbookingEventNotDefineException {
		if (current == Status.DISPLAYINGFLIGHT) {
			reactions.displayReservationDetails();
			reactions.askCustomerWishToChange();
		} else {
			throw new EbookingEventNotDefineException("change");
		}
	}
	
	public void cancel() throws EbookingEventNotDefineException {
		if (current == Status.DISPLAYINGFLIGHT || 
				current == Status.WAITFORBAGGAGENUMBERS ||
				current == Status.WAITFORDOCUMENTSWITHRAWAL ||
				current == Status.WAITFORRESPONSE ) {
			reactions.askForReservationNumber();
			setCurrent(Status.IDLE);
		} else {
			throw new EbookingEventNotDefineException("cancel");
		}
	}
	
	public void no() throws EbookingEventNotDefineException {
		if (current == Status.WAITFORRESPONSE) {
			reactions.printBoardingPass();
			reactions.ejectBoardingPass();
			setCurrent(Status.WAITFORDOCUMENTSWITHRAWAL);
		} else {
			throw new EbookingEventNotDefineException("no");
		}
	}
	
	public void yes() throws EbookingEventNotDefineException {
		if (current == Status.WAITFORRESPONSE) {
			reactions.askForNumberOfPieces();
			setCurrent(Status.WAITFORBAGGAGENUMBERS);
		} else {
			throw new EbookingEventNotDefineException("yes");
		}
	}
	
	public void numberOfPieces() throws EbookingEventNotDefineException {
		if (current == Status.WAITFORBAGGAGENUMBERS) {
			reactions.printBoardingPass();
			reactions.ejectBoardingPass();
			reactions.printBaggageSlips();
			reactions.ejectBaggageSlips();
			reactions.displayProceedsToAgentMessage();
			setCurrent(Status.WAITFORDOCUMENTSWITHRAWAL);
		} else {
			throw new EbookingEventNotDefineException("numberOfPieces");
		}
	}
	
	public void withdrawDocuments() throws EbookingEventNotDefineException {
		if (current == Status.WAITFORDOCUMENTSWITHRAWAL) {
			reactions.askForReservationNumber();
			setCurrent(Status.IDLE);
		} else if (current == Status.SOUNDINGALARM) {
			reactions.stopAlarm();
			reactions.askForReservationNumber();
			setCurrent(Status.IDLE);
		} else {
			throw new EbookingEventNotDefineException("withdrawDocuments");
		}
	}
	
	public void timeout() throws EbookingEventNotDefineException {
		if (current == Status.WAITFORDOCUMENTSWITHRAWAL) {
			reactions.startAlarm();
			setCurrent(Status.SOUNDINGALARM);
		} else {
			throw new EbookingEventNotDefineException("timeout");
		}
	}
}
