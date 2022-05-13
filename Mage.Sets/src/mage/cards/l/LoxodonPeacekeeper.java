
package mage.cards.l;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author L_J
 */
public final class LoxodonPeacekeeper extends CardImpl {

    public LoxodonPeacekeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.SOLDIER);        
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, the player with the lowest life total gains control of Loxodon Peacekeeper. If two or more players are tied for lowest life total, you choose one of them, and that player gains control of Loxodon Peacekeeper.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoxodonPeacekeeperEffect(), TargetController.YOU, false));

    }

    private LoxodonPeacekeeper(final LoxodonPeacekeeper card) {
        super(card);
    }

    @Override
    public LoxodonPeacekeeper copy() {
        return new LoxodonPeacekeeper(this);
    }
}

class LoxodonPeacekeeperEffect extends OneShotEffect {

    public LoxodonPeacekeeperEffect() {
        super(Outcome.Benefit);
        this.staticText = "the player with the lowest life total gains control of {this}. If two or more players are tied for lowest life total, you choose one of them, and that player gains control of {this}";
    }

    public LoxodonPeacekeeperEffect(final LoxodonPeacekeeperEffect effect) {
        super(effect);
    }

    @Override
    public LoxodonPeacekeeperEffect copy() {
        return new LoxodonPeacekeeperEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null) {
                int lowLife = Integer.MAX_VALUE;
                Set<UUID> tiedPlayers = new HashSet<>();
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (player.getLife() < lowLife) {
                            lowLife = player.getLife();
                        }
                    }
                }
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        if (player.getLife() == lowLife) {
                            tiedPlayers.add(playerId);
                        }
                    }
                }
                
                if (tiedPlayers.size() > 0) {
                    UUID newControllerId = null;
                    if (tiedPlayers.size() > 1) {
                        FilterPlayer filter = new FilterPlayer("a player tied for lowest life total");
                        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                            if (!tiedPlayers.contains(playerId)) {
                                filter.add(Predicates.not(new PlayerIdPredicate(playerId)));
                            }
                        }
                        TargetPlayer target = new TargetPlayer(1, 1, true, filter);
                        if (target.canChoose(controller.getId(), source, game)) {
                            while (!target.isChosen() && target.canChoose(controller.getId(), source, game) && controller.canRespond()) {
                                controller.chooseTarget(outcome, target, source, game);
                            }
                        } else {
                            return false;
                        }
                        newControllerId = game.getPlayer(target.getFirstTarget()).getId();
                    } else {
                        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                            if (tiedPlayers.contains(playerId)) {
                                newControllerId = playerId;
                                break;
                            }
                        }
                    }
                    if (newControllerId != null) {
                        ContinuousEffect effect = new GainControlTargetEffect(Duration.Custom, newControllerId);
                        effect.setTargetPointer(new FixedTarget(sourcePermanent, game));
                        game.addEffect(effect, source);
                        game.informPlayers(game.getPlayer(newControllerId).getLogName() + " has gained control of " + sourcePermanent.getLogName());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
