package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.watchers.common.SpellsCastWatcher;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class CycleOfLife extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature you cast this turn");
    static {
        filter.add(CycleOfLifePredicate.instance);
    }

    public CycleOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Return Cycle of Life to its owner's hand: Target creature you cast this turn becomes 0/1 until your next upkeep. At the beginning of your next upkeep, put a +1/+1 counter on that creature.
        Ability ability = new SimpleActivatedAbility(
                new SetBasePowerToughnessTargetEffect(0, 1, Duration.UntilYourNextUpkeepStep),
                new ReturnToHandFromBattlefieldSourceCost());
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                                .withTargetDescription("that creature")
                ), true
        ));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private CycleOfLife(final CycleOfLife card) {
        super(card);
    }

    @Override
    public CycleOfLife copy() {
        return new CycleOfLife(this);
    }
}

enum CycleOfLifePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
        return watcher != null && watcher
                .getSpellsCastThisTurn(input.getPlayerId())
                .stream()
                .anyMatch(spell -> spell.getSourceId().equals(input.getObject().getId())
                        && spell.getZoneChangeCounter(game) + 1 == input.getObject().getZoneChangeCounter(game)
                );
    }
}
