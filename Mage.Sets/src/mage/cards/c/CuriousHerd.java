package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.BeastToken;
import mage.game.permanent.token.Token;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CuriousHerd extends CardImpl {

    public CuriousHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{G}");

        // Choose target opponent. You create X 3/3 green Beast creature tokens, where X is the number of artifacts that player controls.
        this.getSpellAbility().addEffect(new CuriousHerdEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private CuriousHerd(final CuriousHerd card) {
        super(card);
    }

    @Override
    public CuriousHerd copy() {
        return new CuriousHerd(this);
    }
}

class CuriousHerdEffect extends OneShotEffect {

    private static final Token token = new BeastToken();

    CuriousHerdEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target opponent. You create X 3/3 green Beast creature tokens, " +
                "where X is the number of artifacts that player controls.";
    }

    private CuriousHerdEffect(final CuriousHerdEffect effect) {
        super(effect);
    }

    @Override
    public CuriousHerdEffect copy() {
        return new CuriousHerdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int artifactCount = game.getBattlefield().countAll(
                StaticFilters.FILTER_PERMANENT_ARTIFACT, source.getFirstTarget(), game
        );
        if (artifactCount > 0) {
            token.putOntoBattlefield(artifactCount, game, source, source.getControllerId());
        }
        return true;
    }
}