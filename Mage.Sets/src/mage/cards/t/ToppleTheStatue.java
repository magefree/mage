package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ToppleTheStatue extends CardImpl {

    public ToppleTheStatue(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Tap target permanent. If it's an artifact, destroy it.
        // Draw a card.
        this.getSpellAbility().addEffect(new ToppleTheStatueEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private ToppleTheStatue(final ToppleTheStatue card) {
        super(card);
    }

    @Override
    public ToppleTheStatue copy() {
        return new ToppleTheStatue(this);
    }
}

class ToppleTheStatueEffect extends OneShotEffect {

    ToppleTheStatueEffect() {
        super(Outcome.Benefit);
        staticText = "Tap target permanent. If it's an artifact, destroy it.<br>Draw a card.";
    }

    private ToppleTheStatueEffect(final ToppleTheStatueEffect effect) {
        super(effect);
    }

    @Override
    public ToppleTheStatueEffect copy() {
        return new ToppleTheStatueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        permanent.tap(source, game);
        if (permanent.isArtifact(game)) {
            permanent.destroy(source, game, false);
        }
        return new DrawCardSourceControllerEffect(1).apply(game, source);
    }
}