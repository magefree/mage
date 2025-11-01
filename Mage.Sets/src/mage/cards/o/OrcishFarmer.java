package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 * @author notgreat
 */
public final class OrcishFarmer extends CardImpl {

    public OrcishFarmer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");

        this.subtype.add(SubType.ORC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Target land becomes a Swamp until its controller's next untap step.
        Ability ability = new SimpleActivatedAbility(new OrcishFarmerEffect(), new TapSourceCost());
        ability.addTarget(new TargetLandPermanent());
        this.addAbility(ability);
    }

    private OrcishFarmer(final OrcishFarmer card) {
        super(card);
    }

    @Override
    public OrcishFarmer copy() {
        return new OrcishFarmer(this);
    }
}

class OrcishFarmerEffect extends BecomesBasicLandTargetEffect {

    OrcishFarmerEffect() {
        super(Duration.Custom, SubType.SWAMP);
        setText("Target land becomes a Swamp until its controller's next untap step");
    }

    protected OrcishFarmerEffect(final OrcishFarmerEffect effect) {
        super(effect);
    }

    public OrcishFarmerEffect copy() {
        return new OrcishFarmerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (p == null) {
            discard();
            return false;
        }
        if (getEffectStartingOnTurn() != game.getTurnNum()
                && game.isActivePlayer(p.getControllerId())
                && game.getStep().getType() == PhaseStep.UNTAP) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}
