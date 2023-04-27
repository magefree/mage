
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetManaValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class GreatDefender extends CardImpl {

    public GreatDefender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target creature gets +0/+X until end of turn, where X is its converted mana cost.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(StaticValue.get(0), TargetManaValue.instance, Duration.EndOfTurn)
                .setText("Target creature gets +0/+X until end of turn, where X is its mana value.")
        );
    }

    private GreatDefender(final GreatDefender card) {
        super(card);
    }

    @Override
    public GreatDefender copy() {
        return new GreatDefender(this);
    }
}
