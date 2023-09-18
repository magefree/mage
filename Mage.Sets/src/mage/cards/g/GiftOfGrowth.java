
package mage.cards.g;

import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author rscoates
 */
public final class GiftOfGrowth extends CardImpl {

    public GiftOfGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}");

        // Kicker {2}
        this.addAbility(new KickerAbility("{2}"));

        // Untap target creature. It gets +2/+2 until end of turn. If this spell was kicked, that creature gets +4/+4 until end of turn instead.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new UntapTargetEffect().setText("Untap target creature"));
        this.getSpellAbility().addEffect(new ConditionalContinuousEffect(new BoostTargetEffect(4, 4, Duration.EndOfTurn),
                new BoostTargetEffect(2, 2, Duration.EndOfTurn), new LockedInCondition(KickedCondition.ONCE),
                "It gets +2/+2 until end of turn. If this spell was kicked, that creature gets +4/+4 until end of turn instead."));
    }

    private GiftOfGrowth(final GiftOfGrowth card) {
        super(card);
    }

    @Override
    public GiftOfGrowth copy() {
        return new GiftOfGrowth(this);
    }
}
