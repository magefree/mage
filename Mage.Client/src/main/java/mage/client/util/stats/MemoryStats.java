package mage.client.util.stats;

/**
 *
 * @author JayDi85
 */
public class MemoryStats {

    private float Available = 0;
    private float MaxAvailable = 0;
    private float Used = 0;
    private float Free = 0;

    public MemoryStats(float MaxAvailable, float Available, float Used, float Free){
        this.setMaxAvailable(MaxAvailable);
        this.setAvailable(Available);
        this.setUsed(Used);
        this.setFree(Free);
    }

    public float getAvailable() {
        return Available;
    }

    public void setAvailable(float available) {
        Available = available;
    }

    public float getUsed() {
        return Used;
    }

    public void setUsed(float used) {
        Used = used;
    }

    public float getFree() {
        return Free;
    }

    public void setFree(float free) {
        Free = free;
    }

    public float getMaxAvailable() {
        return MaxAvailable;
    }

    public void setMaxAvailable(float maxAvailable) {
        MaxAvailable = maxAvailable;
    }
}