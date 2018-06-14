package com.pipai.shmup;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.utils.IntBag;
import com.pipai.shmup.artemis.components.CollisionBoxComponent;
import com.pipai.shmup.artemis.components.Dog;
import com.pipai.shmup.artemis.components.XyComponent;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * An example function with a single argument x. It does some stuff, then returns an integer.
     */
    public static int example(int x) {
        // Create a new variable z that has the value of x plus 0.1
        float z = x + 0.1f;

        // This line prints out the value of z.
        System.out.println(z);

        // This square variable is assigned the value of x times x. AKA, the square.
        int square = x * x;
        System.out.println(square);

        // This is a string variable that is assigned the text value "what".
        String what = "what";
        System.out.println(what);

        // This is a boolean. If the square happens to be 9, then this variable will be true.
        boolean squareIs9 = square == 9;

        // This will be the opposite of squareIs9.
        boolean squareIsNot9 = !squareIs9;

        System.out.println(squareIsNot9);

        // We can combine some comparison statements with && (and) and || (or)
        System.out.println(x == 0 || x + square > 10);

        double y = 12.0;
        // Sometimes we should use some parentheses to indicate which things are evaluated first.
        y = -(10 * (y / 2 + x));
        System.out.println(y);

        // We will return the square value.
        return square;
    }

    /**
     * Object example function.
     * Note: void means that it does not return anything.
     */
    public static void objectExample() {
        // Let's create a new dog! This dog's constructor takes one argument, the name.
        Dog spot = new Dog("Spot");

        // Spot can bark!
        spot.bark();

        System.out.println("Spot's position: (" + spot.x + ", " + spot.y + ")");

        spot.walkTo(2, 2);
        System.out.println("Spot's position: (" + spot.x + ", " + spot.y + ")");

        // Check Spot's name
        System.out.println(spot.getName());

        // Let's change the name
        spot.setName("Snoopy");
        System.out.println(spot.getName());

        // Let's reassign the variable
        Dog snoopy = spot;
        snoopy.bark();

        snoopy.setName("Fido");
        System.out.println(snoopy.getName());

        // Note that since snoopy and spot are actually the same object, both of their names are now Fido.
        // This will likely show up as a bug for you. Make sure to use new objects for new variables!
        System.out.println(spot.getName());
    }

    public static List<Integer> fetchEntities(World world, Aspect.Builder aspects) {
        IntBag entityBag = world.getAspectSubscriptionManager()
                .get(aspects)
                .getEntities();
        List<Integer> entities = new ArrayList<>();
        for (int i = 0; i < entityBag.size(); i++) {
            entities.add(entityBag.get(i));
        }
        return entities;
    }

    public static boolean collides(XyComponent xy1, CollisionBoxComponent collision1, XyComponent xy2, CollisionBoxComponent collision2) {
        return xy1.x + collision1.xOffset < xy2.x + collision2.xOffset + collision2.width
                && xy1.x + collision1.xOffset + collision1.width > xy2.x + collision2.xOffset
                && xy1.y + collision1.yOffset < xy2.y + collision2.yOffset + collision2.height
                && xy1.y + collision1.yOffset + collision1.height > xy2.y + collision2.yOffset;
    }
}
