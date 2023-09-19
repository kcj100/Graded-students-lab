# Graded-students-lab
## Head First Java, 2nd Edition Notes (Chapter 5-9)
### Chapter 5
- Most prepcode includes three parts: instance variable declarations, method declarations, method logic. (Page 100)
- "Don't put in anything that's not in the spec (no matter how tempted you are to put in funcionality 'for the future')."
  (Page 101)
- "No killer schedules; work regular hours." (Page 101)
- "Write the test code first" (Page 101)
- "Keep it simple" (Page 101)
- Write test code and implementation code at the same time, step by step (Page 103).
- In a line of code like "int randomNum = (int) (Math.random() * 5)", the 5 would mean only numbers from 0-4 will return. (Page 111)
- A while loop has only the boolean test, while the for-loop has a built-in initialization and iteration expression. (Page 115)
- Enhanced for-loop came out in Java 5.0. (Page 116)

### Chapter 6
- Single & and | force the JVM to always check both sides of the expression, unlike double & (&&)
and | (||). (Page 151)
- You can easily find out what's in the Java library by flipping through a reference book.
- We can also use polymorphism when we create parameters and put arguments for methods.

### Chapter 7
- Instance variables are not overridden. (Page 168)
- "Do not use inheritance just so that you can reuse code from another class, if the relationship
between the superclass and subclass violate either of the above two rules" (Page 181)

### Chapter 8
- "If you declare an abstract <em>method</em>, you MUST mark the <em>class</em> abstract as well.
You can't have an abstract method in a non-abstract class." (Page 203)
- Every class, regardless of whether it was created by you or already built in the Java API, extends the object class. 
All classes are subclasses of the object class.
- If a specific object turns into a reference of Object class, we can cast it back to the original Class.

### Chapter 9 
- It is advisable to include a parameterless constructor to simplify object creation, providing default values if needed.
- Overloaded constructors are when a class has multiple constructors with different argument lists.
- Overloaded constructors must have distinct argument lists, considering the order and types of arguments.
