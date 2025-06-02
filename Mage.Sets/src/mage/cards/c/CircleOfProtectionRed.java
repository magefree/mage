
package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSource;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class CircleOfProtectionRed extends CardImpl {

    private static final FilterSource filter = new FilterSource("red source");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public CircleOfProtectionRed(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");


        // {1}: The next time a red source of your choice would deal damage to you this turn, prevent that damage.
        Effect effect = new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, true, filter);
        this.addAbility(new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{1}")));
    }

    private CircleOfProtectionRed(final CircleOfProtectionRed card) {
        super(card);
    }

    @Override
    public CircleOfProtectionRed copy() {
        return new CircleOfProtectionRed(this);
    }
}
