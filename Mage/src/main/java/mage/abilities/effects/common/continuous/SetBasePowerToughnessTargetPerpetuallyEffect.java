package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.PerpetuallyEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.*;

/**
 * @author karapuzz14
 */
public class SetBasePowerToughnessTargetPerpetuallyEffect extends ContinuousEffectImpl implements PerpetuallyEffect {

    private final DynamicValue power;
    private final DynamicValue toughness;

    private HashMap<UUID, Boolean> morphedMap  = new HashMap<>();


    public SetBasePowerToughnessTargetPerpetuallyEffect(DynamicValue power, DynamicValue toughness) {
        super(Duration.Perpetually, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.Neutral);
        this.power = power;
        this.toughness = toughness;
    }

    public SetBasePowerToughnessTargetPerpetuallyEffect(int power, int toughness) {
        this(StaticValue.get(power), StaticValue.get(toughness));
    }

    protected SetBasePowerToughnessTargetPerpetuallyEffect(final SetBasePowerToughnessTargetPerpetuallyEffect effect) {
        super(effect);
        this.power = effect.power;
        this.toughness = effect.toughness;
        this.morphedMap = effect.morphedMap;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        List<UUID> targetList = getTargetPointer().getTargets(game, source);

        targetList.stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(card -> {
                    MageObjectReference cardReference = new MageObjectReference(card, game);
                    this.affectedObjectList.add(cardReference);

                    if(card.isPermanent(game) && game.getPermanent(card.getId()) != null) {
                        morphedMap.put(card.getId(), true);
                    }
                    else {
                        morphedMap.put(card.getId(), false);
                    }
                });

    }
    @Override
    public SetBasePowerToughnessTargetPerpetuallyEffect copy() {
        return new SetBasePowerToughnessTargetPerpetuallyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean result = false;
        for (UUID targetId : morphedMap.keySet()) {
            Card target = game.getCard(targetId);
            if (target == null) {
                continue;
            }
            //TODO: not finished morph?
            if (power != null) {
                int currentPower = power.calculate(game, source, this);
                target.getPower().setModifiedBaseValue(currentPower);
                target.addInfo("basePowerPerpetualChange", "<font color=#9A4FFE>"+ target.getName() + "'s base power perpetually becomes " + currentPower +".</font>", game);
                if(target.isPermanent()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        if(permanent.isFaceDown(game) && !morphedMap.get(targetId)) {
                            return result;
                        }
                        permanent.getPower().setModifiedBaseValue(currentPower);
                    }
                }
            }
            if (toughness != null) {
                int currentToughness = toughness.calculate(game, source, this);
                target.getToughness().setModifiedBaseValue(currentToughness);
                target.addInfo("baseToughnessPerpetualChange", "<font color=#9A4FFE>"+ target.getName() + "'s base toughness perpetually becomes " + currentToughness +".</font>", game);
                if(target.isPermanent()) {
                    Permanent permanent = game.getPermanent(targetId);
                    if (permanent != null) {
                        if(permanent.isFaceDown(game) && !morphedMap.get(targetId)) {
                            return result;
                        }
                        permanent.getToughness().setModifiedBaseValue(currentToughness);
                    }
                }

            }
            result = true;
        }
        return result;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + (getTargetPointer().isPlural(mode.getTargets()) ? " have" : " has")
                + " base power and toughness " + power + '/' + toughness
                + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }

    @Override
    public boolean affectsCard(Card card, Game game) {
        for(UUID target : morphedMap.keySet()) {
            if(card.isPermanent(game)) {
                Permanent permanent = game.getPermanent(card.getId());
                if(permanent != null && permanent.getId().equals(target)) {
                    return true;
                }
            }
            if(card.getId().equals(target)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeTarget(Card card, Game game) {
        for(UUID target : morphedMap.keySet()) {
            Card gameCard = game.getCard(card.getId());
            if(gameCard != null && gameCard.getId().equals(target)) {
                if(power != null) {
                    gameCard.getPower().resetToBaseValue();
                }
                if(toughness != null) {
                    gameCard.getToughness().resetToBaseValue();
                }
            }
        }
        morphedMap.keySet().removeIf(target -> card.getId().equals(target));
        if(morphedMap.isEmpty()) {
            this.discard();
        }
    }

    @Override
    public void addTarget(Card card, Game game) {
        if(card.isPermanent(game) && game.getPermanent(card.getId()) != null) {
            morphedMap.put(card.getId(), true);
        }
        else {
            morphedMap.put(card.getId(), false);
        }

        MageObjectReference cardReference = new MageObjectReference(card, game);
        this.affectedObjectList.add(cardReference);
    }

    public boolean affectsPower() {
        return power != null;
    }

    public boolean affectsToughness() {
        return toughness != null;
    }
}
