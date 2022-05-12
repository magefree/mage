
package mage.cards.r;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.Target;
import mage.target.Targets;
import mage.util.TargetAddress;

/**
 *
 * @author L_J
 */
public final class Ricochet extends CardImpl {
    
    protected static final FilterSpell filter = new FilterSpell("a spell that targets a single player");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
        filter.add(new SpellWithOnlyPlayerTargetsPredicate());
    }

    public Ricochet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{R}");

        // Whenever a player casts a spell that targets a single player, each player rolls a six-sided die. Change the target of that spell to the player with the lowest result. Reroll to break ties, if necessary.
        this.addAbility(new SpellCastAllTriggeredAbility(new RicochetEffect(), filter, false, SetTargetPointer.SPELL));
    }

    private Ricochet(final Ricochet card) {
        super(card);
    }

    @Override
    public Ricochet copy() {
        return new Ricochet(this);
    }
}

class SpellWithOnlyPlayerTargetsPredicate implements ObjectSourcePlayerPredicate<Spell> {

    @Override
    public boolean apply(ObjectSourcePlayer<Spell> input, Game game) {
        Spell spell = input.getObject();
        if (spell == null) {
            return false;
        }
        for (TargetAddress addr : TargetAddress.walk(spell)) {
            Target targetInstance = addr.getTarget(spell);
            for (UUID targetId : targetInstance.getTargets()) {
                if (game.getPlayer(targetId) == null) {
                    return false;
                }
            }
        }
        return true;
    }
}

class RicochetEffect extends OneShotEffect {

    public RicochetEffect() {
        super(Outcome.Detriment);
        staticText = "each player rolls a six-sided die. Change the target of that spell to the player with the lowest result. Reroll to break ties, if necessary";
    }

    public RicochetEffect(final RicochetEffect effect) {
        super(effect);
    }

    @Override
    public RicochetEffect copy() {
        return new RicochetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(this.getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Targets targets = new Targets();
            Ability sourceAbility = spell.getSpellAbility();
            for (UUID modeId : sourceAbility.getModes().getSelectedModes()) {
                Mode mode = sourceAbility.getModes().get(modeId);
                targets.addAll(mode.getTargets());
            }
            if (targets.size() != 1 || targets.get(0).getTargets().size() != 1) {
                return false;
            }

            Map<Player, Integer> playerRolls = new HashMap<>();
            for (UUID playerId : game.getPlayerList().copy()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    playerRolls.put(player, 7);
                }
            }
            do {
                for (Player player : playerRolls.keySet()) {
                    playerRolls.put(player, player.rollDice(Outcome.Detriment, source, game, 6)); // bad outcome - ai must choose lowest value
                }
                int minValueInMap = Collections.min(playerRolls.values());
                for (Map.Entry<Player, Integer> mapEntry : new HashSet<>(playerRolls.entrySet())) {
                    if (mapEntry.getValue() > minValueInMap) {
                        playerRolls.remove(mapEntry.getKey());
                    }
                }
            } while (playerRolls.size() > 1);
            
            if (playerRolls.size() == 1) {
                Player loser = (Player) playerRolls.keySet().toArray()[0];
                UUID loserId = loser.getId();
                Target target = targets.get(0);
                if (target.getFirstTarget().equals(loserId)) {
                    return true;
                }
                String oldTargetName = null;
                if (target.canTarget(spell.getControllerId(), loserId, sourceAbility, game)) {
                    Player oldPlayer = game.getPlayer(targets.getFirstTarget());
                    if (oldPlayer != null) {
                        oldTargetName = oldPlayer.getLogName();
                    }
                    target.clearChosen();
                    target.addTarget(loserId, sourceAbility, game);
                }
                MageObject sourceObject = game.getObject(source);
                if (oldTargetName != null && sourceObject != null) {
                    game.informPlayers(sourceObject.getLogName() + ": Changed target of " + spell.getLogName() + " from " + oldTargetName + " to " + loser.getLogName());
                }
            }
            return true;
        }
        return false;
    }
}
