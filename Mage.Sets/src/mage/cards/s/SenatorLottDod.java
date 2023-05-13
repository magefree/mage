package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author Styxo
 */
public final class SenatorLottDod extends CardImpl {

    public SenatorLottDod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.NEIMOIDIAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Spells your opponents cast that target you cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SenatorLottDodSpellsTargetingYouCostModificationEffect()));

        // Spell your opponents cast that target a creature you control cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SenatorLottDodSpellsTargetingCreatureCostModificationEffect()));
    }

    private SenatorLottDod(final SenatorLottDod card) {
        super(card);
    }

    @Override
    public SenatorLottDod copy() {
        return new SenatorLottDod(this);
    }
}

class SenatorLottDodSpellsTargetingCreatureCostModificationEffect extends CostModificationEffectImpl {

    public SenatorLottDodSpellsTargetingCreatureCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Spell your opponents cast that target a creature you control cost {1} more to cast";
    }

    protected SenatorLottDodSpellsTargetingCreatureCostModificationEffect(SenatorLottDodSpellsTargetingCreatureCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        if (!game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
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

            // can target without cost increase
            if (allTargets.stream().anyMatch(target -> !isTargetCompatible(target, source, game))) {
                return false;
            }
            ;
        }

        return allTargets.stream().anyMatch(target -> isTargetCompatible(target, source, game));
    }

    private boolean isTargetCompatible(UUID target, Ability source, Game game) {
        // target a creature you control
        Permanent targetPermanent = game.getPermanent(target);
        if (targetPermanent != null && targetPermanent.isCreature(game) && targetPermanent.isControlledBy(source.getControllerId())) {
            return true;
        }

        return false;
    }

    @Override
    public SenatorLottDodSpellsTargetingCreatureCostModificationEffect copy() {
        return new SenatorLottDodSpellsTargetingCreatureCostModificationEffect(this);
    }
}

class SenatorLottDodSpellsTargetingYouCostModificationEffect extends CostModificationEffectImpl {

    public SenatorLottDodSpellsTargetingYouCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = "Spells your opponents cast that target you cost {2} more to cast";
    }

    protected SenatorLottDodSpellsTargetingYouCostModificationEffect(SenatorLottDodSpellsTargetingYouCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        if (!game.getOpponents(source.getControllerId()).contains(abilityToModify.getControllerId())) {
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

            // can target without cost increase
            if (allTargets.stream().anyMatch(target -> !isTargetCompatible(target, source, game))) {
                return false;
            }
            ;
        }

        return allTargets.stream().anyMatch(target -> isTargetCompatible(target, source, game));
    }

    private boolean isTargetCompatible(UUID target, Ability source, Game game) {
        // target you
        Player targetPlayer = game.getPlayer(target);
        if (targetPlayer != null && targetPlayer.getId().equals(source.getControllerId())) {
            return true;
        }

        return false;
    }

    @Override
    public SenatorLottDodSpellsTargetingYouCostModificationEffect copy() {
        return new SenatorLottDodSpellsTargetingYouCostModificationEffect(this);
    }
}
