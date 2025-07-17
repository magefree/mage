package mage.abilities.effects.common.ruleModifying;

import mage.MageItem;
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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CombatDamageByToughnessTargetEffect extends ContinuousEffectImpl {

    public CombatDamageByToughnessTargetEffect() {
        this(Duration.EndOfTurn);
    }

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
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        List<Permanent> permanents = affectedObjects.stream().map(Permanent.class::cast).collect(Collectors.toList());
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new PermanentReferenceInCollectionPredicate(permanents, game));
        game.getCombat().setUseToughnessForDamage(true);
        game.getCombat().addUseToughnessForDamageFilter(filter);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        affectedObjects.addAll(getTargetPointer().getTargets(game, source).stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
        return !affectedObjects.isEmpty();
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return (duration.toString().isEmpty() ? "" : duration + ", ")
                + getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + " assigns combat damage equal to its toughness rather than its power";
    }

}
