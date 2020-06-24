package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThoughtCollapse extends CardImpl {

    public ThoughtCollapse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");

        // Counter target spell. Its controller puts the top three cards of their library into their graveyard.
        this.getSpellAbility().addEffect(new ThoughtCollapseEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    private ThoughtCollapse(final ThoughtCollapse card) {
        super(card);
    }

    @Override
    public ThoughtCollapse copy() {
        return new ThoughtCollapse(this);
    }
}

class ThoughtCollapseEffect extends OneShotEffect {

    private static final Effect effect = new CounterTargetEffect();

    ThoughtCollapseEffect() {
        super(Outcome.Benefit);
        staticText = "Counter target spell. Its controller mills three cards";
    }

    private ThoughtCollapseEffect(final ThoughtCollapseEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtCollapseEffect copy() {
        return new ThoughtCollapseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getControllerId(source.getFirstTarget()));
        if (player == null) {
            return false;
        }
        player.millCards(3, source, game);
        return effect.apply(game, source);
    }
}
