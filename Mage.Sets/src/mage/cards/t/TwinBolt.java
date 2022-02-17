package mage.cards.t;

import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTargetAmount;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TwinBolt extends CardImpl {

    public TwinBolt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Twin Bolt deals 2 damage divided as you choose among one or two targets.
        this.getSpellAbility().addEffect(new DamageMultiEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTargetAmount(2));
    }

    private TwinBolt(final TwinBolt card) {
        super(card);
    }

    @Override
    public TwinBolt copy() {
        return new TwinBolt(this);
    }
}
