package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SaddledCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.Optional;

/**
 * @author TheElk801
 */
public class SaddleAbility extends SimpleActivatedAbility {

    private final int value;
    private static final Hint hint = new ConditionHint(SaddledCondition.instance, "This permanent is saddled");

    public SaddleAbility(int value) {
        super(new SaddleEffect(), new GenericManaCost(0));
        this.value = value;
        this.addHint(hint);
    }

    private SaddleAbility(final SaddleAbility ability) {
        super(ability);
        this.value = ability.value;
    }

    @Override
    public SaddleAbility copy() {
        return new SaddleAbility(this);
    }

    @Override
    public String getRule() {
        return "Saddle " + value;
    }
}

class SaddleEffect extends OneShotEffect {

    SaddleEffect() {
        super(Outcome.Benefit);
    }

    private SaddleEffect(final SaddleEffect effect) {
        super(effect);
    }

    @Override
    public SaddleEffect copy() {
        return new SaddleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(permanent -> permanent.setSaddled(true));
        return true;
    }
}
