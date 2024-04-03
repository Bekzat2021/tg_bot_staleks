package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Bot extends TelegramLongPollingBot {
    private final String botName;
    private final String botToken;
    private boolean brak=false;
    private boolean standard=false;

    public Bot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    private String selectedDept;

    private Long user_id;

    private boolean firstNameMode=false;
    private boolean lastNameMode=false;
    private InlineKeyboardMarkup deptSelectKb(){
        InlineKeyboardMarkup chooseDepartment=new InlineKeyboardMarkup();
        InlineKeyboardButton metalBtn=new InlineKeyboardButton();
        InlineKeyboardButton mdfBtn=new InlineKeyboardButton();
        InlineKeyboardButton assemblyBtn=new InlineKeyboardButton();

        metalBtn.setText("Металл");
        metalBtn.setCallbackData("metal_dept");

        mdfBtn.setText("МДФ");
        mdfBtn.setCallbackData("mdf_dept");

        assemblyBtn.setText("Сборка");
        assemblyBtn.setCallbackData("assembly_dept");

        List<InlineKeyboardButton> kbBtnRow1=new ArrayList<>();
        kbBtnRow1.add(metalBtn);
        kbBtnRow1.add(mdfBtn);
        kbBtnRow1.add(assemblyBtn);

        List<List<InlineKeyboardButton>> rowList=new ArrayList<>();
        rowList.add(kbBtnRow1);

        chooseDepartment.setKeyboard(rowList);

        return chooseDepartment;
    }

    private InlineKeyboardMarkup infoSelectKb(String dept){
        InlineKeyboardMarkup chooseInfo=new InlineKeyboardMarkup();
        InlineKeyboardButton standBtn = InlineKeyboardButton
                    .builder().text("Стандарт").callbackData("standard")
                    .build();
        InlineKeyboardButton brakBtn=InlineKeyboardButton
                    .builder().text("Брак").callbackData("brak")
                    .build();

        List<InlineKeyboardButton> kBtn1=new ArrayList<>();

        kBtn1.add(standBtn);
        kBtn1.add(brakBtn);

        List<List<InlineKeyboardButton>> rowList=new ArrayList<>();
        rowList.add(kBtn1);
        chooseInfo.setKeyboard(rowList);

        return chooseInfo;
    }

    private String selectDeptMenu(Update update){
        if (update.hasCallbackQuery()) {
            String checkMetal=update.getCallbackQuery().getData();

            if (Objects.equals(checkMetal, "metal_dept")){
                return "metal_dept";
            } else if (update.getCallbackQuery().getData().equals("mdf_dept")) {
                return "mdf_dept";
            } else if (update.getCallbackQuery().getData().equals("assembly_dept")) {
                return "assembly_dept";
            }

        }
        return null;
    }

    private boolean isRegistred(Update update){
        Long user_id = update.getMessage().getFrom().getId();

        if (db_connection.isUserRegistred(user_id))
            return true;

        return false;
    }

    public void savePhoto(Update update, boolean brak, boolean standard){
        List<PhotoSize> photos= update.getMessage().getPhoto();

        String fileId=photos.stream().sorted(Comparator.comparing(PhotoSize::getFileSize)
                .reversed()).findFirst().orElse(null).getFileId();

        try{
            GetFile getFile = new GetFile(fileId);
            File file = execute(getFile);
            LocalDateTime localDateTime=LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss");
            var t1=downloadFile(file, new java.io.File("src/main/resources/photos"+
                    update.getMessage().getFrom().getId()+localDateTime.format(dateTimeFormatter)+ ".png"));
            db_connection.insertPhoto(db_connection.getUserId(update.getMessage().getFrom().getId()),
                    update.getMessage().getFrom().getId()+localDateTime.format(dateTimeFormatter)+ ".png",
                    "src/main/resources/photos/", standard);
        }catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void sendMessage(Long chatId, String message){
        SendMessage sm=new SendMessage();

        sm.setChatId(chatId);
        sm.setText(message);

        try{
            execute(sm);
        }catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }
    }

    private void sendMessage(Long chatId, String message, InlineKeyboardMarkup kb){
        SendMessage sm=new SendMessage();

        sm.setChatId(chatId);
        sm.setText(message);
        sm.setReplyMarkup(kb);

        try{
            execute(sm);
        }catch (TelegramApiException exception){
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update){
        if (update.hasCallbackQuery()){
            selectedDept=selectDeptMenu(update);

            if (selectedDept!=null){
                sendMessage(update.getCallbackQuery().getMessage().getChatId(), "Отдел выбран. Введите имя: ");
                firstNameMode=true;
            }//else{
             //   sendMessage(update.getCallbackQuery().getMessage().getChatId(), "Ошибка отдел не выбран. ");
            //}

            if (Objects.equals(update.getCallbackQuery().getData(), "standard")){
                standard=true;
                sendMessage(update.getCallbackQuery().getMessage().getChatId(), "Отправьте фото стандарта: ");
            } else if (Objects.equals(update.getCallbackQuery().getData(), "brak")) {
                brak=true;
                sendMessage(update.getCallbackQuery().getMessage().getChatId(), "Отправьте фото брака: ");
            }
        }

        if (lastNameMode && update.hasMessage()) {
            User.setLastName(update.getMessage().getText());
            lastNameMode=false;
            user_id=update.getMessage().getFrom().getId();
            if (db_connection.insertUser(User.getFirstName(), User.getLastName(), selectedDept, user_id)){
                sendMessage(update.getMessage().getChatId(), "Вы успешно зарегистрировались!");
            }
        }

        if (firstNameMode && update.hasMessage()) {
            User.setFirstName(update.getMessage().getText());
            firstNameMode=false;
            sendMessage(update.getMessage().getChatId(), "Имя введено. Введите фамилию: ");
            lastNameMode=true;
        }

        if (update.hasMessage() && update.getMessage().isCommand()
                && !isRegistred(update) && update.getMessage().getText().equals("/start")){
            sendMessage(update.getMessage().getChatId(), "Вы не зарегистрированы. Выбирете отдел: ", deptSelectKb());

        } else if(update.hasMessage() && brak) {
            if (update.getMessage().hasPhoto()) {
                savePhoto(update, brak, standard);
                brak = false;
                standard = false;
                sendMessage(update.getMessage().getChatId(), "Фото брака успешно сохранено.");
            } else {
                sendMessage(update.getMessage().getChatId(), "Отправьте фото брака");
                brak = true;
                standard = false;
            }
        }else if (update.hasMessage() && standard){
            if (update.getMessage().hasPhoto()){
                savePhoto(update, brak, standard);
                brak=false;
                standard=false;
                sendMessage(update.getMessage().getChatId(), "Фото стандарта успешно сохранено.");
            }else {
                sendMessage(update.getMessage().getChatId(), "Отправьте фото стандарта");
                brak = false;
                standard = true;
            }
        }else if (isRegistred(update)) {
            sendMessage(update.getMessage().getChatId(), "Что вы хотите ввести?",
                    infoSelectKb(db_connection.userDept(update.getMessage().getFrom().getId())));

        }
    }

    @Override
    public String getBotUsername(){
        return botName;
    }

    @Override
    public String getBotToken(){
        return botToken;
    }
}
