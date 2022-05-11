package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth & L_J
 */
public final class KeldonBattlewagon extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped creature you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
    }

    public KeldonBattlewagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.subtype.add(SubType.JUGGERNAUT);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Keldon Battlewagon can't block.
        this.addAbility(new CantBlockAbility());

        // When Keldon Battlewagon attacks, sacrifice it at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(new AtTheEndOfCombatDelayedTriggeredAbility(new SacrificeSourceEffect())), false));

        // Tap an untapped creature you control: Keldon Battlewagon gets +X/+0 until end of turn, where X is the power of the creature tapped this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new KeldonBattlewagonBoostEffect(), new KeldonBattlewagonCost(new TargetControlledPermanent(filter))));

    }

    private KeldonBattlewagon(final KeldonBattlewagon card) {
        super(card);
    }

    @Override
    public KeldonBattlewagon copy() {
        return new KeldonBattlewagon(this);
    }
}

class KeldonBattlewagonCost extends CostImpl {

    TargetControlledPermanent target;

    public KeldonBattlewagonCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = "Tap an untapped creature you control";
    }

    public KeldonBattlewagonCost(final KeldonBattlewagonCost cost) {
        super(cost);
        this.target = cost.target.copy();
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (target.choose(Outcome.Tap, controllerId, source.getSourceId(), source, game)) {
            for (UUID targetId : target.getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    return false;
                }
                paid |= permanent.tap(source, game);
                for (Effect effect : ability.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                }
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return target.canChoose(controllerId, source, game);
    }

    @Override
    public KeldonBattlewagonCost copy() {
        return new KeldonBattlewagonCost(this);
    }
}

class KeldonBattlewagonBoostEffect extends OneShotEffect {

    public KeldonBattlewagonBoostEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} gets +X/+0 until end of turn, where X is the power of the creature tapped this way";
    }

    public KeldonBattlewagonBoostEffect(KeldonBattlewagonBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent KeldonBattlewagon = game.getPermanent(source.getSourceId());
        Permanent tappedCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (tappedCreature != null && KeldonBattlewagon != null) {
            int amount = tappedCreature.getPower().getValue();
            game.addEffect(new BoostSourceEffect(amount, 0, Duration.EndOfTurn), source);
        }
        return true;
    }

    @Override
    public KeldonBattlewagonBoostEffect copy() {
        return new KeldonBattlewagonBoostEffect(this);
    }
}
