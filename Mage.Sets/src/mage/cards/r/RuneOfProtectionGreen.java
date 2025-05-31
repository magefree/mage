
package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterSource;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author Backfir3
 */
public final class RuneOfProtectionGreen extends CardImpl {

    private static final FilterSource filter = new FilterSource("green source");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public RuneOfProtectionGreen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");


        // {W}: The next time a green source of your choice would deal damage to you this turn, prevent that damage.
        Effect effect = new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, true, filter);
        this.addAbility(new SimpleActivatedAbility(effect, new ManaCostsImpl<>("{W}")));
        // Cycling {2} ({2}, Discard this card: Draw a card.)
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RuneOfProtectionGreen(final RuneOfProtectionGreen card) {
        super(card);
    }

    @Override
    public RuneOfProtectionGreen copy() {
        return new RuneOfProtectionGreen(this);
    }
}
