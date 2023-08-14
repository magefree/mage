package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class UglukOfTheWhiteHand extends CardImpl {

    public UglukOfTheWhiteHand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another creature you control dies, put a +1/+1 counter on Ugluk of the White Hand.
        // If that creature was a Goblin or Orc, put two +1/+1 counters on Ugluk instead.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new UglukOfTheWhiteHandEffect(),
            false,
            StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL,
            true
        ));
    }

    private UglukOfTheWhiteHand(final UglukOfTheWhiteHand card) {
        super(card);
    }

    @Override
    public UglukOfTheWhiteHand copy() {
        return new UglukOfTheWhiteHand(this);
    }
}


class UglukOfTheWhiteHandEffect extends OneShotEffect {

    private static final FilterPermanent filterOrcOrGoblin = new FilterCreaturePermanent();

    static {
        filterOrcOrGoblin.add(
            Predicates.or(
                SubType.ORC.getPredicate(),
                SubType.GOBLIN.getPredicate()
            ));
    }

    public UglukOfTheWhiteHandEffect() {
        super(Outcome.Benefit);
        staticText = "put a +1/+1 counter on Ugluk of the White Hand. " +
            "If that creature was a Goblin or Orc, put two +1/+1 counters on Ugluk instead.";
    }

    public UglukOfTheWhiteHandEffect(final UglukOfTheWhiteHandEffect effect) {
        super(effect);
    }

    @Override
    public UglukOfTheWhiteHandEffect copy() {
        return new UglukOfTheWhiteHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent deadCreature = game.getPermanentOrLKIBattlefield(targetPointer.getFirst(game, source));

        boolean wasOrcOrGoblin = false;
        if (deadCreature != null) {
            wasOrcOrGoblin = filterOrcOrGoblin.match(deadCreature, game);
        }

        Permanent ugluk = game.getPermanent(source.getSourceId());
        if(ugluk == null){
            return false;
        }

        ugluk.addCounters(
            CounterType.P1P1.createInstance(wasOrcOrGoblin ? 2 : 1),
            source.getControllerId(), source, game);

        return true;
    }
}
