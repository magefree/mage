package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public final class GatekeeperGargoyle extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public GatekeeperGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.GARGOYLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Gargoyle Guardian enters the battlefield with a +1/+1 counter on it for each Gate you control.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(
                        CounterType.P1P1.createInstance(),
                        new PermanentsOnBattlefieldCount(filter), true
                ), "with a +1/+1 counter on it for each Gate you control"
        ));
    }

    public GatekeeperGargoyle(final GatekeeperGargoyle card) {
        super(card);
    }

    @Override
    public GatekeeperGargoyle copy() {
        return new GatekeeperGargoyle(this);
    }
}
