
package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackYouAllEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OrzhovAdvokist extends CardImpl {

    public OrzhovAdvokist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, each player may put two +1/+1 counters on a creature they control. If a player does, creatures that player controls can't attack you or a planeswalker you control until your next turn.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new OrzhovAdvokistEffect()));
    }

    private OrzhovAdvokist(final OrzhovAdvokist card) {
        super(card);
    }

    @Override
    public OrzhovAdvokist copy() {
        return new OrzhovAdvokist(this);
    }
}

class OrzhovAdvokistEffect extends OneShotEffect {

    OrzhovAdvokistEffect() {
        super(Outcome.Benefit);
        this.staticText = "each player may put two +1/+1 counters on a creature they control. "
                + "If a player does, creatures that player controls can't attack you or planeswalkers you control until your next turn";
    }

    private OrzhovAdvokistEffect(final OrzhovAdvokistEffect effect) {
        super(effect);
    }

    @Override
    public OrzhovAdvokistEffect copy() {
        return new OrzhovAdvokistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        List<UUID> players = new ArrayList<>();
        List<UUID> creatures = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null || !player.chooseUse(outcome, "Put two +1/+1 counters on a creature you control?", source, game)) {
                continue;
            }
            Target target = new TargetControlledCreaturePermanent(0, 1);
            target.withNotTarget(true);
            target.withChooseHint("to add counters to");
            player.choose(outcome, target, source, game);
            Optional.ofNullable(target)
                    .map(Target::getFirstTarget)
                    .map(game::getPermanent)
                    .ifPresent(permanent -> {
                        creatures.add(permanent.getId());
                        players.add(player.getId());
                    });
        }
        for (UUID creatureId : creatures) {
            Permanent creature = game.getPermanent(creatureId);
            if (creature != null) {
                creature.addCounters(CounterType.P1P1.createInstance(2), creature.getControllerId(), source, game);
            }
        }
        for (UUID playerId : players) {
            if (source.isControlledBy(playerId)) {
                continue;
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new ControllerIdPredicate(playerId));
            game.addEffect(new CantAttackYouAllEffect(Duration.UntilYourNextTurn, filter, true), source);
        }
        return true;
    }
}
