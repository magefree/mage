package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FearlessFledgling extends CardImpl {

    public FearlessFledgling(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.GRIFFIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Landfall - Whenever a land enters the battlefield under your control, put a +1/+1 counter on Fearless Fledgling. It gains flying until end of turn.
        Ability ability = new LandfallAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        ability.addEffect(new GainAbilitySourceEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains flying until end of turn."));
        this.addAbility(ability);
    }

    private FearlessFledgling(final FearlessFledgling card) {
        super(card);
    }

    @Override
    public FearlessFledgling copy() {
        return new FearlessFledgling(this);
    }
}
