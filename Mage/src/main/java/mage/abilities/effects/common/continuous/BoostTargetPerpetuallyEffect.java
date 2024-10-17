package mage.abilities.effects.common.continuous;

import mage.MageInt;
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
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author karapuzz14
 */
public class BoostTargetPerpetuallyEffect extends ContinuousEffectImpl implements PerpetuallyEffect {

    private DynamicValue power;
    private DynamicValue toughness;

    private HashMap<UUID, targetData> targetMap = new HashMap<>();

    private class targetData {
        boolean isUsed;
        boolean wasPermanentAtInit;
        int lastPowerDiff;
        int lastToughnessDiff;

        public targetData() {
            isUsed = false;
        }

    }

    public BoostTargetPerpetuallyEffect(int power, int toughness) {
        this(StaticValue.get(power), StaticValue.get(toughness));
    }

    public BoostTargetPerpetuallyEffect(DynamicValue power, DynamicValue toughness) {
        super(Duration.Perpetually, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, CardUtil.getBoostOutcome(power, toughness));
        this.power = power;
        this.toughness = toughness;
    }

    protected BoostTargetPerpetuallyEffect(final BoostTargetPerpetuallyEffect effect) {
        super(effect);
        this.power = effect.power.copy();
        this.toughness = effect.toughness.copy();
        this.targetMap = new HashMap<>(effect.targetMap);
    }

    @Override
    public BoostTargetPerpetuallyEffect copy() {
        return new BoostTargetPerpetuallyEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        power = StaticValue.get(power.calculate(game, source, this));
        toughness = StaticValue.get(toughness.calculate(game, source, this));

        List<UUID> targetList = getTargetPointer().getTargets(game, source);
        for(UUID target : targetList) {
            targetMap.put(target, new targetData());
        }
        targetList.stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(card -> {
                    UUID cardId = card.getId();
                    MageObjectReference cardReference = new MageObjectReference(card, game);
                    this.affectedObjectList.add(cardReference);

                    targetMap.get(cardId).wasPermanentAtInit = card.isPermanent(game) && game.getPermanent(cardId) != null;
                });

    }

    @Override
    public boolean apply(Game game, Ability source) {

        int affectedTargets = 0;
        for (UUID target : targetMap.keySet()) {
            Card card = game.getCard(target);
            if(card != null) {

                MageInt cardPower = card.getPower();
                MageInt cardToughness = card.getToughness();

                int additionalPower = power.calculate(game, source, this);
                int additionalToughness = toughness.calculate(game, source, this);

                // applying effect to the card once
                if(!targetMap.get(target).isUsed) {
                    cardPower.increaseBoostedValue(additionalPower);
                    cardToughness.increaseBoostedValue(additionalToughness);
                    targetMap.get(target).isUsed = true;
                }

                // periodically applying effect to the permanent
                Permanent permanent = game.getPermanent(target);
                if (permanent != null && permanent.isCreature(game)) {
                    if(permanent.isFaceDown(game) && !targetMap.get(target).wasPermanentAtInit) {
                        return false;
                    }
                    if(!permanent.isFaceDown(game) || (permanent.isFaceDown(game) && targetMap.get(target).wasPermanentAtInit)) {
                        permanent.addPower(additionalPower);
                        permanent.addToughness(additionalToughness);
                        if(targetMap.get(target).isUsed) {
                            affectedTargets++;
                        }
                    }
                }


                // calculating PT for additional info
                NumberFormat plusMinusNF = new DecimalFormat("+#;-#");

                Integer powerDiff;
                Integer toughnessDiff;

                if(permanent != null && permanent.isFaceDown(game) && targetMap.get(target).wasPermanentAtInit) {
                    powerDiff = 0;
                    toughnessDiff = 0;

                    List<BoostTargetPerpetuallyEffect> boostPerpetualEffectsOnCard = game.getContinuousEffects()
                            .getLayeredEffects(game).stream()
                            .filter(effect -> effect instanceof BoostTargetPerpetuallyEffect)
                            .map(effect -> (BoostTargetPerpetuallyEffect) effect)
                            .filter(effect -> effect.affectsCard(card, game))
                            .filter(effect -> targetMap.get(target).wasPermanentAtInit)
                            .collect(Collectors.toList());

                    for(BoostTargetPerpetuallyEffect effect : boostPerpetualEffectsOnCard) {
                        powerDiff += effect.power.calculate(game, null, null);
                        toughnessDiff += effect.toughness.calculate(game, null, null);

                    }
                }
                else {
                    powerDiff = cardPower.getValue() - cardPower.getModifiedBaseValue();
                    toughnessDiff = cardToughness.getValue() - cardToughness.getModifiedBaseValue();

                    if(powerDiff == 0) {
                        powerDiff = targetMap.get(target).lastPowerDiff;
                    } else {
                        targetMap.get(target).lastPowerDiff = powerDiff;
                    }
                    if(toughnessDiff == 0) {
                        toughnessDiff = targetMap.get(target).lastToughnessDiff;
                    } else {
                        targetMap.get(target).lastToughnessDiff = toughnessDiff;
                    }
                }

                card.addInfo("boostPTPerpetualChange", "<font color=#9A4FFE>{this}" +
                        " perpetually gets " + plusMinusNF.format(powerDiff) + "/" + plusMinusNF.format(toughnessDiff)
                        +".</font>", game);
            }
        }
        return affectedTargets > 0;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return getTargetPointer().describeTargets(mode.getTargets(), "it") +
                (getTargetPointer().isPlural(mode.getTargets()) ? " each get " : " gets ") +
                CardUtil.getBoostText(power, toughness, duration);
    }

    @Override
    public boolean affectsCard(Card card, Game game) {
        for(UUID target : targetMap.keySet()) {
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
        for(UUID target : targetMap.keySet()) {
            Card gameCard = game.getCard(card.getId());
            if(gameCard != null && gameCard.getId().equals(target)) {
                if(power != null) {
                    gameCard.getPower().increaseBoostedValue(-1 * power.calculate(game, null, this));
                }
                if(toughness != null) {
                    gameCard.getToughness().increaseBoostedValue(-1 * toughness.calculate(game, null, this));
                }
            }
        }
        targetMap.keySet().removeIf(target -> card.getId().equals(target));
        if(targetMap.keySet().isEmpty()) {
            this.discard();
        }
    }

    @Override
    public void addTarget(Card card, Game game) {
        UUID cardId = card.getId();

        targetMap.put(cardId, new targetData());
        MageObjectReference cardReference = new MageObjectReference(card, game);
        this.affectedObjectList.add(cardReference);

        targetMap.get(cardId).wasPermanentAtInit = card.isPermanent(game) && game.getPermanent(cardId) != null;

    }

    public boolean affectsPower(Game game) {
        if(power == null)
            return false;
        return power.calculate(game, null, this) != 0;
    }

    public boolean affectsToughness(Game game) {
        if(toughness == null)
            return false;
        return toughness.calculate(game, null, this) != 0;
    }
}
