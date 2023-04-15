package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ConvokedSourcePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZephyrSinger extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that convoked it");

    static {
        filter.add(ConvokedSourcePredicate.PERMANENT);
    }

    public ZephyrSinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");

        this.subtype.add(SubType.SIREN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Convoke
        this.addAbility(new ConvokeAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Zephyr Singer enters the battlefield, put a flying counter on each creature that convoked it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new AddCountersAllEffect(CounterType.FLYING.createInstance(), filter)
        ));
    }

    private ZephyrSinger(final ZephyrSinger card) {
        super(card);
    }

    @Override
    public ZephyrSinger copy() {
        return new ZephyrSinger(this);
    }
}
