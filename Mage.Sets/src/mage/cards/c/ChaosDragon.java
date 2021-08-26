package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 * @author TheElk801
 */
public final class ChaosDragon extends CardImpl {

    public ChaosDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Chaos Dragon attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // At the beginning of combat on your turn, each player rolls a d20. If one or more opponents had the highest result, Chaos Dragon can't attack those players or planeswalkers they control this combat.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new ChaosDragonEffect(), TargetController.YOU, false
        ));
    }

    private ChaosDragon(final ChaosDragon card) {
        super(card);
    }

    @Override
    public ChaosDragon copy() {
        return new ChaosDragon(this);
    }
}

class ChaosDragonEffect extends OneShotEffect {

    ChaosDragonEffect() {
        super(Outcome.Benefit);
        staticText = "each player rolls a d20. If one or more opponents had the highest result, " +
                "{this} can't attack those players or planeswalkers they control this combat";
    }

    private ChaosDragonEffect(final ChaosDragonEffect effect) {
        super(effect);
    }

    @Override
    public ChaosDragonEffect copy() {
        return new ChaosDragonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<Integer, Set<UUID>> playerMap = new HashMap<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                return false;
            }
            playerMap.computeIfAbsent(player.rollDice(outcome, source, game, 20), x -> new HashSet<>()).add(playerId);
        }
        int max = playerMap.keySet().stream().mapToInt(x -> x).max().orElse(0);
        game.addEffect(new ChaosDragonRestrictionEffect(playerMap.get(max)), source);
        return true;
    }
}

class ChaosDragonRestrictionEffect extends RestrictionEffect {

    private final Set<UUID> playerSet = new HashSet<>();

    public ChaosDragonRestrictionEffect(Set<UUID> playerSet) {
        super(Duration.EndOfCombat, Outcome.Benefit);
        this.playerSet.addAll(playerSet);
    }

    ChaosDragonRestrictionEffect(final ChaosDragonRestrictionEffect effect) {
        super(effect);
        this.playerSet.addAll(effect.playerSet);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.equals(source.getSourcePermanentIfItStillExists(game));
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (playerSet.contains(defenderId)) {
            return false;
        }
        Permanent planeswalker = game.getPermanent(defenderId);
        return planeswalker == null || !playerSet.contains(planeswalker.getControllerId());
    }

    @Override
    public ChaosDragonRestrictionEffect copy() {
        return new ChaosDragonRestrictionEffect(this);
    }
}
