
public	Available to all.
private	Available to only members of the same class.
(None)	Known as default or package visibility. Other members of the same package can access it, but members in other packages cannot.
protected	Avaiable to members of the same package and any derived classes.
A property or method can have any of the modifers, but a class can only have public or none (default).
Now with the different constructors and accessor methoded defined, using the Car class would look something like this:

Car sporty = new Car("Red", 2);
sporty.start();
sporty.drive();

Car family = new Car();
family.setColor("Gold");
family.setNumberOfDoors(4);
family.start();
family.drive();
Before the class is expanded again some conventions will be explained. Note that these are only conventions, not actual language rules, and an organization's policies or coding standards my differ. They exist mainly for making a class more readable and maintainable.
First - the arrangement of the class. Member variables and constants (which we will see shortly) appear at the beginning of the file. These are generally grouped according to purpose (member or constant) and within each of those groupings, by visibility modifier.
Next - methods. The order of these can sometimes be different, but these are also grouped together by purpose and visibility. In the Car class above, the accessor methods appear in pairs immediately after the member variables. This proximity can be advantageous. Following the accessors, the constructors are grouped together, followed by the public methods of the class. If there were any private methods or static methods, these would also be grouped together.
Again, there is no compiler rule that enforces this arrangment or grouping. In some cases the private members are placed together at the bottom of the file with the logic being that they are not public and are changed or updated far less than their public accessor methods, which would be at the top of the file. Unlike a function, where a variable must be declared before it is used, the compiler sees the class in a holistic manner and class members, whether functions or variables, can theoretically appear anywhere in the class.

Private vs. Public methods
In the simple Car class there are no private methods, only private member variables. So why would there be methods also declared as private? Again, this goes a little bit into object-oriented programming, but the public methods of a class should provide the user of the class the ability to interact and/or modify the object in predictible ways. And if there are operations that the object should only perform on itself, those should be declared private.

One example, as has been mentioned before, is a read-only property. Assume the car class has expanded to not only drive, but to have speed property and both the get and set accessors. Also, there is a currentRPM that represents the speed of the engine, which is calculated from the speed of the car (a public property) and some internal factos such as gears, weight, and size of engine. The designer of the Car class would most likely want this to be a read-only property implemented as follows:

private int currentRPM;
private int speed;

public int getCurrentRPM()
{
    return currentRPM;
}

private void setCurrentRPM(int rpm)
{
    currentRpm = rpm;
}

public void setSpeed(int speed)
{
    this.speed = speed;
    setCurrentRPM(calculateRPM(speed));
}
In this case, the RPM is set indirectly when the speed is set, but the implementation is hidden from the user who sets the speed. Incidently, this also demonstrates the use of a private method. The calculateRPM() method would also be declared as private so external users would not have access to it.

private int calculateRPM(int speed)
{
    int rpm = 0;

    // represents a real rpm calcuation
    rpm = speed * getBestGear(speed) * engineFactor();

    return rpm;

}
Overloading
Earlier the term 'overloading' was mentioned in the context of constructors and basically was defined as the same method with different pararmeter lists. This principal can also be applied to non-constructor methods. It is used quite extensively in the JDK itself and exists mainly to give the user of the class options when manipulating an object. If the Car class grew to include more functionality, there could be several overloads of the drive() method. An example of this is shown below

// Just start driving
public void drive()
{
    System.out.println("Car driving");
}

// Drive for the specified number of miles
public void drive(int miles)
{
    System.out.println("Driving for " + miles + " miles.");
}

// Drive specified miles at the specified speed
public void drive(int miles, int speed)
{
    System.out.println("Driving " + speed + "mph for " + miles + " miles.");
}

// drive to the specified destination
public void drive(String destination)
{
    System.out.println("Driving to " + destination);
}
Again, the purpose of overloaded methods is to provide the user of the class with multiple options when implementing similar ideas. The alternative would be to have methods with slightly different names - drive, driveForDistance, driveDistanceAtSpeed, and driveToDestination. While this could work and may in some cases be more descriptive, the concept of overloading remains a powerful way to present a common idea in a concise manner.
The key rule is that the parameter lists have to be different for the methods to be overloaded. Or the parameters have to be in a different order. That is legal but not generally recommended due to possible confusion. The return type isn't taken into consideration.

Class Methods and Constants
Thus far all the methods and variables have been instance methods and variables, meaning that each instantiated object has their own separate copy. The sporty Car was Red and the family car was Gold for instance. Each one can start() or drive() independantly of the other.
Often, a class will declare values that it can use and/or expose. For instance, it is considered bad practice to have hard-coded 'magic' numbers, such as 6 in the code above. A much better way is to declare constants. The following snippet shows how to declare a few constants, both public and private, and to use them.

public class Car 
{
    // private (internal) constants
	private static final int MAX_DOORS = 6;
	private static final int MIN_DOORS = 1;
	
    // public (external) constants
	public static final String TYPE_SPORTY = "Sporty";
	public static final String TYPE_FAMILY = "Family";
	
    // a new property and its respective accessors
    private String carType;
	
    public String getCarType() 
    {
        return carType;
    }
    public void setCarType(String carType) 
    {
        this.carType = carType;
    }
    // rest of class the same with the exception of setNumberOfDoors
    public void setNumberOfDoors(int numberOfDoors) 
    {
        if ( MIN_DOORS >= 1 && numberOfDoors <= MAX_DOORS)
            this.numberOfDoors = numberOfDoors;
        else
            this.numberOfDoors = -1;
    }
}

Car fam = new Car("White", 4)
fam.setCarType(Car.TYPE_FAMILY);
In Java, class 'constants' are typcially declared as static final and are initialized when they are declared. As per the visibility modifiers discussed earlier, the private constants are only visible inside the class, and the public ones are used outside the class. They are required to be accessed outside the class using both the class and property name - Car.TYPE_SPORTY. Notice that the constants are declared as all caps with underscores between the words. Again, this is a convention and not enforced by the compiler.

Although the final modifier is what makes the value not able to be changed, the static modifier is quite important as well. The static modifier means that the value is a class-level member, not an instance member. This is why it has to be referenced through the class - Car.TYPE_FAMILY and not through an instance such as fam.TYPE_FAMILY. This will save on memory because instead of each instance having this value there will only be one copy of TYPE_FAMILY shared amonst all instances of the class.
The static modifier can also be applied to methods. Because this makes the function a "class-level" function it is also referenced through the class. The caveat is that a static function can only access other static members of the class. Given that limitation there are still certain cases where static functions make sense. The reader is encouraged to go back and review the static methods of the wrapper classes to see how they are defined and what their purpose is in those scenarios.

For the Car class, an example might be if the ability to compare two cars based solely on the number of doors they had. This could be done with an instance method, but it could also be implemented with a static method as follows:

public static boolean areDoorsEqual(Car c1, Car c2)
{
    return c1.getNumberOfDoors() == c2.getNumberOfDoors();
}

// And this could be used as follows
boolean doorsTheSame = Car.areDoorsEqual(family, sporty);
Another reason might be to define a "utility" method that is applicable to the Car class in general and maybe doesn't need to be on each and every instance. For example, say there is another attribute added to Car, a grossWeightInLbs member with its accompying accessors. A utility method might be to convert the weight to kilograms:

public static int convertGrossWeightToKGs(Car c)
{
    double kgs = c.grossWeightInLbs/2.2;
    return (int)kgs;
}
Object Base Class and Overrides
At the risk of encroaching on the object-oriented topic, it should be noted that every user-defined class, once it is compiled, is actually a child class of the java.lang.Object class. In fact, every class in Java, even those defined by the JDK, are children of Object. The reasoning behind that is beyond the scope of this course, but the important thing to realize is this gives every class some common behavior represented as methods. The two most common are toString() and equals(). It is possible to "override" these in the user-defined classes to have our classes exhibit consistent behavior.
The term "overriding" means that a method that is defined in a class' parent type can be redefined with behavior that is desirable in the child. Again, not wanting to step all over the object oriented lecture, but using the two methods from the previous paragraph will be a good example without having to explain too much of the OO theory.

In the Object class, the toString() method returns "a string representation of the object." But what exactly does that mean, it is surely different for each class? By default, the Object toString() method returns the name of the class with a few other symbols and a hash code. This method is called any time an object needs to be converted to a String. This default implementation in general is not very useful as it looks something like this when called on a Car object:

System.out.println(fam);

com.tts.Car@15db9742
This is obviously not very informational. The compiler tries to find a way to print the object, so it does so by internally calling the object's toString() method. To override and provide a useful implementation looks like this:

@Override
public String toString() 
{
    return String.format("This %s car has %d doors and is %s", 
            this.getCarType(),
            this.getNumberOfDoors(),
            this.getColor());
}

// this looks like:
System.out.println(fam);

This Family car has 4 doors and is Blue
The @Override statement is known as an annotation. These will be discussed in more detail in a later lesson. For now it is enough to know that it should be declared on methods that are overridden to help the compiler know that this is an overridden method. Other than that the method is a normal method implementation.

The other common method to override is equals() to test the equality of two objects. Remember that user-defined objects are reference types. And as was discussed in the types lecture, reference types are only equal if they refer to the same object. So the following code actually produces the same value of false.

Car car1 = new Car("Blue", 2);
Car car2 = new Car("Blue", 2);

System.out.println("Do they point to the same: " + (car1 == car2));
System.out.println("Are they equal: " + car1.equals(car2));

// The output is:
Do they point to the same: false
Are they equal: false
So a very simple implementation of the Car equals() method would be:

@Override
public boolean equals(Object obj) 
{
    Car c = (Car)obj;
    
    return (this.color.equals(c.color) && (this.numberOfDoors == c.numberOfDoors);
}

// With this definition of equals, the output is:
Do they point to the same: false
Are they equal: true
Notice that since the method is declared in the Object class, the type of argument is Object. This must be cast to the appropriate type before the comparison of the members is made. Notice that the comparison is this case is simple, based on the color and number of doors. The comparison may be a more complex memberwise comparison, or there could be a single attribute that is unique and should be used for all comparison. In a more complex car model the equals() method might be implemented as follows:

@Override
public boolean equals(Object obj) 
{
    Car c = (Car)obj;   
    // Assume the Car class has a VIN attribute, which should be unique for every car.
    return (this.VIN.equals(c.VIN));
}
--

--

--

--

Questions
The package that is imported into every Java file by default is:

base.package
java.lang
java.util
java.system
--

For a class called "Utils" in the "com.tts.misc" package, the folder structure will be:

src/Utils.java
src/packages/Utils.java
src/com.tts.misc.Utils.java
src/com/tts/misc/Utils.java
--

The method used to create an object from a class definition is called

init method
main method
new method
constructor method
--

It is required that the main() method be in the no-name default package

true
false
--

In java, class constancts are declared how?

static final int CONSTANT_VAL = 0;
constant final int CONSTANT_VAL = 0;
static void int CONSTANT_VAL = 0;
static locked int CONSTANT_VAL = 0;
--

Which is NOT a visiblity modifier defined in Java?

public
private
protected
--

What statement is used to bring a class in scope from a package?

insert
import
using
define
--

Multiple methods with the same name and different parameter lists is known as:

Overriding
Duplicating
Duplicate Defining
Overloading
--

Redefining methods defined in a parent class is known as:

toString()
Overloading
Overriding
Hiding
