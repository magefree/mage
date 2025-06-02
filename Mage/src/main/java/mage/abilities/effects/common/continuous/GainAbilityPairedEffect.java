
package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author noxx
 */
public class GainAbilityPairedEffect extends ContinuousEffectImpl {

    protected Ability ability;

    public GainAbilityPairedEffect(Ability ability, String rule) {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
        staticText = rule;
    }

    protected GainAbilityPairedEffect(final GainAbilityPairedEffect effect) {
        super(effect);
        this.ability = effect.ability.copy();
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            Permanent paired = Optional.ofNullable(permanent.getPairedCard())
                    .map(pairedCard -> pairedCard.getPermanent(game))
                    .orElse(null);
            if (paired != null) {
                return Arrays.asList(permanent, paired);
            }
        }
        return Collections.emptyList();
    }

    @Override
    public GainAbilityPairedEffect copy() {
        return new GainAbilityPairedEffect(this);
    }
}
