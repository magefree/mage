
package mage.cards.a;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.HighestManaValueCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nigelzor
 */
public final class AcceleratedMutation extends CardImpl {

    public AcceleratedMutation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}{G}");

        // Target creature gets +X/+X until end of turn, where X is the highest converted mana cost among permanents you control.
        DynamicValue amount = new HighestManaValueCount();
        this.getSpellAbility().addEffect(new BoostTargetEffect(amount, amount, Duration.EndOfTurn, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AcceleratedMutation(final AcceleratedMutation card) {
        super(card);
    }

    @Override
    public AcceleratedMutation copy() {
        return new AcceleratedMutation(this);
    }
}
