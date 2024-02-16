package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterPlayer;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OathOfMages extends CardImpl {

    public OathOfMages(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        // At the beginning of each player's upkeep, that player chooses target player who has more life than they do and is their opponent. The first player may have Oath of Mages deal 1 damage to the second player.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new OathOfMagesEffect(), TargetController.ANY, false
        );
        ability.setTargetAdjuster(OathOfMagesAdjuster.instance);
        this.addAbility(ability);
    }

    private OathOfMages(final OathOfMages card) {
        super(card);
    }

    @Override
    public OathOfMages copy() {
        return new OathOfMages(this);
    }
}

enum OathOfMagesAdjuster implements TargetAdjuster {
    instance;
    private static final FilterPlayer filter = new FilterPlayer();

    static {
        filter.add(OathOfMagesPredicate.instance);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        Player activePlayer = game.getPlayer(game.getActivePlayerId());
        if (activePlayer == null) {
            return;
        }
        ability.getTargets().clear();
        TargetPlayer target = new TargetPlayer(1, 1, false, filter);
        target.setTargetController(activePlayer.getId());
        ability.getTargets().add(target);
    }
}

enum OathOfMagesPredicate implements ObjectSourcePlayerPredicate<Player> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Player> input, Game game) {
        Player targetPlayer = input.getObject();
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        if (targetPlayer == null
                || firstPlayer == null
                || !firstPlayer.hasOpponent(targetPlayer.getId(), game)) {
            return false;
        }
        int lifeTotalTargetPlayer = targetPlayer.getLife();
        int lifeTotalFirstPlayer = firstPlayer.getLife();

        return lifeTotalTargetPlayer > lifeTotalFirstPlayer;
    }

    @Override
    public String toString() {
        return "player who has more life than they do and is their opponent";
    }
}

class OathOfMagesEffect extends OneShotEffect {

    OathOfMagesEffect() {
        super(Outcome.Damage);
        staticText = "that player chooses target player who has more life than they do and is their opponent. " +
                "The first player may have {this} deal 1 damage to the second player";
    }

    private OathOfMagesEffect(OathOfMagesEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source);
        Player firstPlayer = game.getPlayer(game.getActivePlayerId());
        Player secondPlayer = game.getPlayer(source.getFirstTarget());
        if (sourceObject == null || firstPlayer == null) {
            return false;
        }
        if (firstPlayer.chooseUse(Outcome.Benefit, "Deal one damage to " + secondPlayer.getLogName() + "?", source, game)) {
            secondPlayer.damage(1, source.getSourceId(), source, game);
        }
        return true;
    }

    @Override
    public OathOfMagesEffect copy() {
        return new OathOfMagesEffect(this);
    }
}
