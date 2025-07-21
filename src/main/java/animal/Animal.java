package animal;

public abstract class Animal {
    private long id;
    protected String name;
    protected int age;
    private double weight;
    private String color;
    protected String type;

    public Animal(long id, String name, int age, double weight, String color, String type) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.color = color;
    }
public long getId(){
        return id;
}
public void setId(long id){
        this.id = id;
}


    public String getType() {
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = (int) weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void say() {
        System.out.println("Я говорю");
    }

    public void go() {
        System.out.println("Я иду");
    }

    public void drink() {
        System.out.println("Я пью");
    }

    public void eat() {
        System.out.println("Я ем");
    }



    @Override
    public String toString() {
        String yearsForm = getYearsForm(age);
        return "Привет! Меня зовут " + name +
                ", мне " + age + " " + yearsForm +
                ", я вешу - " + weight + " кг, мой цвет - " + color;
    }


    // Выбор правильной формы слова "лет"
    private static String getYearsForm(int number) {
        if (number % 10 == 1 && number != 11) return "год";
        else if ((number % 10 >= 2 && number % 10 <= 4) &&
                !(number > 10 && number < 15)) return "года";
        else return "лет";
    }
}


