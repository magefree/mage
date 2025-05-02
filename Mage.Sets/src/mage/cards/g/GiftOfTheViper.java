package mage.cards.g;

import java.util.UUID;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author grimreap124
 */
public final class GiftOfTheViper extends CardImpl {

    public GiftOfTheViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.INSTANT }, "{G}");

        // Put a +1/+1 counter, a reach counter, and a deathtouch counter on target creature. Untap it.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                .setText("Put a +1/+1 counter"));
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.REACH.createInstance())
                .setText("a reach counter").concatBy(","));
        this.getSpellAbility()
                .addEffect(new AddCountersTargetEffect(CounterType.DEATHTOUCH.createInstance())
                        .setText("a deathtouch counter on target creature").concatBy(", and"));
        this.getSpellAbility().addEffect(new UntapTargetEffect("untap it"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private GiftOfTheViper(final GiftOfTheViper card) {
        super(card);
    }

    @Override
    public GiftOfTheViper copy() {
        return new GiftOfTheViper(this);
    }
}
