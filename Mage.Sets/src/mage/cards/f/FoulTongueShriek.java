package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.AttackingCreatureCount;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FoulTongueShriek extends CardImpl {

    public FoulTongueShriek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Target opponent loses 1 life for each attacking creature you control. You gain that much life.
        this.getSpellAbility().addEffect(new FoulTongueShriekEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());

    }

    private FoulTongueShriek(final FoulTongueShriek card) {
        super(card);
    }

    @Override
    public FoulTongueShriek copy() {
        return new FoulTongueShriek(this);
    }
}

class FoulTongueShriekEffect extends OneShotEffect {

    public FoulTongueShriekEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent loses 1 life for each attacking creature you control. You gain that much life";
    }

    private FoulTongueShriekEffect(final FoulTongueShriekEffect effect) {
        super(effect);
    }

    @Override
    public FoulTongueShriekEffect copy() {
        return new FoulTongueShriekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetOpponent != null) {
            int amount = new AttackingCreatureCount(StaticFilters.FILTER_PERMANENT_CREATURES_CONTROLLED).calculate(game, source, this);
            if (amount > 0) {
                targetOpponent.loseLife(amount, game, source, false);
                controller.gainLife(amount, game, source);
            }
            return true;
        }
        return false;
    }
}
