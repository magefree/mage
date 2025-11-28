package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.SacrificeCost;
import mage.abilities.costs.UseAttachedCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DamageWithPowerFromSourceToAnotherTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
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
public final class DireFlail extends TransformingDoubleFacedCard {

    public DireFlail(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{R}",
                "Dire Blunderbuss",
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "R");

        // Dire Flail
        // Equipped creature gets +2/+0.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 0)));

        // Equip {1}
        this.getLeftHalfCard().addAbility(new EquipAbility(1, false));

        // Craft with artifact {3}{R}{R}
        this.getLeftHalfCard().addAbility(new CraftAbility("{3}{R}{R}"));

        // Dire Blunderbuss

        // Equipped creature gets +3/+0 and has "Whenever this creature attacks, you may sacrifice an artifact other than Dire Blunderbuss. When you do, this creature deals damage equal to its power to target creature"
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(3, 0));
        ability.addEffect(new DireBlunderbussGainAbilityEffect());
        this.getRightHalfCard().addAbility(ability);

        // Equip {1}
        this.getRightHalfCard().addAbility(new EquipAbility(1, false));
    }

    private DireFlail(final DireFlail card) {
        super(card);
    }

    @Override
    public DireFlail copy() {
        return new DireFlail(this);
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
        if (getAffectedObjectsSet()) {
            Permanent equipment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null) {
                this.setTargetPointer(new FixedTarget(equipment.getAttachedTo(), game.getState().getZoneChangeCounter(equipment.getAttachedTo())));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = null;
        if (getAffectedObjectsSet()) {
            permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
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
        ReflexiveTriggeredAbility reflexive = new ReflexiveTriggeredAbility(
                new DamageWithPowerFromSourceToAnotherTargetEffect("this creature"), false
        );
        reflexive.addTarget(new TargetCreaturePermanent());
        Ability grant = new mage.abilities.common.AttacksTriggeredAbility(
                new DoWhenCostPaid(
                        reflexive, new DireBlunderbussSacrificeCost(source, game),
                        "Sacrifice an artifact other than the equipment?"
                ), false
        );
        permanent.addAbility(grant, source.getSourceId(), game);
        return true;
    }
}

class DireBlunderbussSacrificeCost extends UseAttachedCost implements SacrificeCost {

    private final mage.abilities.costs.common.SacrificeTargetCost sacrificeCost;
    private final mage.MageObjectReference mageObjectReference;

    DireBlunderbussSacrificeCost(Ability source, Game game) {
        super();
        this.mageObjectReference = new mage.MageObjectReference(source.getSourceObject(game), game);
        FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent();
        filter.add(Predicates.not(new MageObjectReferencePredicate(this.mageObjectReference)));
        this.sacrificeCost = new mage.abilities.costs.common.SacrificeTargetCost(filter);
    }

    private DireBlunderbussSacrificeCost(final DireBlunderbussSacrificeCost cost) {
        super(cost);
        this.sacrificeCost = cost.sacrificeCost.copy();
        this.mageObjectReference = cost.mageObjectReference;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return sacrificeCost.canPay(ability, source, controllerId, game);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, mage.abilities.costs.Cost costToPay) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null) {
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
