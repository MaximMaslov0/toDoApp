package edu.practice.presentation.io;

public abstract class Output {
    public static final String WELCOME_TEXT = """




            Вітаємо Вас вперше в програмі ToDoApp!!!
            Оскільки Ви запустили програму, це значить, що Ви прочитали файл README.md
            Якщо ж Ви цього не зробили, то обов'язково детально ознайомтесь з цим файлом
            ...
            Для початку роботи, давайте створимо перший список завдань!
            Щоб зробити це, введіть команду /addTaskList
            Інші команди можна дізнатися також за допомогою файла README.md
                            
            """, INCORRECT_COMMAND_MESSAGE = "\nВи ввели невірну команду!\n",
            EXIT_MESSAGE = "\nВи вийшли із програми!\n";
}
