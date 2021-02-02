
package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class MutagenicGrowth extends CardImpl {

    public MutagenicGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G/P}");

        // ({GP} can be paid with either {G} or 2 life.)
        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
    }

    private MutagenicGrowth(final MutagenicGrowth card) {
        super(card);
    }

    @Override
    public MutagenicGrowth copy() {
        return new MutagenicGrowth(this);
    }

}
