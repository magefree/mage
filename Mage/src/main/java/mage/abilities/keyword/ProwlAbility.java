package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.condition.common.ProwlCondition;
import mage.abilities.costs.AlternativeSourceCostsImpl;
import mage.abilities.hint.common.ProwlHint;
import mage.cards.Card;
import mage.game.Game;
import mage.watchers.common.ProwlWatcher;

/**
 * 702.74. Prowl #
 * <p>
 * 702.74a Prowl is a static ability that functions on the stack. "Prowl [cost]"
 * means "You may pay [cost] rather than pay this spell's mana cost if a player
 * was dealt combat damage this turn by a source that, at the time it dealt that
 * damage, was under your control and had any of this spell's creature types."
 * Paying a spell's prowl cost follows the rules for paying alternative costs in
 * rules 601.2b and 601.2e-g
 *
 * @author LevelX2
 */
public class ProwlAbility extends AlternativeSourceCostsImpl {

    private static final String PROWL_KEYWORD = "Prowl";
    private static final String reminderText = "You may cast this for its prowl cost if you dealt combat damage to a "
            + "player this turn with a creature that shared a creature type with {this}";

    public ProwlAbility(Card card, String manaString) {
        super(PROWL_KEYWORD, reminderText, manaString);
        this.setRuleAtTheTop(true);
        this.addWatcher(new ProwlWatcher());
        this.addHint(ProwlHint.instance);
    }

    private ProwlAbility(final ProwlAbility ability) {
        super(ability);
    }

    @Override
    public ProwlAbility copy() {
        return new ProwlAbility(this);
    }

    @Override
    public boolean isAvailable(Ability source, Game game) {
        return ProwlCondition.instance.apply(game, source);
    }
}
