package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.SacrificePermanentTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrithingChrysalis extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ELDRAZI, "another Eldrazi");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WrithingChrysalis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // When you cast this spell, create two 0/1 colorless Eldrazi Spawn creature tokens with "Sacrifice this creature: Add {C}."
        this.addAbility(new CastSourceTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken(), 2)));

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever you sacrifice another Eldrazi, put a +1/+1 counter on Writhing Chrysalis.
        this.addAbility(new SacrificePermanentTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));
    }

    private WrithingChrysalis(final WrithingChrysalis card) {
        super(card);
    }

    @Override
    public WrithingChrysalis copy() {
        return new WrithingChrysalis(this);
    }
}
