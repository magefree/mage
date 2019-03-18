
package mage.cards.g;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.TargetConvertedManaCost;
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
        this.getSpellAbility().addEffect(new BoostTargetEffect(new StaticValue(0), TargetConvertedManaCost.instance, Duration.EndOfTurn, true)
                .setText("Target creature gets +0/+X until end of turn, where X is its converted mana cost.")
        );
    }

    public GreatDefender(final GreatDefender card) {
        super(card);
    }

    @Override
    public GreatDefender copy() {
        return new GreatDefender(this);
    }
}
