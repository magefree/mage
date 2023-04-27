package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
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
 * @author emerald000
 */
public final class ChainLightning extends CardImpl {

    public ChainLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{R}");

        // Chain Lightning deals 3 damage to any target. Then that player or that creature's controller may pay {R}{R}. If the player does, they may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainLightningEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private ChainLightning(final ChainLightning card) {
        super(card);
    }

    @Override
    public ChainLightning copy() {
        return new ChainLightning(this);
    }
}

class ChainLightningEffect extends OneShotEffect {

    ChainLightningEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 3 damage to any target. Then that player or that permanent's controller may pay {R}{R}. If the player does, they may copy this spell and may choose a new target for that copy";
    }

    ChainLightningEffect(final ChainLightningEffect effect) {
        super(effect);
    }

    @Override
    public ChainLightningEffect copy() {
        return new ChainLightningEffect(this);
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
                if (affectedPlayer.chooseUse(Outcome.Copy, "Pay {R}{R} to copy the spell?", source, game)) {
                    Cost cost = new ManaCostsImpl<>("{R}{R}");
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
