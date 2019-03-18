

package mage.abilities.effects;

/**
 *
 * @author LevelX2
 */
public class PreventionEffectData {

    private boolean error;
    private int preventedDamage;
    private int remainingAmount;
    private boolean replaced;

    public PreventionEffectData(int remainingAmount) {
        this.error = false;
        this.preventedDamage = 0;
        this.remainingAmount = remainingAmount;
        this.replaced = false;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getPreventedDamage() {
        return preventedDamage;
    }

    public void setPreventedDamage(int preventedDamage) {
        this.preventedDamage = preventedDamage;
    }

    public int getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(int remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public boolean isReplaced() {
        return replaced;
    }

    public void setReplaced(boolean replaced) {
        this.replaced = replaced;
    }

}
