package mage.cards.o;

import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnTheJob extends CardImpl {

    public OnTheJob(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");

        // Creatures you control get +2/+1 until end of turn. Investigate.
        this.getSpellAbility().addEffect(new BoostControlledEffect(2, 1, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new InvestigateEffect());
    }

    private OnTheJob(final OnTheJob card) {
        super(card);
    }

    @Override
    public OnTheJob copy() {
        return new OnTheJob(this);
    }
}
