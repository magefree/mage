package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.cards.Card;

/**
 * @author TheElk801
 */
public class PrototypeAbility extends SpellAbility {

    public PrototypeAbility(Card card, String manaString, int power, int toughness) {
        super(new ManaCostsImpl<>(manaString), card.getName());
        // TODO: implement this
    }

    private PrototypeAbility(final PrototypeAbility ability) {
        super(ability);
    }

    @Override
    public PrototypeAbility copy() {
        return new PrototypeAbility(this);
    }

    @Override
    public String getRule() {
        return "Prototype";
    }
}
