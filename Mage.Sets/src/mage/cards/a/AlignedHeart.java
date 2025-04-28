package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.FlurryAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.permanent.token.MonasteryMentorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AlignedHeart extends CardImpl {

    private static final DynamicValue xValue = new CountersSourceCount(CounterType.RALLY);

    public AlignedHeart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Flurry -- Whenever you cast your second spell each turn, put a rally counter on this enchantment. Then create a 1/1 white Monk creature token with prowess for each rally counter on it.
        Ability ability = new FlurryAbility(new AddCountersSourceEffect(CounterType.RALLY.createInstance()));
        ability.addEffect(new CreateTokenEffect(new MonasteryMentorToken(), xValue)
                .setText("Then create a 1/1 white Monk creature token with prowess for each rally counter on it"));
        this.addAbility(ability);
    }

    private AlignedHeart(final AlignedHeart card) {
        super(card);
    }

    @Override
    public AlignedHeart copy() {
        return new AlignedHeart(this);
    }
}
