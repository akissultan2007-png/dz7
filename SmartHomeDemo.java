import java.util.*;

// =======================
// 1) COMMAND PATTERN
// =======================

interface Command {
    void execute();
    void undo();
}

// Receivers
class Light {
    public void on() { System.out.println("Light is ON"); }
    public void off() { System.out.println("Light is OFF"); }
}

class Door {
    public void open() { System.out.println("Door is OPEN"); }
    public void close() { System.out.println("Door is CLOSED"); }
}

class Thermostat {
    private int temperature = 20;

    public void increase() {
        temperature++;
        System.out.println("Temperature increased to " + temperature);
    }

    public void decrease() {
        temperature--;
        System.out.println("Temperature decreased to " + temperature);
    }
}

// Concrete Commands
class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
    public void undo() { light.off(); }
}

class LightOffCommand implements Command {
    private Light light;
    public LightOffCommand(Light light) { this.light = light; }
    public void execute() { light.off(); }
    public void undo() { light.on(); }
}

class DoorOpenCommand implements Command {
    private Door door;
    public DoorOpenCommand(Door door) { this.door = door; }
    public void execute() { door.open(); }
    public void undo() { door.close(); }
}

class DoorCloseCommand implements Command {
    private Door door;
    public DoorCloseCommand(Door door) { this.door = door; }
    public void execute() { door.close(); }
    public void undo() { door.open(); }
}

class TempIncreaseCommand implements Command {
    private Thermostat thermostat;
    public TempIncreaseCommand(Thermostat thermostat) { this.thermostat = thermostat; }
    public void execute() { thermostat.increase(); }
    public void undo() { thermostat.decrease(); }
}

class TempDecreaseCommand implements Command {
    private Thermostat thermostat;
    public TempDecreaseCommand(Thermostat thermostat) { this.thermostat = thermostat; }
    public void execute() { thermostat.decrease(); }
    public void undo() { thermostat.increase(); }
}

// Invoker
class RemoteControl {
    private Stack<Command> history = new Stack<>();

    public void executeCommand(Command command) {
        command.execute();
        history.push(command);
    }

    public void undoLast() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            cmd.undo();
        } else {
            System.out.println("No commands to undo");
        }
    }
}

// =======================
// 2) TEMPLATE METHOD
// =======================

abstract class Beverage {

    public final void prepareRecipe() {
        boilWater();
        brew();
        pourInCup();
        if (customerWantsCondiments()) {
            addCondiments();
        }
    }

    private void boilWater() {
        System.out.println("Boiling water");
    }

    private void pourInCup() {
        System.out.println("Pouring into cup");
    }

    protected abstract void brew();
    protected abstract void addCondiments();

    protected boolean customerWantsCondiments() {
        return true; // hook
    }
}

class Tea extends Beverage {
    protected void brew() {
        System.out.println("Steeping the tea");
    }

    protected void addCondiments() {
        System.out.println("Adding lemon");
    }
}

class Coffee extends Beverage {

    protected void brew() {
        System.out.println("Dripping coffee through filter");
    }

    protected void addCondiments() {
        System.out.println("Adding sugar and milk");
    }

    protected boolean customerWantsCondiments() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want sugar and milk? (yes/no): ");
        String answer = scanner.nextLine();
        return answer.equalsIgnoreCase("yes");
    }
}

// =======================
// 3) MEDIATOR PATTERN
// =======================

interface Mediator {
