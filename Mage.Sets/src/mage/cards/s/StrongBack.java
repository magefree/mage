package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttachedToAttachedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class StrongBack extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Aura and Equipment attached to it");

    static {
        filter.add(Predicates.or(
            SubType.AURA.getPredicate(),
            SubType.EQUIPMENT.getPredicate()
        ));
        filter.add(AttachedToAttachedPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);

    public StrongBack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Equip abilities you activate that target enchanted creature cost {3} less to activate.
        this.addAbility(new SimpleStaticAbility(new StrongBackEquipEffect()));

        // Aura spells you cast that target enchanted creature cost {3} less to cast.
        this.addAbility(new SimpleStaticAbility(new StrongBackAuraSpellEffect()));

        // Enchanted creature gets +2/+2 for each Aura and Equipment attached to it.
        this.addAbility(new SimpleStaticAbility(new BoostEnchantedEffect(xValue, xValue)));
    }

    private StrongBack(final StrongBack card) {
        super(card);
    }

    @Override
    public StrongBack copy() {
        return new StrongBack(this);
    }
}

class StrongBackEquipEffect extends CostModificationEffectImpl {

    StrongBackEquipEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "equip abilities you activate that target enchanted creature cost {3} less to activate";
    }

    private StrongBackEquipEffect(final StrongBackEquipEffect effect) {
        super(effect);
    }

    @Override
    public StrongBackEquipEffect copy() {
        return new StrongBackEquipEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof EquipAbility)
                || !source.isControlledBy(abilityToModify.getControllerId())) {
            return false;
        }
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        UUID enchantedId = enchantment.getAttachedTo();
        if (game.getStack().getStackObject(abilityToModify.getId()) != null) {
            return CardUtil.getAllSelectedTargets(abilityToModify, game).contains(enchantedId);
        } else {
            return CardUtil.getAllPossibleTargets(abilityToModify, game).contains(enchantedId);
        }
    }
}

class StrongBackAuraSpellEffect extends CostModificationEffectImpl {

    StrongBackAuraSpellEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "Aura spells you cast that target enchanted creature cost {3} less to cast";
    }

    private StrongBackAuraSpellEffect(final StrongBackAuraSpellEffect effect) {
        super(effect);
    }

    @Override
    public StrongBackAuraSpellEffect copy() {
        return new StrongBackAuraSpellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 3);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)
                || !source.isControlledBy(abilityToModify.getControllerId())) {
            return false;
        }
        Card card = game.getCard(abilityToModify.getSourceId());
        if (card == null || !card.hasSubtype(SubType.AURA, game)) {
            return false;
        }
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        UUID enchantedId = enchantment.getAttachedTo();
        if (game.getStack().getStackObject(abilityToModify.getId()) != null) {
            return CardUtil.getAllSelectedTargets(abilityToModify, game).contains(enchantedId);
        } else {
            return CardUtil.getAllPossibleTargets(abilityToModify, game).contains(enchantedId);
        }
    }
}
