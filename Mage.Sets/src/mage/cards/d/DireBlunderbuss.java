package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.UseAttachedCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageWithPowerFromSourceToAnotherTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DireBlunderbuss extends CardImpl {

    public DireBlunderbuss(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");
        this.nightCard = true;

        this.subtype.add(SubType.EQUIPMENT);
        this.color.setRed(true);

        // Equipped creature gets +3/+0 and has "Whenever this creature attacks, you may sacrifice an artifact other than Dire Blunderbuss. When you do, this creature deals damage equal to its power to target creature"
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 0));
        ability.addEffect(new DireBlunderbussGainAbilityEffect());
        this.addAbility(ability);

        // Equip {1}
        this.addAbility(new EquipAbility(1, false));
    }

    private DireBlunderbuss(final DireBlunderbuss card) {
        super(card);
    }

    @Override
    public DireBlunderbuss copy() {
        return new DireBlunderbuss(this);
    }
}

class DireBlunderbussGainAbilityEffect extends ContinuousEffectImpl {

    DireBlunderbussGainAbilityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "and has \"Whenever this creature attacks, you may sacrifice an artifact other than {this}. "
                + "When you do, this creature deals damage equal to its power to target creature.\"";
    }

    protected DireBlunderbussGainAbilityEffect(final DireBlunderbussGainAbilityEffect effect) {
        super(effect);
    }

    @Override
    public DireBlunderbussGainAbilityEffect copy() {
        return new DireBlunderbussGainAbilityEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (affectedObjectsSet) {
            Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        if (affectedObjectsSet) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
            if (permanent == null) {
                discard();
                return true;
            }
        } else {
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                permanent = game.getPermanentOrLKIBattlefield(equipment.getAttachedTo());
            }
        }
        if (permanent == null) {
            return true;
        }
        Ability ability = makeAbility(game, source);
        ability.getEffects().setValue("attachedPermanent", game.getPermanent(source.getSourceId()));
        permanent.addAbility(ability, source.getSourceId(), game);
        return true;
    }

    protected Ability makeAbility(Game game, Ability source) {
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new DamageWithPowerFromSourceToAnotherTargetEffect("this creature"), false
        );
        reflexive.addTarget(new TargetCreaturePermanent());
        return new AttacksTriggeredAbility(
                new DoWhenCostPaid(
                        reflexive, new DireBlunderbussSacrificeCost(source, game),
                        "Sacrifice an artifact other than the equipment?"
                ), false
        );
    }
}

class DireBlunderbussSacrificeCost extends UseAttachedCost implements SacrificeCost {

    private final SacrificeTargetCost sacrificeCost;

    DireBlunderbussSacrificeCost(Ability source, Game game) {
        super();
        this.setMageObjectReference(source, game);
        FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent();
        filter.add(Predicates.not(new MageObjectReferencePredicate(this.mageObjectReference)));
        this.sacrificeCost = new SacrificeTargetCost(filter);
    }

    private DireBlunderbussSacrificeCost(final DireBlunderbussSacrificeCost cost) {
        super(cost);
        this.sacrificeCost = cost.sacrificeCost.copy();
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return sacrificeCost.canPay(ability, source, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (mageObjectReference == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return paid;
        }
        paid = sacrificeCost.pay(ability, game, source, controllerId, noMana, costToPay);
        return paid;
    }

    @Override
    public DireBlunderbussSacrificeCost copy() {
        return new DireBlunderbussSacrificeCost(this);
    }

    @Override
    public String getText() {
        return "sacrifice an artifact other than " + this.name;
    }
}
