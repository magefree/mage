package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.HeroToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AerithRescueMission extends CardImpl {

    public AerithRescueMission(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{W}");

        // Choose one--
        // * Take the Elevator -- Create three 1/1 colorless Hero creature tokens.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new HeroToken(), 3));
        this.getSpellAbility().withFirstModeFlavorWord("Take the Elevator");

        // * Take 59 Flights of Stairs -- Tap up to three target creatures. Put a stun counter on one of them.
        this.getSpellAbility().addMode(new Mode(new TapTargetEffect())
                .addEffect(new AddCountersTargetEffect(CounterType.STUN.createInstance())
                        .setText("Put a stun counter on one of them"))
                .addTarget(new TargetCreaturePermanent(0, 3))
                .withFlavorWord("Take 59 Flights of Stairs"));
    }

    private AerithRescueMission(final AerithRescueMission card) {
        super(card);
    }

    @Override
    public AerithRescueMission copy() {
        return new AerithRescueMission(this);
    }
}
