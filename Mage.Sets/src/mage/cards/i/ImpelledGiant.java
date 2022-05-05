package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
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
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class ImpelledGiant extends CardImpl {

    static final private FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("an untapped red creature you control other than Impelled Giant");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter.add(AnotherPredicate.instance);
    }

    public ImpelledGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Tap an untapped red creature you control other than Impelled Giant: Impelled Giant gets +X/+0 until end of turn, where X is the power of the creature tapped this way.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ImpelledGiantBoostEffect(), new ImpelledGiantCost(new TargetControlledPermanent(filter))));

    }

    private ImpelledGiant(final ImpelledGiant card) {
        super(card);
    }

    @Override
    public ImpelledGiant copy() {
        return new ImpelledGiant(this);
    }
}

class ImpelledGiantCost extends CostImpl {

    TargetControlledPermanent target;

    public ImpelledGiantCost(TargetControlledPermanent target) {
        this.target = target;
        this.text = "Tap an untapped red creature you control other than Impelled Giant";
    }

    public ImpelledGiantCost(final ImpelledGiantCost cost) {
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
    public ImpelledGiantCost copy() {
        return new ImpelledGiantCost(this);
    }

}

class ImpelledGiantBoostEffect extends OneShotEffect {

    public ImpelledGiantBoostEffect() {
        super(Outcome.BoostCreature);
        staticText = "{this} gets +X/+0 until end of turn, where X is the power of the creature tapped this way";
    }

    public ImpelledGiantBoostEffect(ImpelledGiantBoostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent impelledGiant = game.getPermanent(source.getSourceId());
        Permanent tappedCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (tappedCreature != null && impelledGiant != null) {
            int amount = tappedCreature.getPower().getValue();
            game.addEffect(new BoostSourceEffect(amount, 0, Duration.EndOfTurn), source);
        }
        return true;
    }

    @Override
    public ImpelledGiantBoostEffect copy() {
        return new ImpelledGiantBoostEffect(this);
    }
}
