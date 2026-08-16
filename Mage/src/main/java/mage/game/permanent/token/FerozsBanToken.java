package mage.game.permanent.token;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostIncreasingAllEffect;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;

/**
 * @author muz
 */
public final class FerozsBanToken extends TokenImpl {

    private static final FilterCard filter = new FilterCreatureCard("Creature spells");

    public FerozsBanToken() {
        super("Feroz's Ban", "Feroz's Ban token");
        manaCost = new ManaCostsImpl<>("{6}");
        cardType.add(CardType.ARTIFACT);

        // Creature spells cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostIncreasingAllEffect(2, filter, TargetController.ANY)));
    }

    private FerozsBanToken(final FerozsBanToken token) {
        super(token);
    }

    @Override
    public FerozsBanToken copy() {
        return new FerozsBanToken(this);
    }
}
