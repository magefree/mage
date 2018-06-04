

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class GreenhiltTrainee extends CardImpl {

    public GreenhiltTrainee (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostTargetEffect(4, 4, Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new GreenhiltTraineeCost());
        this.addAbility(ability);
    }

    public GreenhiltTrainee (final GreenhiltTrainee card) {
        super(card);
    }

    @Override
    public GreenhiltTrainee copy() {
        return new GreenhiltTrainee(this);
    }
}

class GreenhiltTraineeCost extends CostImpl {

    public GreenhiltTraineeCost() {
        this.text = "Activate this ability only if Greenhilt Trainee's power is 4 or greater";
    }

    public GreenhiltTraineeCost(final GreenhiltTraineeCost cost) {
        super(cost);
    }

    @Override
    public GreenhiltTraineeCost copy() {
        return new GreenhiltTraineeCost(this);
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        Permanent permanent = game.getPermanent(sourceId);
        if (permanent != null) {
            if (permanent.getPower().getValue() >= 4) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
        this.paid = true;
        return paid;
    }
}
