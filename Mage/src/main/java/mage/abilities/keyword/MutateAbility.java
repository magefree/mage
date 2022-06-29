package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;

public class MutateAbility extends SpellAbility {

    public MutateAbility(Card card, String manaString) {
        super(new ManaCostsImpl<>(manaString), card.getName() + " using mutate");
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = TimingRule.SORCERY;
        // TODO: Implement this
    }

    private MutateAbility(final MutateAbility ability) {
        super(ability);
    }

    @Override
    public MutateAbility copy() {
        return new MutateAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Mutate " + getManaCostsToPay().getText() + " <i>(If you cast this spell for its mutate cost, " +
                "put it over or under target non-Human creature you own. " +
                "They mutate into the creature on top plus all abilities from under it.)</i>";
    }

}
