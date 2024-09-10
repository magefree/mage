package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.condition.common.RevealedOrControlledDragonCondition;
import mage.abilities.costs.common.RevealDragonFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.DragonOnTheBattlefieldWhileSpellWasCastWatcher;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DraconicRoar extends CardImpl {

    public DraconicRoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // As an additional cost to cast Draconic Roar, you may reveal a Dragon card from your hand.
        this.getSpellAbility().addCost(new RevealDragonFromHandCost());

        // Draconic Roar deals 3 damage to target creature. If you revealed a Dragon card or controlled a Dragon as you cast Draconic Roar, Draconic Roar deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new DraconicRoarEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addWatcher(new DragonOnTheBattlefieldWhileSpellWasCastWatcher());
    }

    private DraconicRoar(final DraconicRoar card) {
        super(card);
    }

    @Override
    public DraconicRoar copy() {
        return new DraconicRoar(this);
    }
}

class DraconicRoarEffect extends OneShotEffect {

    DraconicRoarEffect() {
        super(Outcome.Benefit);
        staticText = "{this} deals 3 damage to target creature. If you revealed a Dragon card or controlled " +
                "a Dragon as you cast this spell, {this} deals 3 damage to that creature's controller.";
    }

    private DraconicRoarEffect(final DraconicRoarEffect effect) {
        super(effect);
    }

    @Override
    public DraconicRoarEffect copy() {
        return new DraconicRoarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.damage(3, source.getSourceId(), source, game);
        if (!RevealedOrControlledDragonCondition.instance.apply(game, source)) {
            return true;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player != null) {
            player.damage(3, source.getSourceId(), source, game);
        }
        return true;
    }
}
