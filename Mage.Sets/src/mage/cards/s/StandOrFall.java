package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2 & L_J
 */
public final class StandOrFall extends CardImpl {

    public StandOrFall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");

        // At the beginning of combat on your turn, separate all creatures defending player controls into two piles. Only creatures in the pile of that player’s choice can block this turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new StandOrFallEffect(), TargetController.YOU, false));
    }

    private StandOrFall(final StandOrFall card) {
        super(card);
    }

    @Override
    public StandOrFall copy() {
        return new StandOrFall(this);
    }
}

class StandOrFallEffect extends OneShotEffect {

    public StandOrFallEffect() {
        super(Outcome.Detriment);
        this.staticText = "separate all creatures that player controls into two piles and that player chooses one. Only creatures in the chosen piles can block this turn";
    }

    public StandOrFallEffect(final StandOrFallEffect effect) {
        super(effect);
    }

    @Override
    public StandOrFallEffect copy() {
        return new StandOrFallEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // 802.2. As the combat phase starts, the attacking player doesn’t choose an opponent to become the defending player.
        // Instead, all the attacking player’s opponents are defending players during the combat phase.
        //
        // 802.2a Any rule, object, or effect that refers to a “defending player” refers to one specific defending player, not to all of the defending players. 
        // If an ability of an attacking creature refers to a defending player, or a spell or ability refers to both an attacking creature and a defending player, 
        // then unless otherwise specified, the defending player it’s referring to is the player that creature was attacking at the time it became an attacking 
        // creature that combat, or the controller of the planeswalker that creature was attacking at the time it became an attacking creature that combat. If a spell or ability 
        // could apply to multiple attacking creatures, the appropriate defending player is individually determined for each of those attacking creatures.
        // If there are multiple defending players that could be chosen, the controller of the spell or ability chooses one.
        //
        // https://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/756140-stand-or-fall-mechanics
        Player player = game.getPlayer(source.getControllerId());
        Set<UUID> opponents = game.getOpponents(source.getControllerId());
        if (!opponents.isEmpty()) {
            Player targetPlayer = game.getPlayer(opponents.iterator().next());
            if (opponents.size() > 1) {
                TargetOpponent targetOpponent = new TargetOpponent(true);
                if (player != null && player.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                    targetPlayer = game.getPlayer(targetOpponent.getFirstTarget());
                    game.informPlayers(player.getLogName() + " chose " + targetPlayer.getLogName() + " as the defending player");
                }
            }

            if (player != null && targetPlayer != null) {
                int count = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURES, targetPlayer.getId(), game);
                TargetCreaturePermanent creatures = new TargetCreaturePermanent(0, count, new FilterCreaturePermanent("creatures to put in the first pile"), true);
                List<Permanent> pile1 = new ArrayList<>();
                creatures.setRequired(false);
                if (player.choose(Outcome.Neutral, creatures, source, game)) {
                    List<UUID> targets = creatures.getTargets();
                    for (UUID targetId : targets) {
                        Permanent p = game.getPermanent(targetId);
                        if (p != null) {
                            pile1.add(p);
                        }
                    }
                }
                List<Permanent> pile2 = new ArrayList<>();
                for (Permanent p : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, targetPlayer.getId(), game)) {
                    if (!pile1.contains(p)) {
                        pile2.add(p);
                    }
                }

                boolean choice = targetPlayer.choosePile(outcome, "Choose which pile can block this turn.", pile1, pile2, game);
                List<Permanent> chosenPile = choice ? pile2 : pile1;
                List<Permanent> otherPile = choice ? pile1 : pile2;
                for (Permanent permanent : chosenPile) {
                    if (permanent != null) {
                        RestrictionEffect effect = new CantBlockTargetEffect(Duration.EndOfTurn);
                        effect.setText("");
                        effect.setTargetPointer(new FixedTarget(permanent, game));
                        game.addEffect(effect, source);
                    }
                }
                game.informPlayers("Creatures that can block this turn: " + otherPile.stream().map(Permanent::getLogName).collect(Collectors.joining(", ")));
                return true;
            }
        }
        return false;
    }
}
