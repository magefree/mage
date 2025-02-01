package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.StaticAbility;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class MaxSpeedAbility extends StaticAbility {

    public MaxSpeedAbility(Effect effect) {
        this(new SimpleStaticAbility(effect));
    }

    public MaxSpeedAbility(Ability ability) {
        super(Zone.ALL, new MaxSpeedAbilityEffect(ability));
    }

    private MaxSpeedAbility(final MaxSpeedAbility ability) {
        super(ability);
    }

    @Override
    public MaxSpeedAbility copy() {
        return new MaxSpeedAbility(this);
    }
}

class MaxSpeedAbilityEffect extends ContinuousEffectImpl {

    private final Ability ability;

    MaxSpeedAbilityEffect(Ability ability) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
    }

    private MaxSpeedAbilityEffect(final MaxSpeedAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability;
    }

    @Override
    public MaxSpeedAbilityEffect copy() {
        return new MaxSpeedAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (ControllerSpeedCount.instance.calculate(game, source, null) < 4) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            permanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        Card card = game.getCard(source.getSourceId());
        if (card == null) {
            return false;
        }
        game.getState().addOtherAbility(card, ability);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Max speed &mdash; " + ability.getRule();
    }
}
