package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.Targets;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.*;

public class StrongBack extends CardImpl {

    private static final FilterCard filter = new FilterCard("Aura spells");

    static {
        filter.add(SubType.AURA.getPredicate());
    }

    public StrongBack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Equip abilities you activate that target enchanted creature cost {3} less to activate.
        this.addAbility(new SimpleStaticAbility(new StrongBackEquipCostEffect()));

        // Aura spells you cast that target enchanted creature cost {3} less to cast.
        this.addAbility(new SimpleStaticAbility(new StrongBackAuraCostEffect()));

        // Enchanted creature gets +2/+2 for each Aura and Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(StrongBackBoostValue.instance, StrongBackBoostValue.instance)));
    }

    public StrongBack(StrongBack card) {
        super(card);
    }

    @Override
    public StrongBack copy() {
        return new StrongBack(this);
    }

    private boolean shouldApplyCostReduction(Permanent sourcePermanent, Ability abilityToModify, Game game) {
        UUID attachedId = sourcePermanent.getAttachedTo();
        if (game.inCheckPlayableState()) {
            return !abilityToModify.getTargets().isEmpty() &&
                    abilityToModify.getTargets().get(0).canTarget(attachedId, abilityToModify, game);
        } else {
            Permanent attached = game.getPermanent(attachedId);
            return attached != null && isAttachedTargeted(abilityToModify.getTargets(), attachedId);
        }
    }

    private boolean isAttachedTargeted(Targets targets, UUID attachedId) {
        return targets
                .stream()
                .map(Target::getTargets)
                .flatMap(Collection::stream)
                .anyMatch(attachedId::equals);
    }

    class StrongBackEquipCostEffect extends CostModificationEffectImpl {

        StrongBackEquipCostEffect() {
            super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
            staticText = "equip abilities you activate that target enchanted creature cost {3} less to activate";
        }

        private StrongBackEquipCostEffect(final StrongBackEquipCostEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source, Ability abilityToModify) {
            CardUtil.reduceCost(abilityToModify, 3);
            return true;
        }

        @Override
        public boolean applies(Ability abilityToModify, Ability source, Game game) {
            Card card = game.getCard(abilityToModify.getSourceId());
            if (card != null
                    && abilityToModify instanceof EquipAbility
                    && abilityToModify.isControlledBy(source.getControllerId())) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    return shouldApplyCostReduction(sourcePermanent, abilityToModify, game);
                }
            }

            return false;
        }

        @Override
        public StrongBackEquipCostEffect copy() {
            return new StrongBackEquipCostEffect(this);
        }
    }

    class StrongBackAuraCostEffect extends CostModificationEffectImpl {

        StrongBackAuraCostEffect() {
            super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
            staticText = "aura spells you cast that target enchanted creature cost {3} less to cast";
        }

        private StrongBackAuraCostEffect(final StrongBackAuraCostEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source, Ability abilityToModify) {
            CardUtil.reduceCost(abilityToModify, 3);
            return true;
        }

        @Override
        public boolean applies(Ability abilityToModify, Ability source, Game game) {
            Card card = game.getCard(abilityToModify.getSourceId());
            if (card != null
                    && filter.match(card, game)
                    && abilityToModify.isControlledBy(source.getControllerId())) {
                Permanent sourcePermanent = game.getPermanent(source.getSourceId());
                if (sourcePermanent != null) {
                    return shouldApplyCostReduction(sourcePermanent, abilityToModify, game);
                }
            }

            return false;
        }

        @Override
        public StrongBackAuraCostEffect copy() {
            return new StrongBackAuraCostEffect(this);
        }
    }

    enum StrongBackBoostValue implements DynamicValue {
        instance;

        @Override
        public int calculate(Game game, Ability sourceAbility, Effect effect) {
            return Optional.ofNullable(sourceAbility.getSourcePermanentOrLKI(game))
                    .map(sourcePermanent -> game.getPermanent(sourcePermanent.getAttachedTo()))
                    .map(permanent -> permanent.getAttachments().stream()
                            .map(game::getPermanent)
                            .filter(Objects::nonNull)
                            .filter(attachment -> attachment.hasSubtype(SubType.AURA, game)
                                    || attachment.hasSubtype(SubType.EQUIPMENT, game))
                            .mapToInt(attachment -> 1)
                            .sum() * 2)
                    .orElse(0);
        }

        @Override
        public DynamicValue copy() {
            return this;
        }

        @Override
        public String getMessage() {
            return "Aura and Equipment attached to it";
        }
    }
}
