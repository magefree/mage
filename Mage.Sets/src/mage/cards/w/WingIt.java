package mage.cards.w;

import java.util.UUID;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author grimreap124
 */
public final class WingIt extends CardImpl {

    public WingIt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.INSTANT }, "{1}{W}");

        // Target creature gets +2/+2 until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        // Put a flying counter on it.
        this.getSpellAbility().addEffect(
                new AddCountersTargetEffect(CounterType.FLYING.createInstance()).setText("put a flying counter on it"));

        // Scry 1.
        this.getSpellAbility().addEffect(new ScryEffect(1, false));
    }

    private WingIt(final WingIt card) {
        super(card);
    }

    @Override
    public WingIt copy() {
        return new WingIt(this);
    }
}
