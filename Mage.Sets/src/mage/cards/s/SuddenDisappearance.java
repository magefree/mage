package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class SuddenDisappearance extends CardImpl {

    public SuddenDisappearance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{W}");

        // Exile all nonland permanents target player controls. Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new SuddenDisappearanceEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    private SuddenDisappearance(final SuddenDisappearance card) {
        super(card);
    }

    @Override
    public SuddenDisappearance copy() {
        return new SuddenDisappearance(this);
    }
}

class SuddenDisappearanceEffect extends OneShotEffect {

    SuddenDisappearanceEffect() {
        super(Outcome.Exile);
        staticText = "Exile all nonland permanents target player controls. Return the exiled cards to the battlefield under their owner's control at the beginning of the next end step";
    }

    private SuddenDisappearanceEffect(final SuddenDisappearanceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player == null) {
            return false;
        }
        Cards targets = new CardsImpl(game.getBattlefield().getActivePermanents(StaticFilters.FILTER_PERMANENTS_NON_LAND, player.getId(), game));
        if (targets.isEmpty()) {
            return false;
        }
        Effect effect = new ExileReturnBattlefieldNextEndStepTargetEffect().returnExiledOnly(true);
        effect.setTargetPointer(new FixedTargets(targets, game));
        return effect.apply(game, source);
    }

    @Override
    public SuddenDisappearanceEffect copy() {
        return new SuddenDisappearanceEffect(this);
    }

}
