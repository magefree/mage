package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GimliOfTheGlitteringCaves extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("another legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public GimliOfTheGlitteringCaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Whenever another legendary creature enters the battlefield under your control, put a +1/+1 counter on Gimli of the Glittering Caves.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter
        ));

        // Whenever Gimli deals combat damage to a player, create a Treasure token.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false
        ));
    }

    private GimliOfTheGlitteringCaves(final GimliOfTheGlitteringCaves card) {
        super(card);
    }

    @Override
    public GimliOfTheGlitteringCaves copy() {
        return new GimliOfTheGlitteringCaves(this);
    }
}
