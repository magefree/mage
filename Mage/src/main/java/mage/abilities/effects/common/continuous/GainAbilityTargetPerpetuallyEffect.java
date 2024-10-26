package mage.abilities.effects.common.continuous;

import mage.MageObjectImpl;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.Mode;
import mage.abilities.common.LinkedEffectIdStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.PerpetuallyEffect;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.*;
import java.util.List;

/**
 * @author karapuzz14
 */
public class GainAbilityTargetPerpetuallyEffect extends ContinuousEffectImpl implements PerpetuallyEffect {

    protected final Ability ability;
    private HashMap<UUID, Boolean> morphedMap  = new HashMap<>();


    public GainAbilityTargetPerpetuallyEffect(Ability ability) {
        this(ability, null);
    }

    public GainAbilityTargetPerpetuallyEffect(Ability ability, String rule) {
        super(Duration.Perpetually, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, ability.getEffects().getOutcome(ability, Outcome.AddAbility));
        this.ability = copyAbility(ability); // See the method's comment, ability.copy() is not enough.
        this.staticText = rule;
        this.generateGainAbilityDependencies(ability, null);
    }

    protected GainAbilityTargetPerpetuallyEffect(final GainAbilityTargetPerpetuallyEffect effect) {
        super(effect);
        this.ability = copyAbility(effect.ability); // See the method's comment, ability.copy() is not enough.
        this.morphedMap = new HashMap<>(effect.morphedMap);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        List<UUID> targetList = getTargetPointer().getTargets(game, source);

        targetList.stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .forEach(card -> {
                    if (!card.getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
                        MageObjectReference cardReference = new MageObjectReference(card, game);
                        this.affectedObjectList.add(cardReference);

                        if(card.isPermanent(game) && game.getPermanent(card.getId()) != null) {
                            morphedMap.put(card.getId(), true);
                        }
                        else {
                            morphedMap.put(card.getId(), false);
                        }
                        addTarget(card, game);
                    }
                });

    }

    @Override
    public GainAbilityTargetPerpetuallyEffect copy() {
        return new GainAbilityTargetPerpetuallyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;

        for (UUID targetId : morphedMap.keySet()) {
            Card card = game.getCard(targetId);

            if (card != null) {
                if (card.getAbilities().contains(ability)) {
                    continue;
                }
                if(card.isPermanent(game)) {
                    Permanent permanent = game.getPermanent(targetId);
                    if(permanent != null) {
                        if(permanent.isFaceDown(game) && !morphedMap.get(targetId)) {
                            return affectedTargets > 0;
                        }
                        ability.setControllerId(permanent.getControllerId());
                        permanent.addAbility(ability, source.getId(), game);
                        affectedTargets++;
                        continue;
                    }
                }
                if(card instanceof SplitCard) {
                    SplitCardHalf left = ((SplitCard) card).getLeftHalfCard();
                    Ability leftAbility = copyAbility(ability);
                    leftAbility.setControllerId(source.getControllerId());
                    leftAbility.setSourceId(left.getId());
                    addTarget(left, game);
                    game.getState().addOtherAbility(left, leftAbility);

                    SplitCardHalf right = ((SplitCard) card).getRightHalfCard();
                    Ability rightAbility = copyAbility(ability);
                    rightAbility.setControllerId(source.getControllerId());
                    rightAbility.setSourceId(right.getId());
                    addTarget(right, game);
                    game.getState().addOtherAbility(right, rightAbility);
                }
                else {
                    game.getState().addOtherAbility(card, ability);
                }
                if(card instanceof AdventureCard) {
                    MageObjectImpl spellCardObj = (MageObjectImpl) ((AdventureCard) card).getSpellCard();
                    UUID adventureId = spellCardObj.getId();
                    if (adventureId != null) {
                        Card spellCard = (Card) spellCardObj;
                        Ability newAbility = copyAbility(ability);
                        newAbility.setSourceId(adventureId);
                        newAbility.setControllerId(source.getControllerId());

                        addTarget(spellCard, game);
                        game.getState().addOtherAbility(spellCard, newAbility);
                    }
                }
                affectedTargets++;

            }
        }

        return affectedTargets > 0;
    }
    @Override
    public boolean affectsCard(Card card, Game game) {
        for(UUID targetId : morphedMap.keySet()) {
            if(card.getId().equals(targetId)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeTarget(Card card, Game game) {
        UUID cardId = card.getId();
        morphedMap.remove(cardId);

        Map<MageObjectReference, Set<String>> cardRulesMap = game.getState().getContinuousEffects().getPerpetuallyAffectedObjectsRules();
        Set<MageObjectReference> cardRefs = cardRulesMap.keySet();
        cardRefs.removeIf(ref -> ref.refersTo(cardId, game));
        if(morphedMap.isEmpty()) {
            this.discard();
        }
    }

    @Override
    public void addTarget(Card card, Game game) {
        if (card.getSpellAbility().getSpellAbilityType().equals(SpellAbilityType.SPLIT_FUSED)) {
            return;
        }
        MageObjectReference cardReference = new MageObjectReference(card, game);
        if(!(card instanceof AdventureCardSpell || card instanceof SplitCardHalf)) {
            if (card.isPermanent(game) && game.getPermanent(card.getId()) != null) {
                morphedMap.put(card.getId(), true);
            } else {
                morphedMap.put(card.getId(), false);
            }
            this.affectedObjectList.add(cardReference);
        }
        if(!(ability instanceof MageSingleton && card.getAbilities().contains(ability))) {

            Map<MageObjectReference, Set<String>> cardRulesMap = game.getState().getContinuousEffects().getPerpetuallyAffectedObjectsRules();
            String rule = ability.getRule();
            String upperCaseRule = CardUtil.getTextWithFirstCharUpperCase(rule);

            if (cardRulesMap.containsKey(cardReference)) {
                Set<String> ruleSet = cardRulesMap.get(cardReference);
                ruleSet.add(upperCaseRule);
            } else {
                Set<String> set = new HashSet<>();
                set.add(upperCaseRule);
                cardRulesMap.put(cardReference, set);
            }
        }
    }

    /**
     * Copying the ability and providing ability is needed in a few situations,
     * The copy in order to have internal fields be proper to that ability in particular.
     * Id must be different for the copy, for a few things like the GainAbilityTargetEffect gained
     * by a clone, or in the case of an activated ability, called multiple times on the same target,
     * and thus the ability should be gained multiple times.
     */
    private Ability copyAbility(Ability toCopyAbility) {
        Ability abilityToCopy = toCopyAbility.copy();
        abilityToCopy.newId();
        if (abilityToCopy instanceof LinkedEffectIdStaticAbility) {
            ((LinkedEffectIdStaticAbility) abilityToCopy).setEffectIdManually();
        }
        return abilityToCopy;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        sb.append("perpetually");
        sb.append(getTargetPointer().isPlural(mode.getTargets()) ? " gain " : " gains ");
        if(!(ability instanceof MageSingleton)){
            sb.append("\"").append(CardUtil.getTextWithFirstCharUpperCase(ability.getRule())).append("\"");
        }
        else {
            sb.append(CardUtil.stripReminderText(ability.getRule()));
        }
        return sb.toString();
    }
}
