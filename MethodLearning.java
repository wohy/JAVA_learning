import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MethodLearning {
    public static void main(String[] args) {
        PersonInfo[] personArray = new PersonInfo[11];
        PersonInfo personInfoOne = new PersonInfo("小明", 10);
        PersonInfo personInfoTwo = new PersonInfo("小红", 11);
        PersonInfo personInfoThree = new PersonInfo("小赵", 12);
        PersonInfo personInfoFour = new PersonInfo("小钱", 13);
        PersonInfo personInfoFive = new PersonInfo("小李", 12);
        PersonInfo personInfoSix = new PersonInfo("小周", 15);
        PersonInfo personInfoSeven = new PersonInfo("小吴", 16);
        PersonInfo personInfoEight = new PersonInfo("小郑", 12);
        PersonInfo personInfoNine = new PersonInfo("小王", 18);
        PersonInfo personInfoTen = new PersonInfo("小胡", 12);
        personArray[0] = personInfoOne;
        personArray[1] = personInfoTwo;
        personArray[2] = personInfoThree;
        personArray[3] = personInfoFour;
        personArray[4] = personInfoFive;
        personArray[5] = personInfoSix;
        personArray[6] = personInfoSeven;
        personArray[7] = personInfoEight;
        PersonInfos persons = new PersonInfos();
        persons.addPerson(personArray);
        persons.addPerson(new PersonInfo[]{personInfoNine, personInfoTen});
        persons.getPersonByName("小赵");
        persons.getPersonByName("小胡");
        persons.getPersonByAge(12);
        persons.getAnyAgePersonNums(12);
        // persons.getAllPersons();
    }
}

class PersonInfo {
    public String name;
    public int age;
    // 构造函数
    public PersonInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }
    @Override
    public String toString() {
        return "PersonInfo [name=" + name + ", age=" + age + "]";
    }
}

class PersonInfos {
    private final List<PersonInfo> allPerson;

    PersonInfos() {
        this.allPerson = new ArrayList<>();
    }

    public void getPersonByName(String name) {
        for (PersonInfo person : allPerson) {
            if (person != null && person.name.equals(name)) {
                System.out.println(person.age);
            }
        }
    }

    public void getPersonByAge(int age) {
        for (PersonInfo person : allPerson) {
            if (person != null && person.age == age) {
                System.out.println(person.name);
            }
        }
    }

    public void getAnyAgePersonNums(int age) {
        /*
            int nums = 0;
            for (PersonInfo person : allPerson) {
                if (person != null && person.age == age) {
                    nums++;
                }
            }
        */
        long nums = allPerson.stream().filter(item -> item != null && item.age == age).count();
        System.out.println("There are "+ nums + " people aged " + age + " years old");
    }

    public void getAllPersons() {
        for (PersonInfo person : allPerson) {
            System.out.println(person);
        }
    }

    public void addPerson(PersonInfo[] person) {
        allPerson.addAll(Arrays.asList(person));
    }
}