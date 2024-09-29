package mage.abilities.keyword;

import mage.abilities.common.SpellTransformedAbility;
import mage.cards.Card;
import mage.constants.*;

/**
 * @author notgreat, TheElk801
 */
public class MoreThanMeetsTheEyeAbility extends SpellTransformedAbility {


    public MoreThanMeetsTheEyeAbility(Card card, String manaCost) {
        super(card, manaCost);
        this.setSpellAbilityCastMode(SpellAbilityCastMode.MORE_THAN_MEETS_THE_EYE);
    }

    private MoreThanMeetsTheEyeAbility(final MoreThanMeetsTheEyeAbility ability) {
        super(ability);
    }

    @Override
    public MoreThanMeetsTheEyeAbility copy() {
        return new MoreThanMeetsTheEyeAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "More Than Meets the Eye " + this.manaCost
            + " <i>(You may cast this card converted for " + this.manaCost + ".)</i>";
    }
}
