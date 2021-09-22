package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author halljared
 */
public final class InfectiousCurse extends CardImpl {

    public InfectiousCurse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");
        this.subtype.add(SubType.AURA, SubType.CURSE);
        this.color.setBlack(true);

        this.nightCard = true;

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Spells you cast that target enchanted player cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new InfectiousCurseCostReductionEffect()));

        // At the beginning of enchanted player's upkeep, that player loses 1 life and you gain 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new LoseLifeTargetEffect(1).setText("that player loses 1 life"),
                TargetController.ENCHANTED, false
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private InfectiousCurse(final InfectiousCurse card) {
        super(card);
    }

    @Override
    public InfectiousCurse copy() {
        return new InfectiousCurse(this);
    }
}

class InfectiousCurseCostReductionEffect extends CostModificationEffectImpl {

    InfectiousCurseCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Spells you cast that target enchanted player cost {1} less to cast";
    }

    private InfectiousCurseCostReductionEffect(InfectiousCurseCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        if (!source.isControlledBy(abilityToModify.getControllerId())) {
            return false;
        }

        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }

        Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
        Set<UUID> allTargets;
        if (spell != null) {
            // real cast
            allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
        } else {
            // playable
            allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);
        }

        // try to reduce all the time (if it possible to target)
        return allTargets.stream().anyMatch(target -> Objects.equals(target, enchantment.getAttachedTo()));
    }

    @Override
    public InfectiousCurseCostReductionEffect copy() {
        return new InfectiousCurseCostReductionEffect(this);
    }
}
