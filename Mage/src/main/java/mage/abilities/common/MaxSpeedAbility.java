package mage.abilities.common;

import mage.MageItem;
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
import mage.util.CardUtil;

import java.util.Map;
import java.util.UUID;

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
    public MaxSpeedAbilityEffect copy() {
        return new MaxSpeedAbilityEffect(this);
    }

    @Override
    public Map<UUID, MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        if (ControllerSpeedCount.instance.calculate(game, source, null) < 4) {
            return affectedObjectMap;
        }
        if (!affectedObjectMap.isEmpty()) {
            return affectedObjectMap;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            affectedObjectMap.put(permanent.getId(), permanent);
        } else if (game.getCard(source.getSourceId()) != null) {
            Card card = game.getCard(source.getSourceId());
            affectedObjectMap.put(card.getId(), card);
        }
        return affectedObjectMap;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, Map<UUID, MageItem> objects) {
        for (MageItem object : objects.values()) {
            if (object instanceof Permanent) {
                ((Permanent) object).addAbility(ability, source.getSourceId(), game);
            } else {
                game.getState().addOtherAbility((Card) object, ability);
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (queryAffectedObjects(layer, source, game).isEmpty()) {
            return false;
        }
        applyToObjects(layer, sublayer, source, game, affectedObjectMap);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Max speed &mdash; " + CardUtil.getTextWithFirstCharUpperCase(ability.getRule());
    }
}
