package mage.abilities.effects.common.ruleModifying;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CombatDamageByToughnessTargetEffect extends ContinuousEffectImpl {
    
    public CombatDamageByToughnessTargetEffect(Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Neutral);
    }

    private CombatDamageByToughnessTargetEffect(final CombatDamageByToughnessTargetEffect effect) {
        super(effect);
    }

    @Override
    public CombatDamageByToughnessTargetEffect copy() {
        return new CombatDamageByToughnessTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Permanent> set = targetPointer.getTargets(game, source).stream()
            .map(game::getPermanent)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PermanentReferenceInCollectionPredicate(set, game));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);

        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return (duration.toString().isEmpty() ? "" : duration.toString() + ", ")
                + getTargetPointer().describeTargets(mode.getTargets(), "that creature")
            + " assigns combat damage equal to its toughness rather than its power";
    }

}
