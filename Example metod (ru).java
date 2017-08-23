Использование dragAndDropTo для перемещения блока
(Живой пример)
......................................................................
open("https://jqueryui.com/resources/demos/draggable/snap-to.html");  
Thread.sleep(1000);
$(".draggable.ui-widget-content.ui-draggable.ui-draggable-handle").
dragAndDropTo($("#snaptarget")); 
Thread.sleep(1000);
......................................................................
Пример по переходу через iframe:

open("https://support.norse.digital/secure/Dashboard.jspa");
switchTo().innerFrame("gadget-0"); // где (“”) - это id  iframe
$(By.name("os_username")).setautomationValue("ваше имя").click();
$(By.id("login-form-password")).setValue("ваш пароль").pressEnter();

https://github.com/codeborne/selenide/blob/master/src/test/java/integration/FramesTest.java // большой пример
......................................................................
Перед началом теста чистим Кэши

@Before
    public void clearCache() {
        clearBrowserCache();
    }
......................................................................
Если необходимо найти текст или любой локатор:

(Он будет искаться пока не найдется, относительно заданного количества поиска)

@Test
public void setup () throws InterruptedException {
   open("https://www.google.ru");
   $(By.name("q")).val("Selenide(воодим слово в поиск гугл)").pressEnter();
   int pagesVisited = 0;
   SelenideElement element = $(byText("Selenide - Wikipedia(слово кот ищем)"));
   while (++pagesVisited < 10 (кол раз кот будет нажиматься)&& !element.exists()){
$(By.id("pnnext(след страница)")).click(); Thread.sleep(700);
   }
   element.click();
}
......................................................................
Перейти в новое окно браузера, которое откроется после нажатия кнопки

open("https://www.google.ru");
$(By.name("q")).val("Selenide").pressEnter();Thread.sleep(700);
$(byText("Selenide: лаконичные и стабильные UI тесты на Java")).pressEnter(); 
switchTo().window(1); //- “1” это номер вкладки, кот начинается с “0”
$$(".ql").get(1).click();Thread.sleep(500);
......................................................................
Переключение между окнами в браузере

switchTo().window(title);
switchTo().window(index);
switchTo().frame("topFrame");
switchTo().innerFrame("parentFrame");
......................................................................
Открытие любой ссылки в Новой Вкладке:С помощью Robot

open("https://ru.stackoverflow.com/");
Robot robot = new Robot();
$(byText("locator")).contextClick();  Thread.sleep(500);//жмем правой клав мыши
robot.keyPress(KeyEvent.VK_ENTER);	  Thread.sleep(700);
robot.keyPress(KeyEvent.VK_ESCAPE);   Thread.sleep(700);
switchTo().window(1);
$(By.id("nav-questions")).click();    Thread.sleep(700);
-------
С помощью actions()

actions().sendKeys(Keys.CONTROL,"t").build().perform(); 	Thread.sleep(500);
$(byText("locator")).click(); 					                  Thread.sleep(500);
switchTo().window(1);Thread.sleep(1500); switchTo().window(1).close();  // перейти и закрыть вкладку 
......................................................................
Если необходимо сделать цикл:

for ( int i=0; i<10; i++) {
 $$("...").get(0).click();
 $("...").val(“...“)click();
}
......................................................................
Скачать “href=...” файл

File cv = $("#popup-download-button").download(); or
File report = $("input#submit").download();
......................................................................
Закачать фаил "uploadFile"

open("http://document.online-convert.com/ru/convert-to-pdf");  
        $(by("name", "file")).uploadFile(new File("C:\\Users\\User\\Downloads\\lNV7I8VcRNM.jpg")); 
        $(by("id","submit_button")).scrollTo().doubleClick(); Thread.sleep(10000);
......................................................................    
Сделать скриншот отдельного элемента  

File screenshot = $("#popup-download-button").screenshot();
......................................................................
Некоторые полезные функции:

- .clear(); 					            // очистить текстовое поле
-$$(".no-underline").get(25).scrollTo(); 	// - сделать скролл до локатора
-$(by("title", "Логотип Acer")).isImage();  // - подтвердить, что локатор - картинка
-zoom(2.5); 					            // - Увеличить в 2.5 раза
-Configuration.browser = "chrome"; 	        // - Задаем размер браузера, ставим над “open”
        Configuration.browserSize = "1500x1200";
-Configuration.savePageSource = false;      // - При падении теста отключаем создание html файла
-actions().clickAndHold($(By.id("logo")))   // - кликает и зажимает
-$("div").hover(); 				            // - Навести курсор на локатор
-navigator.back(); (или back();)			// - Вернуться на предыдущую страницу 
-$(By.id("myParagraph")).should(textCaseSensitive("My text")) // где textCaseSensitive Условие, указывающее, что элемент HTML имеет определенное значение или текст с учетом прописных и строчных букв.
-$("#results").shouldHave(text("Selenide.org"));             // Само подождёт, пока у элемента появится нужный текст
......................................................................
Если необходимо ввести пароль в Basic Auth 

- Selenide.open("http://httpbin.org/basic-auth/user/passwd", "", "user", "passwd");
......................................................................
Копировать, Вставить(не все “горячие” клавиши работают)

To select all (Ctrl-A):
 open("http://google.com");
 $("#lst-ib").val("qwertyuiop");
 $("#lst-ib").sendKeys(Keys.chord(Keys.CONTROL, "a"));
----------
To copy (Ctrl-C):
 open("http://google.com");
 $("#lst-ib").val("qwertyuiop");
 $("#lst-ib").sendKeys(Keys.chord(Keys.CONTROL, "a"));
 $("#lst-ib").sendKeys(Keys.chord(Keys.CONTROL, "c"));
----------
To cut (Ctrl-X):
 open("http://google.com");
 $("#lst-ib").val("qwertyuiop");
 $("#lst-ib").sendKeys(Keys.chord(Keys.CONTROL, "a"));
 $("#lst-ib").sendKeys(Keys.chord(Keys.CONTROL, "x"));
----------
To paste (Ctrl-V):
 $("#lst-ib").sendKeys(Keys.chord(Keys.CONTROL, "v"));
......................................................................
Поддержка множественных селектов
Бывают такие выпадающие списки, в которых можно выбрать несколько опций (<select multiple>). В Selenide можно тыкать и в такие. Несколько опций можно выбрать всего одной командой:

select.selectOption("Маргарита", "Theodor Woland"); 	// по тексту
select.selectOption(0, 2, 3);                        	// по индексу
select.selectOptionByValue("cat", "woland");         	// по значению
Метод для получения списка всех выбранных опций:
select.getSelectedOptions().shouldHave(texts("Маргарита", "Theodor Woland"));
----------
Можно написать нормальное условие для проверки чекбокса:
 $("#i-agree").shouldBe(checked);
----------
Метод byAttribute (или короче by)
$(by("first-name", "john macclane").click();
----------
Можно искать атрибут по подстроке:
$(by("first-name*", "hn maccl").click();
----------
По началу атрибута:
$(by("first-name^", "joh").click();
----------
По концу атрибута:
$(by("first-name$", "clane").click();
----------
По вхождению слова в атрибут:
$(by("first-name~", "john").click();
......................................................................
Методы поиска внутренних элементов:

find(String cssSelector) / $(String cssSelector)
find(By) / $(By)
findAll(String cssSelector) / $$(String cssSelector)
findAll(By) / $$(By)
$("#header").find("#menu").findAll(".item")
......................................................................
Если необходимо кликнуть по координатам:

 actions()
                .moveToElement($(by("name", "q")))
                .moveByOffset(0,0)			//координаты относительно от локатора
                .сlick()					//(0,0)(пиксели по горизонтали, по вертикали)
                .build().perform();
......................................................................
Собрать все ссылки на странице

List<String> links = new ArrayList<String>();
        for (SelenideElement link : $$("a"))links.add(link.attr("href"));Thread.sleep(1000);
----------
Собрать ссылки и вывести в консоль результат

open("https://www.google.com.ua/");
      $(By.name("q")).val("Selenide").pressEnter();
        ElementsCollection link = $$(By.tagName("a")); //или $$("div.g>div>div.rc>h3.r>a") или $$("#ires .g .r a");// идем к дочернему “а” чтобы показать только найденный список "Selenide"
            for (int i = 0; i<15; i++) {
            System.out.println(link.get(i).getAttribute("text"));
            System.out.println(link.get(i).getAttribute("href"));
 System.out.println(link.get(i).getAttribute("hreflang"));          //делает пустую строку
        }
----------
Собирает все ссылки и кликает по всем результам

open("https://www.google.com.ua/");
      $(By.name("q")).val("Selenide").pressEnter();
for (int i = 0; i < 5; i++) {
            SelenideElement link = $("#ires .g", i).find("a");
            System.out.println(link.attr("href"));
            System.out.println(link.attr("text"));
            link.click();
            back();
        }
......................................................................
При “Падении” Теста, Будет Тестить Дальше
(недоделанная)
public static boolean holdBrowserOpen = Boolean.getBoolean("selenide.holdBrowserOpen"); // Dselenide property “-Dselenide.holdBrowserOpen=true“
......................................................................
Если необходимо узнать ширину и высоту Элемента:

ElementsCollection link = $$(By.name("q"));
        for (int i = 0; i<1; i++) {
        System.out.println("Width element = "+ link.get(i).getSize().width);	
        System.out.println("Height element = "+link.get(i).getSize().height);
        }
......................................................................

