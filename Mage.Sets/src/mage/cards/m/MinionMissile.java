package mage.cards.m;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class MinionMissile extends CardImpl {

    public MinionMissile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // As an additional cost to cast this spell, sacrifice a creature or discard a card.
        this.getSpellAbility().addCost(new OrCost(
            "sacrifice a creature or discard a card",
            new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE),
            new DiscardCardCost()
        ));

        // Destroy target creature. Minion Missile deals 2 damage to that creature's controller.
        this.getSpellAbility().addEffect(new MinionMissileEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private MinionMissile(final MinionMissile card) {
        super(card);
    }

    @Override
    public MinionMissile copy() {
        return new MinionMissile(this);
    }
}

class MinionMissileEffect extends OneShotEffect {

    MinionMissileEffect() {
        super(Outcome.Benefit);
        staticText = "Destroy target creature. {this} deals 2 damage to that creature's controller.";
    }

    private MinionMissileEffect(final MinionMissileEffect effect) {
        super(effect);
    }

    @Override
    public MinionMissileEffect copy() {
        return new MinionMissileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        permanent.destroy(source, game, false);
        player.damage(2, source.getSourceId(), source, game);
        return true;
    }
}
