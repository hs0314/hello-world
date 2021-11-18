package pattern;

import java.util.ArrayList;
import java.util.List;

public class StrategyMain {

    public static void main(String[] args){
        Customer firstCustomer = new Customer(new NormalStrategy());

        // Normal billing
        firstCustomer.Add(1.0, 1);

        // Start Happy Hour
        firstCustomer.strategy = new HappyHourStrategy();
        firstCustomer.Add(1.0, 2);

        // New Customer
        Customer secondCustomer = new Customer(new HappyHourStrategy());
        secondCustomer.Add(0.8, 1);
        // The Customer pays
        firstCustomer.PrintBill();

        // End Happy Hour
        secondCustomer.strategy = new NormalStrategy();
        secondCustomer.Add(1.3, 2);
        secondCustomer.Add(2.5, 1);
        secondCustomer.PrintBill();
    }
}

class Customer{
    private List<Double> drinks;
    public BillingStrategy strategy;

    public Customer(BillingStrategy strategy){
        this.drinks = new ArrayList<>();
        this.strategy = strategy;
    }

    // 전략 패턴 사용
    public void Add(double price, int quantity){
        drinks.add(strategy.GetActPrice(price * quantity));
    }

    // Payment of bill
    public void PrintBill(){
        double sum = 0;
        for(Double i : drinks){
            sum += i;
        }
        System.out.println(("Total due: " + sum));
        drinks.clear();
    }
}

// Strategy 인터페이스
interface BillingStrategy{
    double GetActPrice(double rawPrice);
}

// 전략1 : Normal billing strategy (unchanged price)
class NormalStrategy implements BillingStrategy{
    public double GetActPrice(double rawPrice){
        return rawPrice;
    }
}

// 전략2 : Strategy for Happy hour (50% discount)
class HappyHourStrategy implements BillingStrategy{
    public double GetActPrice(double rawPrice){
        return rawPrice * 0.5;
    }
}