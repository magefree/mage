package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.ProliferateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExpansionAlgorithm extends CardImpl {

    public ExpansionAlgorithm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{U}{U}");

        // Proliferate X times.
        this.getSpellAbility().addEffect(new ExpansionAlgorithmEffect());
    }

    private ExpansionAlgorithm(final ExpansionAlgorithm card) {
        super(card);
    }

    @Override
    public ExpansionAlgorithm copy() {
        return new ExpansionAlgorithm(this);
    }
}

class ExpansionAlgorithmEffect extends OneShotEffect {

    ExpansionAlgorithmEffect() {
        super(Outcome.Benefit);
        staticText = "proliferate X times";
    }

    private ExpansionAlgorithmEffect(final ExpansionAlgorithmEffect effect) {
        super(effect);
    }

    @Override
    public ExpansionAlgorithmEffect copy() {
        return new ExpansionAlgorithmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0);
        for (int i = 0; i < xValue; i++) {
            new ProliferateEffect().apply(game, source);
        }
        return xValue > 0;
    }
}
