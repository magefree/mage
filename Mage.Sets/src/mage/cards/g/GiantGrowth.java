package mage.cards.g;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class GiantGrowth extends CardImpl {

    public GiantGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Target creature gets +3/+3 until end of turn.
        Effect effect = new BoostTargetEffect(3, 3, Duration.EndOfTurn);
        effect.setOutcome(Outcome.Benefit);
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GiantGrowth(final GiantGrowth card) {
        super(card);
    }

    @Override
    public GiantGrowth copy() {
        return new GiantGrowth(this);
    }
}
