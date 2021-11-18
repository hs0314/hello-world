package java8;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class OptionalMain {
    public static void main(String[] args){
        List<Person> personList = new ArrayList<>();


        personList.add(getNewPerson("i1"));
        personList.add(getNewPerson("i2"));
        personList.add(getNewPerson("i3"));
        personList.add(getNewPerson(null));


        //String name = getCarInsuranceName(Optional.of(p));
        Set<String> nameSet = getCarInsuranceNameSet(personList);
        //System.out.println(name);

    }

    public static Person getNewPerson(String insuranceName){
        Person p = new Person();
        Car c = new Car();
        Insurance i = new Insurance(insuranceName);

        c.setInsurance(Optional.of(i));
        p.setCar(Optional.of(c));

        return p;
    }

    public static String getCarInsuranceName(Optional<Person> person){
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown"); // 결과 Optional 이 비었으면 기본값 사용
    }

    public static Set<String> getCarInsuranceNameSet(List<Person> personList){
        return personList.stream()
                .map(Person::getCar)
                .map(optCar -> optCar.flatMap(Car::getInsurance))
                .map(optIns -> optIns.map(Insurance::getName))
                .flatMap(Optional::stream) // 이전단계까지의 결과물인 Stream<Optional<String>>에서 빈 Optional은 제외하고 실제 값이 있는 Optional만 가져오기 위해서 Optional::stream 메서드 활용
                .collect(toSet());
    }

}

class Person{
    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }
}
class Car{
    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }
}
class Insurance{
    private String name; // 필수로 값이 들어있어야하므로 Optional로 감싸지 않는다

    public Insurance(String _name){
        name = _name;
    }

    public String getName(){
        return name;
    }
}
