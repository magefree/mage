package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PopulateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @author TheElk801
 */
public final class FullFlowering extends CardImpl {

    public FullFlowering(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{G}");

        // Populate X times.
        this.getSpellAbility().addEffect(new FullFloweringEffect());
    }

    private FullFlowering(final FullFlowering card) {
        super(card);
    }

    @Override
    public FullFlowering copy() {
        return new FullFlowering(this);
    }
}

class FullFloweringEffect extends OneShotEffect {

    private static final Effect effect = new PopulateEffect();

    FullFloweringEffect() {
        super(Outcome.Benefit);
        staticText = "populate X times";
    }

    private FullFloweringEffect(final FullFloweringEffect effect) {
        super(effect);
    }

    @Override
    public FullFloweringEffect copy() {
        return new FullFloweringEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        if (xValue == 0) {
            return true;
        }
        IntStream.range(0, xValue).forEach(i -> effect.apply(game, source));
        return true;
    }
}