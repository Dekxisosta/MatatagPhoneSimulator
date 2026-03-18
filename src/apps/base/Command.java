package apps.base;

public class Command {
    private int key;
    private String name;
    private int duration;
    private Runnable action;
    private BooleanSupplier check;

    public interface BooleanSupplier {
        boolean getAsBoolean();
    }
    public Command(
            int key,
            String name,
            int duration,
            Runnable action,
            BooleanSupplier check
    ){
        this.key = key;
        this.name = name;
        this.duration = duration;
        this.action = action;
        this.check = check;
    }

    public boolean isAvailable(){
        if(check != null) return check.getAsBoolean();
        return true;
    }
    public void setAvailabilityCheck(BooleanSupplier check){
        this.check = check;
    }

    public int getKey(){ return key; }
    public String getName(){ return name; }
    public int getDuration(){ return duration; }

    public void execute(){
        if(!isAvailable()) throw new IllegalStateException("[ERROR] Command is not available.");
        action.run();
    }

    public boolean equals(int key){ return this.key == key; }

    @Override
    public boolean equals(Object o){
        if(o==null || getClass() != o.getClass()) return false;
        Command c = (Command) o;
        return this.key == c.key;
    }
}