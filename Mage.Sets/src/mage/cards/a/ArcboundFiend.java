
package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.ModularAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author TheElk801
 */
public final class ArcboundFiend extends CardImpl {

    public ArcboundFiend(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Fear
        this.addAbility(FearAbility.getInstance());

        // At the beginning of your upkeep, you may move a +1/+1 counter from target creature onto Arcbound Fiend.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new MoveCounterFromTargetToSourceEffect(), TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Modular 3
        this.addAbility(new ModularAbility(this, 3));
    }

    private ArcboundFiend(final ArcboundFiend card) {
        super(card);
    }

    @Override
    public ArcboundFiend copy() {
        return new ArcboundFiend(this);
    }
}

class MoveCounterFromTargetToSourceEffect extends OneShotEffect {

    public MoveCounterFromTargetToSourceEffect() {
        super(Outcome.Detriment);
        this.staticText = "move a +1/+1 counter from target creature onto {this}";
    }

    public MoveCounterFromTargetToSourceEffect(final MoveCounterFromTargetToSourceEffect effect) {
        super(effect);
    }

    @Override
    public MoveCounterFromTargetToSourceEffect copy() {
        return new MoveCounterFromTargetToSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Permanent fromPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (fromPermanent != null && fromPermanent.getCounters(game).getCount(CounterType.P1P1) > 0) {
                fromPermanent.removeCounters(CounterType.P1P1.createInstance(), source, game);
                sourceObject.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
                game.informPlayers("Moved a +1/+1 counter from " + fromPermanent.getLogName() + " to " + sourceObject.getLogName());
            }
            return true;
        }
        return false;
    }
}
