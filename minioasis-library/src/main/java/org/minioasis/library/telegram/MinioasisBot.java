package org.minioasis.library.telegram;

import static java.lang.Math.toIntExact;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.minioasis.library.domain.Biblio;
import org.minioasis.library.domain.Checkout;
import org.minioasis.library.domain.CheckoutState;
import org.minioasis.library.domain.Item;
import org.minioasis.library.domain.ItemState;
import org.minioasis.library.domain.Patron;
import org.minioasis.library.domain.Photo;
import org.minioasis.library.domain.Preference;
import org.minioasis.library.domain.Reservation;
import org.minioasis.library.domain.ReservationResult;
import org.minioasis.library.domain.ReservationState;
import org.minioasis.library.domain.TelegramUser;
import org.minioasis.library.domain.YesNo;
import org.minioasis.library.domain.search.BiblioCriteria;
import org.minioasis.library.exception.LibraryException;
import org.minioasis.library.repository.PhotoRepository;
import org.minioasis.library.service.LibraryService;
import org.minioasis.library.service.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//@Component
public class MinioasisBot extends TelegramLongPollingBot {

	private static final Logger logger = LoggerFactory.getLogger(MinioasisBot.class);
	
	@Value("${telegrambot.token}")
	private String token;
	
	@Value("${telegrambot.username}")
	private String username;
	
	@Value("${reminder.days}")
	private int reminderDays;

	@Value("${message.command}")
	private String messageCommand;
	
	@Autowired
	private TelegramService telegramService;
	
	@Autowired
	private LibraryService libraryService;
	
	@Autowired
	private PhotoRepository photoRepository;
	
	private static int pageSize = 5;
	
	private static String START = "*Welcome to the BOT !*\n"
								+ "/register : 1st time user\n"
								+ "Other commands : /help";

	private static String SETTINGS = "*Please send me :*\n"
									+ "----------------------------------\n"
									+ "*My Settings*\n"
									+ "/show\\_settings : show my settings"
									+ "\n"
									+ "*All*\n"
									+ "/all\\_on"
									+ "\n"
									+ "/all\\_off"
									+ "\n"
									+ "*Reminder*\n"
									+ "/reminder\\_on"
									+ "\n"
									+ "/reminder\\_off"
									+ "\n"
									+ "*Anouncement*\n"
									+ "/annoucement\\_on"
									+ "\n"
									+ "/annoucement\\_off"
									+ "\n"
//									+ "*Event*\n"
//									+ "/event\\_on"
//									+ "\n"
//									+ "/event\\_off"
//									+ "\n"
//									+ "*New Release*\n"
//									+ "/new\\_release\\_on"
//									+ "\n"
//									+ "/new\\_release\\_off"
//									+ "\n"
//									+ "*Article*\n"
//									+ "/article\\_on"
//									+ "\n"
//									+ "/article\\_off"
//									+ "\n"
//									+ "*Promotion*\n"
//									+ "/promotion\\_on"
//									+ "\n"
//									+ "/promotion\\_off"
									;
	
	private static String HELP = "*Member :*\n"
								+ "/register : 1st time member\n"
								+ "/due : my checkouts\n"
								+ "/renew : renew my books\n"
								+ "/reservation : my reservations"
								+ "\n\n"
								+ "*Public User :*\n"
								+ "/search : search books by title, author\n"
//								+ "/recommendation : recommendation of book"
//								+ "\n\n"
//								+ "*Library Information :*\n"
//								+ "/openinghours : opening hours\n"
//								+ "/holidays : library holidays\n"
//								+ "/news : library news\n"
//								+ "/releases : new book releases\n"
//								+ "/annoucements : new annoucements\n"
//								+ "/events : library events\n"
//								+ "/articles : blog articles\n"
//								+ "/bookstoread : books recommended by library\n"
//								+ "/promotions : library promotions"
//								+ "\n"
								+ "\n"
								+ "*Settings :*\n"
								+ "/settings : settings\n";
	
	private static String REGISTER = "Key in your\n" 
									+ "*1) member id*\n"
									+ "*2) mobile no.*\n"
									+ "\n"
									+ "in the following format :\n"
									+ "reg#*memberId*#*mobile*\n"
									+ "\n"
									+ "example :\n"
									+ "reg#*0415*#*0124444333*";
	
	private static String REGISTRATION_FAILED_MESSAGE = "*FAILED*\n"
														+ "> no member found with this mobile no.";
	
	private static String REGISTRATION_FAILED = "registration failed !";
	
	private static String MOBILE_LENGTH_ERROR = "mobile length > 12  !";
	private static String MOBILE_NOT_NUMBER_ERROR = "mobile has to be number !";
	private static String VERIFICATION_SUCCESS = "verification success !";
	private static String ALREADY_REGISTERED = "already registered !";
	private static String MEMBER_NOT_FOUND = "member not found !";

	private static String ALL_ON = "ALL ON";
	private static String ALL_OFF = "ALL OFF";
	
	private static String REMINDER_ON = "reminder ON";
	private static String REMINDER_OFF = "reminder OFF";
	
	private static String EVENT_ON = "event ON";
	private static String EVENT_OFF = "event OFF";
	
	private static String NEW_RELEASE_ON = "new release ON";
	private static String NEW_RELEASE_OFF = "new release OFF";

	private static String NEW_ANNOUCEMENT_ON = "new annoucement ON";
	private static String NEW_ANNOUCEMENT_OFF = "new annoucement OFF";
	
	private static String ARTICLE_ON = "article ON";
	private static String ARTICLE_OFF = "article OFF";
	
	private static String PROMOTION_ON = "promotion ON";
	private static String PROMOTION_OFF = "promotion OFF";
	
	private static String RENEW_UNSUCCESSFULL = "renew unsuccessfull !";
	private static String RENEW_UNSUCCESSFULLY_INVALIDATE = "renew unsuccessfully : patron or patron type date expired";
	
	@Override
	public void onUpdateReceived(Update update) {	
		
		// We check if the update has a message and the message has text
		if (update.hasMessage() && update.getMessage().hasText()) {
			
			String incoming = update.getMessage().getText();
			
			sendMessage("/start", update, START);
			
			sendMessage("/help", update, HELP);
			
			sendMessage("/settings", update, SETTINGS);
			
			showSettings("/show_settings", update);
			
			registerMessage("/register", update, REGISTER);

			if(incoming.startsWith("reg#") || incoming.startsWith("Reg#")) {
				registrationVerification(update);
			}
			
			if(incoming.startsWith(messageCommand)) {
				sendAnnouncement(messageCommand, update);
			}
			
			checkouts("/due", update);
			
			renewOneByOne("/renew", update);
			
			reservations("/reservation", update);
			
			search("/search", update);
			
			allOn("/all_on", update);
			allOff("/all_off", update);
			
			reminderOn("/reminder_on", update);
			reminderOff("/reminder_off", update);

			annoucementOn("/annoucement_on", update);
			annoucementOff("/annoucement_off", update);
			
//			eventOn("/event_on", update);
//			eventOff("/event_off", update);
//			
//			newReleaseOn("/new_release_on", update);
//			newReleaseOff("/new_release_off", update);
//
//			articleOn("/article_on", update);
//			articleOff("/article_off", update);
//			
//			promotionOn("/promotion_on", update);
//			promotionOff("/promotion_off", update);

		} else if (update.hasCallbackQuery()) {

			long chat_id = update.getCallbackQuery().getMessage().getChatId();
			long message_id = update.getCallbackQuery().getMessage().getMessageId();
			
			String call_data = update.getCallbackQuery().getData();
			
			if(call_data.startsWith("/p^g^^g?")) {
				pagingCallBack(call_data, chat_id, message_id);
			}
			
			if(call_data.startsWith("/biblioinfo")) {
				biblioInfoCallBack(call_data, chat_id, message_id);
			}

			if(call_data.startsWith("/r^s^rv^?")) {
				reserveCallBack(call_data, chat_id, message_id);
			}
			
		}	
	}
	
	private void showSettings(String command, Update update) {
		
		if(update.getMessage().getText().equals(command)){
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				sendMessage(command, update, settingsView(telegramUser));
			}	
		}
	}
	
	private static String settingsView(TelegramUser t) {

		YesNo reminder = t.getPreference().getReminder();
		YesNo announcement = t.getPreference().getSendMeAnnouncement();
		YesNo article = t.getPreference().getSendMeArticle();
		YesNo event = t.getPreference().getSendMeEvent();
		YesNo newRelease = t.getPreference().getSendMeNewRelease();
		YesNo promotion = t.getPreference().getSendMePromotion();

		StringBuffer s = new StringBuffer();

		s.append("-----------------------------------------------\n");
		s.append("*My Settings*\n");
		s.append("-----------------------------------------------\n");
		s.append(reminder + " : reminder" + "\n");
		s.append(announcement + " : announcement" + "\n");
		s.append(article + " : article" + "\n");
		s.append(event + " : event" + "\n");
		s.append(newRelease + " : new release" + "\n");
		s.append(promotion + " : promotion" + "\n");
		s.append("\n");

		return s.toString();
	}
	
	private void sendAnnouncement(String command, Update update) {
		
		String msg = update.getMessage().getText();
		int spaceIndex = command.length() + 1;
		String annoucement = msg.substring(spaceIndex);

		if(msg.startsWith(command) && msg.substring(spaceIndex-1, spaceIndex).equals(" ")
						&& (annoucement != null && !annoucement.isEmpty())) {

			List<TelegramUser> telegramUsers = telegramService.findAllTelegramUsersByAnnoucementOn();
			
			for(TelegramUser t : telegramUsers) {
				sendAnnoucement(t, annoucement);
			}	
		}
	}
	
	private void sendAnnoucement(TelegramUser t, String annoucement) {
		Long chat_id = t.getChatId();
		SendMessage message = new SendMessage().setChatId(chat_id)
				.setText(annoucement)
				.setParseMode(ParseMode.MARKDOWN);
		try {
			execute(message);
			logger.info("TELEGRAM LOG : " + chat_id + " - [ annoucement send ] ");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
	
	// setting *************************************************
	
	private void sendResponse(Update update, String response) {

		Long chat_id = update.getMessage().getChatId();
		SendMessage message = new SendMessage().setChatId(chat_id)
												.setText(response)
												.setParseMode(ParseMode.MARKDOWN);

		try {
			execute(message);
			logger.info("TELEGRAM LOG : " + chat_id + " - [ " + response + " ] ");
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}

	// [all_on]
	private void allOn(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				
				telegramUser.getPreference().setReminder(YesNo.Y);
				telegramUser.getPreference().setSendMeEvent(YesNo.Y);
				telegramUser.getPreference().setSendMeNewRelease(YesNo.Y);
				telegramUser.getPreference().setSendMeAnnouncement(YesNo.Y);
				telegramUser.getPreference().setSendMeArticle(YesNo.Y);
				telegramUser.getPreference().setSendMePromotion(YesNo.Y);
				
				telegramService.save(telegramUser);
				
				sendResponse(update, ALL_ON);			
			}		
		}	
	}
	
	// [all_off]
	private void allOff(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				
				telegramUser.getPreference().setReminder(YesNo.N);
				telegramUser.getPreference().setSendMeEvent(YesNo.N);
				telegramUser.getPreference().setSendMeNewRelease(YesNo.N);
				telegramUser.getPreference().setSendMeAnnouncement(YesNo.N);
				telegramUser.getPreference().setSendMeArticle(YesNo.N);
				telegramUser.getPreference().setSendMePromotion(YesNo.N);
				
				telegramService.save(telegramUser);
				
				sendResponse(update, ALL_OFF);			
			}		
		}	
	}
	
	// [reminder_on]
	private void reminderOn(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setReminder(YesNo.Y);
				telegramService.save(telegramUser);
				
				sendResponse(update, REMINDER_ON);			
			}		
		}	
	}
	
	// [reminder_off]
	private void reminderOff(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setReminder(YesNo.N);
				telegramService.save(telegramUser);
				
				sendResponse(update, REMINDER_OFF);
			}		
		}		
	}
	
	// [event_on]
	private void eventOn(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeEvent(YesNo.Y);
				telegramService.save(telegramUser);
				
				sendResponse(update, EVENT_ON);			
			}		
		}	
	}
	
	// [event_off]
	private void eventOff(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeEvent(YesNo.N);
				telegramService.save(telegramUser);
				
				sendResponse(update, EVENT_OFF);			
			}		
		}	
	}

	// [new_release_on]
	private void newReleaseOn(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeNewRelease(YesNo.Y);
				telegramService.save(telegramUser);
				
				sendResponse(update, NEW_RELEASE_ON);			
			}		
		}	
	}
	
	// [new_release_off]
	private void newReleaseOff(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeNewRelease(YesNo.N);
				telegramService.save(telegramUser);
				
				sendResponse(update, NEW_RELEASE_OFF);			
			}		
		}	
	}
	
	
	// [annoucement_on]
	private void annoucementOn(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeAnnouncement(YesNo.Y);
				telegramService.save(telegramUser);
				
				sendResponse(update, NEW_ANNOUCEMENT_ON);			
			}		
		}	
	}

	// [annoucement_off]
	private void annoucementOff(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeAnnouncement(YesNo.N);
				telegramService.save(telegramUser);
				
				sendResponse(update, NEW_ANNOUCEMENT_OFF);			
			}		
		}	
	}
	
	// [article_on]
	private void articleOn(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeArticle(YesNo.Y);
				telegramService.save(telegramUser);
				
				sendResponse(update, ARTICLE_ON);			
			}		
		}	
	}

	// [article_off]
	private void articleOff(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMeArticle(YesNo.N);
				telegramService.save(telegramUser);
				
				sendResponse(update, ARTICLE_OFF);			
			}		
		}	
	}
	
	// [promotion_on]
	private void promotionOn(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMePromotion(YesNo.Y);
				telegramService.save(telegramUser);
				
				sendResponse(update, PROMOTION_ON);			
			}		
		}	
	}

	// [promotion_off]
	private void promotionOff(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {
			
			Long chat_id = update.getMessage().getChatId();
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
			
			if(telegramUser != null) {
				telegramUser.getPreference().setSendMePromotion(YesNo.N);
				telegramService.save(telegramUser);
				
				sendResponse(update, PROMOTION_OFF);			
			}		
		}	
	}
	
	// [/reservation] **********************************************************************

	/*
	 * @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
	 * 
	 * Fires at 12 PM every day : 						@Scheduled(cron = "0 0 12 * * ?") 
	 * Fires at 10:15AM every day in the year 2005 : 	@Scheduled(cron = "0 15 10 * * ? 2005") 
	 * Fires every 20 seconds : 						@Scheduled(cron = "0/20 * * * * ?") 
	 */
	
	@Scheduled(cron = "0 15 10 * * ?")
	public void notification() {
		
		List<Reservation> reservations = libraryService.findAvailableReservations();
		
		for(Reservation r : reservations) {
			
			Patron p = r.getPatron();
			String cardKey = p.getCardKey();
			
			TelegramUser telegramUser = telegramService.findTelegramUserByCardKey(cardKey);

			if(telegramUser != null) {
				
				Biblio b = r.getBiblio();
				String title = b.getTitle();
				
				Long chat_id = telegramUser.getChatId();		
				SendMessage message = new SendMessage().setChatId(chat_id);
				
				message.setText("Your reservation [ " + title + " ] is AVAILABLE.");
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ NOTIFICATION ] " + title);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}			
			}		
		}	
	}
	
	private void reservations(String command, Update update) {
		
		if(update.getMessage().getText().equals(command)){
			
			Long chat_id = update.getMessage().getChatId();	
			
			SendMessage message = new SendMessage().setChatId(chat_id);
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);

			if(telegramUser == null) {
				
				message.setText(MEMBER_NOT_FOUND);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] " + MEMBER_NOT_FOUND);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				
			}else {
				
				String cardKey = telegramUser.getCardKey();
	
				List<Reservation> rs = libraryService.findByCardKeyAndStates(cardKey, ReservationState.getActives());
				
				message.setText(reservationsView(cardKey, rs))
					   .setParseMode(ParseMode.MARKDOWN);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] ");
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}	
	}
	
	// [/reservation] reservation view
	private static String reservationsView(String cardKey, List<Reservation> reservations) {
		
		Integer total = reservations.size();

		StringBuffer s = new StringBuffer();
		
		if(total < 1) {
			s.append("You have no reservation.");
		}else {
			
			Reservation r1 = reservations.get(0);

			LocalDate end = r1.getPatron().getEndDate();
			
			s.append("_Member :_ *" + cardKey + "* _[ Exp : " + end + " ]_\n");
			s.append("-------------------------------------------------------------\n");
			s.append("_Total : " + total + "                 Date : " + LocalDate.now() + "_\n");
			s.append("\n");
			
			int i = 1;
			
			for(Reservation r : reservations) {

				String title = r.getBiblio().getTitle();
				LocalDateTime reservationDate = r.getReservationDate();
				
				s.append(i + ". _" + title + "_\n");
				s.append("    _Reservation : " + reservationDate.toLocalDate() + "_\n");
				i++;
			}
		}
		
		return s.toString();
	}
	
	// [reserve button]
	private InlineKeyboardMarkup createInlineReserveButton(long bid) {
		
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		
		rowInline.add(new InlineKeyboardButton().setText("RESERVE THE BOOK").setCallbackData("/r^s^rv^?" + bid));
		rowsInline.add(rowInline);
		
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		
		return markupInline;
	}
	
	// [create reserve callback buttons]
	private void reserveCallBack(String call_data, long chat_id, long message_id) {

		String bid = StringUtils.substringAfter(call_data, "/r^s^rv^?");

		TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);
		String cardKey = telegramUser.getCardKey();

		final LocalDateTime nowLDT = LocalDateTime.now();
		final LocalDate now = nowLDT.toLocalDate();

		Biblio biblio = this.libraryService.getBiblioFetchItems(Long.parseLong(bid));

		Patron patron = this.libraryService.preparingPatronForCirculation(cardKey, now);

		ReservationResult result = null;
		SendMessage new_message = new SendMessage().setChatId(chat_id);

		try {

			result = libraryService.reserve(patron, biblio, nowLDT, now.plusDays(90));

			if (result.getReservation() != null) {
				new_message.setText("RESERVED ! /reservation");
			}

		} catch (LibraryException lex) {
			new_message.setText("reservation FAILED !");
		}

		try {
			execute(new_message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}
	
	// [/search] create inline buttons **********************************************************
	private InlineKeyboardMarkup createInlinePagingButtons(Page<Biblio> biblioPage, String keyword) {
		
		List<Biblio> biblios = biblioPage.getContent();
		int page = biblioPage.getPageable().getPageNumber();
		long total = biblioPage.getTotalElements();
		int pageSize = biblioPage.getSize();
		
		InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
		
		List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
		List<InlineKeyboardButton> rowInline = new ArrayList<>();
		
		rowsInline.add(rowInline);
		
		// parameters format (with order) : arrow--page--total--keyword , e.g. >--2--45--feymman
		if(page != 0) {
			rowInline.add(new InlineKeyboardButton().setText("<").setCallbackData("/p^g^^g?" + "<" + "--" + (page-1) + "--" + keyword));
		}

		for (int i = 0; i < biblios.size(); i++) {
			if(page*pageSize + (i-1) <= total) {
				String num = String.valueOf(page*pageSize + (i+1));
				rowInline.add(new InlineKeyboardButton().setText(num).setCallbackData("/biblioinfo." + biblios.get(i).getId()));
			}
		}
		
		if((page + 1)*pageSize  < total) {
			rowInline.add(new InlineKeyboardButton().setText(">").setCallbackData("/p^g^^g?" + ">" + "--" + (page+1) + "--" + keyword));
		}
		
		// Add it to the message
		markupInline.setKeyboard(rowsInline);
		
		return markupInline;
	}

	// [/search] create callback buttons -> <previous,next> 
	private void pagingCallBack(String call_data, long chat_id, long message_id) {
		
		String extractedPagingParameters = StringUtils.substringAfter(call_data,"/p^g^^g?");
		String parameters[] = extractedPagingParameters.split("--");
		
		String arrow = parameters[0];
		
		if(arrow.equals("<") || arrow.equals(">")) {

			int page = Integer.parseInt(parameters[1]);
			String keyword = parameters[2];
			
			BiblioCriteria criteria = new BiblioCriteria();			
			criteria.setKeyword1(keyword);
			
			Pageable pageable = PageRequest.of(page, pageSize);
			
			Page<Biblio> biblioPage = libraryService.findByCriteria(criteria, pageable);
			
			EditMessageText new_message = new EditMessageText()
					.setChatId(chat_id)
					.setMessageId(toIntExact(message_id));

			new_message.setText(searchView(biblioPage))
						.setParseMode(ParseMode.MARKDOWN);
			
			new_message.setReplyMarkup(createInlinePagingButtons(biblioPage,keyword));			

			try {
				execute(new_message);
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	// [/search] create callback buttons -> 1,2,3,4,5.. 
	private void biblioInfoCallBack(String call_data, long chat_id, long message_id) {
		
		String id = StringUtils.substringAfter(call_data,"/biblioinfo.");
		
		Biblio biblio = libraryService.findByBiblioId(Long.valueOf(id));
		String isbn = biblio.getIsbn();
		String imageId = biblio.getImageId();
		
		List<Item> items = libraryService.findItemsByBiblioId(Long.valueOf(id));

		boolean reservable = true;
		
		if(items.size() == 0) {
			reservable = false;
		}else {
			// if an item is found to be available for reservation, then hidden reservation button !
			for(Item item : items) {
				if(item.getState().equals(ItemState.IN_LIBRARY) && 
						item.getItemStatus().getReservable().equals(Boolean.TRUE)) {
					reservable =  false;
				}
			}
		}

		biblio.setItems(items);

		try {
			
			Photo photo = getPhoto(imageId);
			
			if(photo != null) {
				
				URL imgUrl = new URL(photo.getUrl());
				InputStream in = imgUrl.openStream();

				SendPhoto new_message = new SendPhoto()
										.setChatId(chat_id)
										.setPhoto(imageId, in)
										.setCaption(biblioView(biblio))
										.setParseMode(ParseMode.MARKDOWN);
				
				if(reservable) {
					new_message.setReplyMarkup(createInlineReserveButton(biblio.getId()));	
				}
				
				try {
					execute(new_message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ /biblioinfo : "+ isbn + " ] ");
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

				
			}else {
				
				SendMessage new_message = new SendMessage()
						.setChatId(chat_id)
						.setText(biblioView(biblio))
						.setParseMode(ParseMode.MARKDOWN);
				
				if(reservable) {
					new_message.setReplyMarkup(createInlineReserveButton(biblio.getId()));	
				}
				
				try {
					execute(new_message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ /biblioinfo : "+ isbn + " ] ");
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
			
		}catch(IOException ex) {

			logger.info("TELEGRAM LOG : " + chat_id + " - [ /biblioinfo : IOException ] ");
			
		}
	}
	
	// [/search] button 1,2,3,4,5's biblio view
	private static String biblioView(Biblio biblio) {

		List<Item> items = biblio.getItems();
		
		StringBuffer s = new StringBuffer();
		
		s.append("*" + biblio.getTitle() + "*\n");
		s.append("\n");
		s.append("*Author*\n");
		if(biblio.getAuthor() != null) {
			s.append("_" + biblio.getAuthor() + "_\n");
		}
		s.append("\n");
		s.append("*Publisher*\n");
		if(biblio.getPublisher() != null) {
			s.append("_" + biblio.getPublisher().getName() + "_\n");	
		}
		s.append("\n");
		s.append("*Status*\n");
		
		String state = "";
		
		for (int i = 0; i < items.size(); i++) {
			state = items.get(i).getState().getState();
			s.append((i+1) + ". " + state.replaceAll("_", " ") + "\n");
		}
		return s.toString();
	}
	
	private Photo getPhoto(String imageId){
		
		Photo photo = null;
		
		try {
			photo = this.photoRepository.findBiblioThumbnailByImageId(imageId);
		} catch(ConnectException cex) {
			logger.info("MINIO LOG : Connection failed !");
		} catch (Exception ex) {
			return photo;
		}
		
		return photo;
	}
	
	// [/search]
	private void search(String command, Update update) {
		
		//there must be a SPACE between the /search command and search keyword !
		
		String message = update.getMessage().getText();
		
		boolean searchCommand = message.startsWith(command);

		if(searchCommand && (message.length() > 7)){

			String keyword = message.substring(8);
			
			if(!keyword.equals("")) {
				
				BiblioCriteria criteria = new BiblioCriteria();			
				criteria.setKeyword1(keyword);
				
				int page = 0;
				
				Pageable pageable = PageRequest.of(page, pageSize);
				
				Page<Biblio> biblioPage = libraryService.findByCriteria(criteria, pageable);

				Long chat_id = update.getMessage().getChatId();	
				SendMessage new_message = new SendMessage().setChatId(chat_id);
				
				new_message.setText(searchView(biblioPage))
						.setParseMode(ParseMode.MARKDOWN);
				
				new_message.setReplyMarkup(createInlinePagingButtons(biblioPage,keyword));
				
				try {
					
					execute(new_message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ /search ] ");
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}			
			}			
		} 
	}
	
	// [/search] view
	private static String searchView(Page<Biblio> page) {

		long total = page.getTotalElements();
		int pageNum = page.getPageable().getPageNumber();
		int size = page.getPageable().getPageSize();
		List<Biblio> biblios = page.getContent();
		
		StringBuffer s = new StringBuffer();
		
		if(total < 1) {
			s.append("No book found.");
		}else {
			s.append("_Total books found : " + total + "_\n");
			s.append("-------------------------------------------------------\n");
			
			int i = 1;
			
			for(Biblio b : biblios) {
				String title = b.getTitle();
				s.append((pageNum*size + i) + ". _" + title + "_\n");
				i++;
			}
		}
		
		return s.toString(); 
	}
	
	// [/renew one by one] ***********************************************************************************
	private void renewOneByOne(String command, Update update) {
		
		if (update.getMessage().getText().equals(command)) {

			Long chat_id = update.getMessage().getChatId();
			SendMessage message = new SendMessage().setChatId(chat_id);
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);

			if (telegramUser == null) {

				message.setText(MEMBER_NOT_FOUND);

				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] " + MEMBER_NOT_FOUND);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

			} else {

				String cardKey = telegramUser.getCardKey();
				final LocalDate now = LocalDate.now();

				Patron patron = this.libraryService.preparingPatronForCirculation(cardKey, now);
				
				List<Checkout> checkouts = patron.getCheckouts();
				
				for(Checkout c : checkouts) {
					if(c.getState().equals(CheckoutState.CHECKOUT) || c.getState().equals(CheckoutState.RENEW)) {
						
						Patron p = c.getPatron();
						Item i = c.getItem();
						
						try {
							libraryService.renew(p, i, now);
						} catch (LibraryException ex) {

							logger.info("TELEGRAM LOG : " + chat_id + " - [ " + ex + " ] - renew unsuccessfull !");
							message.setText(RENEW_UNSUCCESSFULL);

							try {
								execute(message);
							} catch (TelegramApiException e) {
								e.printStackTrace();
							}
						}
					}	
				}
			}
		}
	}
	
	// [/renew] ***********************************************************************************
	private void renewAll(String command, Update update) {
		
		if(update.getMessage().getText().equals(command)){
			
			Long chat_id = update.getMessage().getChatId();	
			SendMessage message = new SendMessage().setChatId(chat_id);
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);

			if(telegramUser == null) {
				
				message.setText(MEMBER_NOT_FOUND);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] " + MEMBER_NOT_FOUND);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				
			}else {
				
				String cardKey = telegramUser.getCardKey();
				final LocalDate now = LocalDate.now();
				
				List<Checkout> successRenews =  null;
				
				Patron patron = this.libraryService.preparingPatronForCirculation(cardKey, now);
				
				try {		
					successRenews = libraryService.renewAll(patron, now);				
				}catch(LibraryException ex) {
					
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + ex + " ] ");
					message.setText(RENEW_UNSUCCESSFULLY_INVALIDATE);
					
					try {
						execute(message);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}	
				}	
				
				List<Checkout> checkouts = patron.getCheckouts();
				
				message.setText(renewsView(cardKey, checkouts, successRenews))
				   		.setParseMode(ParseMode.MARKDOWN);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] ");
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}	
			}
		}
	}
	
	// [/renew] renew view
	private static String renewsView(String cardKey, List<Checkout> checkouts, List<Checkout> renews) {
		
		List<Long> successRenewIds = new ArrayList<Long>();
		
		for(Checkout r : renews) {
			successRenewIds.add(r.getId());
		}
		
		final LocalDate now = LocalDate.now();
		
		Integer total = checkouts.size();
		
		StringBuffer s = new StringBuffer();
		
		if(total < 1) {
			s.append("You have no borrowing.");
		}else {
			
			Checkout c1 = checkouts.get(0);

			LocalDate end = c1.getPatron().getEndDate();
			
			s.append("_Member :_ *" + cardKey + "* _[ Exp : " + end + " ]_\n");
			s.append("-------------------------------------------------------------\n");
			s.append("_Total : " + total + "                 Date : " + now + "_\n");
			s.append("\n");
			
			int i = 1;
			
			for(Checkout c : checkouts) {

				String title = c.getItem().getBiblio().getTitle();
				LocalDate dueDate = c.getDueDate();
				
				if(dueDate.isBefore(now)) {
					s.append(i + ". _" + title + "_\n");
					s.append("    *Due: " + dueDate + " (o)*\n");
					
				}else {
					
					s.append(i + ". _" + title + "_\n");
					
					if(successRenewIds.contains(c.getId())) {
						s.append("    _Due: " + dueDate + "(r)_\n");
					}else {
						s.append("    _Due: " + dueDate + "_\n");
					}	
				}
				
				i++;
			}
			
			s.append("-------------------------------------------------------------\n");
			s.append("*(o) - overdue*, *(r) - renewed*");
		}

		return s.toString();
	}
	
	// [/due] ***********************************************************************************
	private void checkouts(String command, Update update) {
		
		if(update.getMessage().getText().equals(command)){
			
			Long chat_id = update.getMessage().getChatId();	
			
			SendMessage message = new SendMessage().setChatId(chat_id);
			TelegramUser telegramUser = telegramService.findTelegramUserByChatId(chat_id);

			if(telegramUser == null) {
				
				message.setText(MEMBER_NOT_FOUND);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] " + MEMBER_NOT_FOUND);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				
			}else {
				
				String cardKey = telegramUser.getCardKey();
	
				List<Checkout> checkouts = libraryService.findAllActiveCheckoutsByCardKey(cardKey);
				
				message.setText(checkoutsView(cardKey, checkouts))
					   .setParseMode(ParseMode.MARKDOWN);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] ");
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// [/due] checkout view
	private static String checkoutsView(String cardKey, List<Checkout> checkouts) {
		
		final LocalDate now = LocalDate.now();
		
		Integer total = checkouts.size();
		
		StringBuffer s = new StringBuffer();
		
		if(total < 1) {
			s.append("You have no borrowing.");
		}else {
			
			Checkout c1 = checkouts.get(0);

			LocalDate end = c1.getPatron().getEndDate();
			
			s.append("_Member :_ *" + cardKey + "* _[ Exp : " + end + " ]_\n");
			s.append("-------------------------------------------------------------\n");
			s.append("_Total : " + total + "                 Date : " + now + "_\n");
			s.append("\n");
			
			int i = 1;
			
			for(Checkout c : checkouts) {

				String title = c.getItem().getBiblio().getTitle();
				LocalDate dueDate = c.getDueDate();
				
				if(dueDate.isBefore(now)) {
					s.append(i + ". _" + title + "_\n");
					s.append("    *Due: " + dueDate + " (o)*\n");
					
				}else {
					s.append(i + ". _" + title + "_\n");
					s.append("    _Due: " + dueDate + "_\n");
				}
				
				i++;
			}
			
			s.append("-------------------------------------------------------------\n");
			s.append("*(o) - overdue*");
		}
		
		return s.toString();
	}
	
	// [reminder] ********************************************************************************
	
	/*
	 * @Scheduled(cron = "[Seconds] [Minutes] [Hours] [Day of month] [Month] [Day of week] [Year]")
	 * 
	 * Fires at 12 PM every day : 						@Scheduled(cron = "0 0 12 * * ?") 
	 * Fires at 10:15AM every day in the year 2005 : 	@Scheduled(cron = "0 15 10 * * ? 2005") 
	 * Fires every 20 seconds : 						@Scheduled(cron = "0/20 * * * * ?") 
	 */
	
	@Scheduled(cron = "0 0 10 * * ?")
	public void reminder() {
		
		final LocalDate now = LocalDate.now();
		
		List<String> list = libraryService.allOverDuePatrons(now, reminderDays);
		
		Set<String> cardKeys = new LinkedHashSet<String>(list); 
		
		for(String cardKey : cardKeys) {
			
			TelegramUser telegramUser = telegramService.findTelegramUserByCardKey(cardKey);

			if(telegramUser != null && telegramUser.getPreference().getReminder().equals(YesNo.Y)) {
				List<Checkout> checkouts = libraryService.patronOverDues(cardKey, now, reminderDays);
				sendMessage(telegramUser.getChatId(), cardKey, now, checkouts);
			}		
		}	
	}
	
	// [reminder - send msg]
	private void sendMessage(Long chat_id, String cardKey, LocalDate given, List<Checkout> checkouts) {

		SendMessage message = new SendMessage().setChatId(chat_id);
		
		message.setText(overdueView(cardKey, checkouts))
				.setParseMode(ParseMode.MARKDOWN);

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}
	
	// [reminder - overdue view]
	private static String overdueView(String cardKey, List<Checkout> checkouts) {
		
		final LocalDate now = LocalDate.now();
		
		Integer total = checkouts.size();
		
		StringBuffer s = new StringBuffer();	
		
		if(total < 1) {
			s.append("You have no borrowing.");
		}else {
			
			Checkout c1 = checkouts.get(0);

			LocalDate end = c1.getPatron().getEndDate();
			
			s.append("Reminder : please /renew to prevent overdue.\n");
			s.append("-------------------------------------------------------------\n");
			s.append("_Member :_ *" + cardKey + "* _[ Exp : " + end + " ]_\n");
			s.append("-------------------------------------------------------------\n");
			s.append("_Total : " + total + "                 Date : " + now + "_\n");
			s.append("\n");
			
			int i = 1;
			
			for(Checkout c : checkouts) {

				String title = c.getItem().getBiblio().getTitle();
				LocalDate dueDate = c.getDueDate();
				
				if(dueDate.isBefore(now)) {
					s.append(i + ". _" + title + "_\n");
					s.append("    *Due: " + dueDate + " (o)*\n");
					
				}else {
					s.append(i + ". _" + title + "_\n");
					s.append("    _Due: " + dueDate + "_\n");
				}
				
				i++;
			}
			
			s.append("-------------------------------------------------------------\n");
			s.append("*(o) - overdue*");
		}
		
		return s.toString();
	}
	
	
	// [/help] ***********************************************************************************
	private void sendMessage(String command, Update update, String response) {

		if(update.getMessage().getText().equals(command)){
			
			Long chat_id = update.getMessage().getChatId();		
			SendMessage message = new SendMessage()
					.setChatId(chat_id)
					.setText(response)
					.setParseMode(ParseMode.MARKDOWN);
			
			try {
				execute(message);
				logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] ");
			} catch (TelegramApiException e) {
				e.printStackTrace();
			}
		}
	}
	
	// [/register]
	private void registerMessage(String command, Update update, String response) {

		if (update.getMessage().getText().equals(command)) {

			Long chat_id = update.getMessage().getChatId();
			SendMessage message = new SendMessage().setChatId(chat_id);
			boolean exist = telegramService.isTelegramUserExist(chat_id);

			if (exist) {

				message.setText(ALREADY_REGISTERED);

				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] " + ALREADY_REGISTERED);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}

			} else {

				message.setText(response).setParseMode(ParseMode.MARKDOWN);

				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [ " + command + " ] ");
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	// registration verification
	private void registrationVerification(Update update) {
		
		Long chat_id = update.getMessage().getChatId();

		String msg = update.getMessage().getText();
		String[] words = msg.split("#");

		// registration
		if (words.length == 3 && (msg.startsWith("reg#") || msg.startsWith("Reg#"))) {

			SendMessage message = new SendMessage().setChatId(chat_id);
			
			String cardKey = words[1];
			String mobile = words[2];
			
			// validation
			if(mobile.length() > 11) {
				
				message.setText(MOBILE_LENGTH_ERROR);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [  ] " + MOBILE_LENGTH_ERROR);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
				
			}else {
				
				try {
					
					Integer.valueOf(mobile);
					
				}catch(NumberFormatException nfex) {
					
					message.setText(MOBILE_NOT_NUMBER_ERROR);
					
					try {
						execute(message);
						logger.info("TELEGRAM LOG : " + chat_id + " - [  ] " + MOBILE_NOT_NUMBER_ERROR);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}			
				}				
			}
			
			boolean exist = libraryService.match(cardKey, mobile);		
			TelegramUser telegramUser = new TelegramUser(chat_id, cardKey, new Preference(YesNo.Y,YesNo.N,YesNo.N,YesNo.Y,YesNo.N,YesNo.N));
					
			if(exist) {
				
				try {
					
					telegramService.save(telegramUser);
					message.setText(VERIFICATION_SUCCESS);
					
					try {
						execute(message);
						logger.info("TELEGRAM LOG : " + chat_id + " - [  ] " + VERIFICATION_SUCCESS);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}
					
				} catch (DataIntegrityViolationException eive) {	
					
					message.setText(ALREADY_REGISTERED);	
					
					try {
						execute(message);
						logger.info("TELEGRAM LOG : " + chat_id + " - [  ] " + ALREADY_REGISTERED);
					} catch (TelegramApiException e) {
						e.printStackTrace();
					}			
				}
				
			}else {
				
				message.setText(REGISTRATION_FAILED_MESSAGE).setParseMode(ParseMode.MARKDOWN);
				
				try {
					execute(message);
					logger.info("TELEGRAM LOG : " + chat_id + " - [  ] " + REGISTRATION_FAILED);
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}	
			}
		}	
	}
	
	// [/reminder (expiring membership)] ***********************************************************************************
	@Scheduled(cron = "0 30 10 * * ?")
	public void expiringMembershipsReminder() {
		
		final int firstRemind = 14;
		final int secondRemind = 7;
		
		final LocalDate now = LocalDate.now();
		
		List<Patron> patrons = libraryService.expiringMembershipPatrons(now, firstRemind, secondRemind);
		
		for(Patron p : patrons) {
			
			String cardKey = p.getCardKey();
			LocalDate endDate = p.getEndDate();
			
			TelegramUser telegramUser = telegramService.findTelegramUserByCardKey(cardKey);

			if(telegramUser != null) {
				sendMessage(telegramUser.getChatId(), cardKey, now, endDate);
			}		
		}	
	}
	
	// [/reminder (expiring membership) - send msg]
	private void sendMessage(Long chat_id, String cardKey, LocalDate given, LocalDate endDate) {

		SendMessage message = new SendMessage().setChatId(chat_id);
		
		message.setText(expiringMembershipsView(cardKey, given, endDate))
				.setParseMode(ParseMode.MARKDOWN);

		try {
			execute(message);
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}

	}
	
	// [/reminder (expiring membership) - overdue view]
	private static String expiringMembershipsView(String cardKey, LocalDate given, LocalDate endDate) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd"); 
	    String expireDate = formatter.format(endDate); 
	        
		StringBuffer s = new StringBuffer();

		s.append("======================================= \n");
		s.append("This is a reminder that your membership \n");
		s.append("with member id. *[" + cardKey + "]* \n");
		s.append("will be expired on *[" + expireDate + "]* \n");
		s.append("======================================= \n");

		return s.toString();
		
	}
	
	@Override
	public String getBotUsername() {
		return username;
	}

	@Override
	public String getBotToken() {
		return token;
	}

}
