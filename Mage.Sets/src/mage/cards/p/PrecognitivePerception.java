package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.condition.common.AddendumCondition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrecognitivePerception extends CardImpl {

    public PrecognitivePerception(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Draw three cards.
        // Addendum — If you cast this spell during your main phase, instead scry 3, then draw three cards.
        this.getSpellAbility().addEffect(new PrecognitivePerceptionEffect());
    }

    private PrecognitivePerception(final PrecognitivePerception card) {
        super(card);
    }

    @Override
    public PrecognitivePerception copy() {
        return new PrecognitivePerception(this);
    }
}

class PrecognitivePerceptionEffect extends OneShotEffect {

    PrecognitivePerceptionEffect() {
        super(Outcome.Benefit);
        staticText = "Draw three cards.<br><i>Addendum</i> &mdash; " +
                "If you cast this spell during your main phase, " +
                "instead scry 3, then draw three cards.";
    }

    private PrecognitivePerceptionEffect(final PrecognitivePerceptionEffect effect) {
        super(effect);
    }

    @Override
    public PrecognitivePerceptionEffect copy() {
        return new PrecognitivePerceptionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (AddendumCondition.instance.apply(game, source)) {
            controller.scry(3, source, game);
        }
        controller.drawCards(3, source, game);
        return true;
    }
}