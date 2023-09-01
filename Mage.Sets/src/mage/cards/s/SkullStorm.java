package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.CommanderStormAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public final class SkullStorm extends CardImpl {

    public SkullStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{7}{B}{B}");

        // When you cast this spell, copy it for each time you've cast your commander from the command zone this game.
        this.addAbility(new CommanderStormAbility());

        // Each opponent sacrifices a creature. Each opponent who can't loses half their life, rounded up.
        this.getSpellAbility().addEffect(new SkullStormEffect());
    }

    private SkullStorm(final SkullStorm card) {
        super(card);
    }

    @Override
    public SkullStorm copy() {
        return new SkullStorm(this);
    }
}

class SkullStormEffect extends OneShotEffect {

    public SkullStormEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent sacrifices a creature. "
                + "Each opponent who can't loses half their life, rounded up.";
    }

    private SkullStormEffect(final SkullStormEffect effect) {
        super(effect);
    }

    @Override
    public SkullStormEffect copy() {
        return new SkullStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.getOpponents(source.getControllerId()).forEach((playerId) -> {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                FilterPermanent filter = new FilterCreaturePermanent();
                filter.add(new ControllerIdPredicate(playerId));
                if (game.getBattlefield().getActivePermanents(
                        filter, source.getControllerId(), game
                ).isEmpty()) {
                    int lifeToLose = (int) Math.ceil(player.getLife() / 2f);
                    player.loseLife(lifeToLose, game, source, false);
                } else {
                    Effect effect = new SacrificeEffect(
                            StaticFilters.FILTER_PERMANENT_CREATURE, 1, null
                    );
                    effect.setTargetPointer(new FixedTarget(playerId, game));
                    effect.apply(game, source);
                }
            }
        });
        return true;
    }
}
//doot doot
