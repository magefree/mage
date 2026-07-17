package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class Fractalize extends CardImpl {

    public Fractalize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{U}");

        // Until end of turn, target creature becomes a green and blue Fractal with base power and toughness each equal to X plus 1.
        this.getSpellAbility().addEffect(new FractalizeEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Fractalize(final Fractalize card) {
        super(card);
    }

    @Override
    public Fractalize copy() {
        return new Fractalize(this);
    }
}

class FractalizeEffect extends BecomesCreatureTargetEffect {

    FractalizeEffect() {
        super(
            new CreatureToken(0, 0, "green and blue Fractal with base power and toughness each equal to X plus 1")
                .withColor("GU").withSubType(SubType.FRACTAL),
            false, false, Duration.EndOfTurn
        );
        this.withDurationRuleAtStart(true);
        this.setRemoveSubtypes(true);
    }

    private FractalizeEffect(final FractalizeEffect effect) {
        super(effect);
    }

    @Override
    public FractalizeEffect copy() {
        return new FractalizeEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        int xValue = CardUtil.getSourceCostsTag(game, source, "X", 0) + 1;
        token.getPower().setModifiedBaseValue(xValue);
        token.getToughness().setModifiedBaseValue(xValue);
    }
}
