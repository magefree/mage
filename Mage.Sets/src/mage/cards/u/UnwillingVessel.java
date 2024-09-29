package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.abilityword.EerieAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.Counter;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.SpiritBlueToken;
import mage.game.permanent.token.Token;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author TheElk801
 */
public final class UnwillingVessel extends CardImpl {

    public UnwillingVessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Eerie -- Whenever an enchantment you control enters and whenever you fully unlock a Room, put a possession counter on Unwilling Vessel.
        this.addAbility(new EerieAbility(new AddCountersSourceEffect(CounterType.POSSESSION.createInstance())));

        // When Unwilling Vessel dies, create an X/X blue Spirit creature token with flying, where X is the number of counters on Unwilling Vessel.
        this.addAbility(new DiesSourceTriggeredAbility(new UnwillingVesselEffect()));
    }

    private UnwillingVessel(final UnwillingVessel card) {
        super(card);
    }

    @Override
    public UnwillingVessel copy() {
        return new UnwillingVessel(this);
    }
}

class UnwillingVesselEffect extends OneShotEffect {

    UnwillingVesselEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X blue Spirit creature token with flying, where X is the number of counters on {this}";
    }

    private UnwillingVesselEffect(final UnwillingVesselEffect effect) {
        super(effect);
    }

    @Override
    public UnwillingVesselEffect copy() {
        return new UnwillingVesselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int count = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(permanent -> permanent.getCounters(game))
                .map(HashMap::values)
                .map(Collection::stream)
                .map(s -> s.mapToInt(Counter::getCount))
                .map(IntStream::sum)
                .orElse(0);
        Token token = new SpiritBlueToken();
        token.setPower(count);
        token.setToughness(count);
        return token.putOntoBattlefield(1, game, source);
    }
}
