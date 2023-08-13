
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author Loki
 */
public final class Grindclock extends CardImpl {

    public Grindclock(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.CHARGE.createInstance()), new TapSourceCost()));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GrindclockEffect(), new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Grindclock(final Grindclock card) {
        super(card);
    }

    @Override
    public Grindclock copy() {
        return new Grindclock(this);
    }

}

class GrindclockEffect extends OneShotEffect {

    public GrindclockEffect() {
        super(Outcome.Detriment);
        staticText = "Target player mills X cards, where X is the number of charge counters on {this}";
    }

    public GrindclockEffect(final GrindclockEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            int amount = sourceObject.getCounters(game).getCount(CounterType.CHARGE);
            Player targetPlayer = game.getPlayer(source.getFirstTarget());
            if (targetPlayer != null) {
                targetPlayer.millCards(amount, source, game);
                return true;
            }
        }
        return false;
    }

    @Override
    public GrindclockEffect copy() {
        return new GrindclockEffect(this);
    }

}
