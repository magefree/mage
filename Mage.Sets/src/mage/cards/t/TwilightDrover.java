package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class TwilightDrover extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public TwilightDrover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a creature token leaves the battlefield, put a +1/+1 counter on Twilight Drover.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter));

        // {2}{W}, Remove a +1/+1 counter from Twilight Drover: Create two 1/1 white Spirit creature tokens with flying.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken(), 2), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new RemoveCountersSourceCost(CounterType.P1P1.createInstance()));
        this.addAbility(ability);
    }

    private TwilightDrover(final TwilightDrover card) {
        super(card);
    }

    @Override
    public TwilightDrover copy() {
        return new TwilightDrover(this);
    }
}
