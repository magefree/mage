package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.DealsDamageToYouAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StrixhavenStadium extends CardImpl {

    public StrixhavenStadium(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {C}. Put a point counter on Strixhaven Stadium.
        Ability ability = new ColorlessManaAbility();
        ability.addEffect(new AddCountersSourceEffect(CounterType.POINT.createInstance()));
        this.addAbility(ability);

        // Whenever a creature deals combat damage to you, remove a point counter from Strixhaven Stadium.
        this.addAbility(new DealsDamageToYouAllTriggeredAbility(StaticFilters.FILTER_PERMANENT_CREATURE,
                new RemoveCounterSourceEffect(CounterType.POINT.createInstance()), true));

        // Whenever a creature you control deals combat damage to an opponent, put a point counter on Strixhaven Stadium. Then if it has ten or more point counters on it, remove them all and that player loses the game.
        ability = new DealsDamageToAPlayerAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.POINT.createInstance()),
                StaticFilters.FILTER_CONTROLLED_A_CREATURE, false, SetTargetPointer.PLAYER,
                true, true, TargetController.OPPONENT
        );
        ability.addEffect(new StrixhavenStadiumEffect());
        this.addAbility(ability);
    }

    private StrixhavenStadium(final StrixhavenStadium card) {
        super(card);
    }

    @Override
    public StrixhavenStadium copy() {
        return new StrixhavenStadium(this);
    }
}

class StrixhavenStadiumEffect extends OneShotEffect {

    StrixhavenStadiumEffect() {
        super(Outcome.Benefit);
        staticText = "Then if it has ten or more point counters on it, remove them all and that player loses the game";
    }

    private StrixhavenStadiumEffect(final StrixhavenStadiumEffect effect) {
        super(effect);
    }

    @Override
    public StrixhavenStadiumEffect copy() {
        return new StrixhavenStadiumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        boolean lki = false;
        if (permanent == null) {
            lki = true;
            permanent = source.getSourcePermanentOrLKI(game);
        }
        if (permanent == null || permanent.getCounters(game).getCount(CounterType.POINT) < 10) {
            return false;
        }
        if (!lki) {
            permanent.removeCounters(CounterType.POINT.createInstance(
                    permanent.getCounters(game).getCount(CounterType.POINT)
            ), source, game);
        }
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.lost(game);
            return true;
        }
        return !lki;
    }
}
