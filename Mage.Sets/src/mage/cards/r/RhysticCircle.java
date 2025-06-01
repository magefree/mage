
package mage.cards.r;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.PreventNextDamageFromChosenSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author djbrez
 */

public final class RhysticCircle extends CardImpl {

    public RhysticCircle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // {1}: Any player may pay {1}. If no one does, the next time a source of your choice would deal damage to you this turn, prevent that damage.
        this.addAbility(new SimpleActivatedAbility(
                new DoUnlessAnyPlayerPaysEffect(
                        new PreventNextDamageFromChosenSourceEffect(Duration.EndOfTurn, true),
                        new GenericManaCost(1)
                ),
                new ManaCostsImpl<>("{1}")));
    }

    private RhysticCircle(final RhysticCircle card) {
        super(card);
    }

    @Override
    public RhysticCircle copy() {
        return new RhysticCircle(this);
    }
}
