package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * TODO: Implement this
 *
 * @author TheElk801
 */
public class ImpendingAbility extends AlternativeSourceCostsImpl {

    private static final String IMPENDING_KEYWORD = "Impending";
    private static final String IMPENDING_REMINDER = "If you cast this spell for its impending cost, " +
            "it enters with %s time counters and isn't a creature until the last is removed. " +
            "At the beginning of your end step, remove a time counter from it.";

    public ImpendingAbility(String manaString) {
        this(manaString, 4);
    }

    public ImpendingAbility(String manaString, int amount) {
        super(IMPENDING_KEYWORD + ' ' + amount, String.format(IMPENDING_REMINDER, CardUtil.numberToText(amount)), manaString);
        this.setRuleAtTheTop(true);
    }

    private ImpendingAbility(final ImpendingAbility ability) {
        super(ability);
    }

    @Override
    public ImpendingAbility copy() {
        return new ImpendingAbility(this);
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return true;
    }

    public static String getActivationKey() {
        return getActivationKey(IMPENDING_KEYWORD);
    }
}
