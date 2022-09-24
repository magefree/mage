package mage.cards.w;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class WebOfInertia extends CardImpl {

    public WebOfInertia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of combat on each opponent's turn, that player may exile a card from their graveyard. If the player doesn't, creatures they control can't attack you this turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(Zone.BATTLEFIELD, new WebOfInertiaEffect(), TargetController.OPPONENT, false, true));
    }

    private WebOfInertia(final WebOfInertia card) {
        super(card);
    }

    @Override
    public WebOfInertia copy() {
        return new WebOfInertia(this);
    }
}

class WebOfInertiaEffect extends OneShotEffect {

    public WebOfInertiaEffect() {
        super(Outcome.Detriment);
        this.staticText = "that player may exile a card from their graveyard. If the player doesn't, creatures they control can't attack you this turn";
    }

    public WebOfInertiaEffect(final WebOfInertiaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            Cost cost = new ExileFromGraveCost(new TargetCardInYourGraveyard());
            if (cost.canPay(source, source, player.getId(), game) && player.chooseUse(Outcome.Detriment, "Exile a card from your graveyard?", source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, player.getId(), false, null)) {
                    if (!game.isSimulation()) {
                        game.informPlayers(player.getLogName() + " pays the cost to prevent the effect");
                    }
                }
            } else {
                game.addEffect(new WebOfInertiaRestrictionEffect(player.getId()), source);
            }
            return true;
        }
        return false;
    }

    @Override
    public WebOfInertiaEffect copy() {
        return new WebOfInertiaEffect(this);
    }
}

class WebOfInertiaRestrictionEffect extends RestrictionEffect {

    private final UUID attackerID;

    public WebOfInertiaRestrictionEffect(UUID attackerID) {
        super(Duration.EndOfTurn);
        this.attackerID = attackerID;
    }

    public WebOfInertiaRestrictionEffect(final WebOfInertiaRestrictionEffect effect) {
        super(effect);
        this.attackerID = effect.attackerID;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isControlledBy(attackerID);
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null) {
            return true;
        }
        return !defenderId.equals(source.getControllerId());
    }

    @Override
    public WebOfInertiaRestrictionEffect copy() {
        return new WebOfInertiaRestrictionEffect(this);
    }

}
