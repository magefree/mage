package mage.abilities.common;

import mage.*;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.StaticAbility;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author TheElk801
 */
public class MaxSpeedAbility extends StaticAbility {

    public MaxSpeedAbility(Effect effect) {
        this(new SimpleStaticAbility(effect));
    }

    public MaxSpeedAbility(Ability ability) {
        super(ability.getZone(), new MaxSpeedAbilityEffect(ability));
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

    private static Duration getDuration(Ability ability) {
        switch (ability.getZone()) {
            case BATTLEFIELD:
                return Duration.WhileOnBattlefield;
            case GRAVEYARD:
                return Duration.WhileInGraveyard;
            default:
                return Duration.Custom;
        }
    }

    MaxSpeedAbilityEffect(Ability ability) {
        super(getDuration(ability), Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        this.ability.setRuleVisible(false);
    }

    private MaxSpeedAbilityEffect(final MaxSpeedAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        if (ControllerSpeedCount.instance.calculate(game, source, null) < 4) {
            return false;
        }
        for (MageObject object : objects) {
            if (object instanceof Permanent) {
                ((Permanent) object).addAbility(ability, source.getSourceId(), game);
                continue;
            }
            if (object instanceof Card) {
                game.getState().addOtherAbility((Card) object, ability);
            }
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        MageObject object = game.getPermanent(source.getSourceId());
        if (object == null) {
            object = game.getCard(source.getSourceId());
        }
        return object != null ? Collections.singletonList(object) : Collections.emptyList();
    }

    @Override
    public MaxSpeedAbilityEffect copy() {
        return new MaxSpeedAbilityEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        return "Max speed &mdash; " + CardUtil.getTextWithFirstCharUpperCase(ability.getRule());
    }
}
