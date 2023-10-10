package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.abilities.effects.common.IfAbilityHasResolvedXTimesEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.AbilityResolutionCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.watchers.common.AbilityResolvedWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshlingThePilgrim extends CardImpl {

    public AshlingThePilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}{R}: Put a +1/+1 counter on Ashling the Pilgrim. If this is the third time this ability has resolved this turn, remove all +1/+1 counters from Ashling the Pilgrim, and it deals that much damage to each creature and each player.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new ManaCostsImpl<>("{1}{R}")
        );
        ability.addEffect(new IfAbilityHasResolvedXTimesEffect(Outcome.Damage, 3, new AshlingThePilgrimEffect()));
        ability.addHint(AbilityResolutionCountHint.instance);
        this.addAbility(ability, new AbilityResolvedWatcher());
    }

    private AshlingThePilgrim(final AshlingThePilgrim card) {
        super(card);
    }

    @Override
    public AshlingThePilgrim copy() {
        return new AshlingThePilgrim(this);
    }
}

class AshlingThePilgrimEffect extends OneShotEffect {

    AshlingThePilgrimEffect() {
        super(Outcome.Damage);
        this.staticText = "remove all +1/+1 counters from {this}, and it deals that much damage to each creature and each player";
    }

    private AshlingThePilgrimEffect(final AshlingThePilgrimEffect effect) {
        super(effect);
    }

    @Override
    public AshlingThePilgrimEffect copy() {
        return new AshlingThePilgrimEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int counters = sourcePermanent.getCounters(game).getCount(CounterType.P1P1);
            if (counters < 1) {
                return false;
            }
            sourcePermanent.removeCounters(CounterType.P1P1.createInstance(counters), source, game);
            return new DamageEverythingEffect(counters, StaticFilters.FILTER_PERMANENT_CREATURE).apply(game, source);
        }
        return true;
    }
}
