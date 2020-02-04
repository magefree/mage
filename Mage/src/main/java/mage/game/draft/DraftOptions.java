

package mage.game.draft;

import java.io.Serializable;
import mage.game.tournament.LimitedOptions;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DraftOptions extends LimitedOptions implements Serializable {

    protected String draftType;
    protected TimingOption timing;
   
    public enum TimingOption {
        REGULAR (1),
        BEGINNER (2),
        NONE (0);

        private int factor;

        TimingOption(int factor) {
            this.factor = factor;
        }

        public int getFactor() {
            return this.factor;
        }
    }

    public String getDraftType() {
        return draftType;
    }

    public void setDraftType(String draftType) {
        this.draftType = draftType;
    }

    public TimingOption getTiming() {
        return timing;
    }

    public void setTiming(TimingOption timing) {
        this.timing = timing;
    }
}
