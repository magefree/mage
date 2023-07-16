package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainOfPlasma extends CardImpl {

    public ChainOfPlasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Chain of Plasma deals 3 damage to any target. Then that player or that creature's controller may discard a card. If the player does, they may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainOfPlasmaEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ChainOfPlasma(final ChainOfPlasma card) {
        super(card);
    }

    @Override
    public ChainOfPlasma copy() {
        return new ChainOfPlasma(this);
    }
}

class ChainOfPlasmaEffect extends OneShotEffect {

    ChainOfPlasmaEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 3 damage to any target. Then that player or that permanent's controller may discard a card. If the player does, they may copy this spell and may choose a new target for that copy.";
    }

    ChainOfPlasmaEffect(final ChainOfPlasmaEffect effect) {
        super(effect);
    }

    @Override
    public ChainOfPlasmaEffect copy() {
        return new ChainOfPlasmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = source.getFirstTarget();
            Player affectedPlayer = null;
            Player player = game.getPlayer(targetId);
            if (player != null) {
                player.damage(3, source.getSourceId(), source, game);
                affectedPlayer = player;
            } else {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.damage(3, source.getSourceId(), source, game, false, true);
                    affectedPlayer = game.getPlayer(permanent.getControllerId());
                }
            }
            if (affectedPlayer != null) {
                if (affectedPlayer.chooseUse(Outcome.Copy, "Discard a card to copy the spell?", source, game)) {
                    Cost cost = new DiscardCardCost();
                    if (cost.pay(source, game, source, affectedPlayer.getId(), false, null)) {
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            spell.createCopyOnStack(game, source, affectedPlayer.getId(), true);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
