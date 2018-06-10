
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class BloodshotTrainee extends CardImpl {

    public BloodshotTrainee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(4), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        ability.addCost(new BloodshotTraineeCost());
        this.addAbility(ability);
    }

    public BloodshotTrainee(final BloodshotTrainee card) {
        super(card);
    }

    @Override
    public BloodshotTrainee copy() {
        return new BloodshotTrainee(this);
    }
}

class BloodshotTraineeCost extends CostImpl {

    public BloodshotTraineeCost() {
        this.text = "Activate this ability only if Bloodshot Trainee's power is 4 or greater";
    }

    public BloodshotTraineeCost(final BloodshotTraineeCost cost) {
        super(cost);
    }

    @Override
    public BloodshotTraineeCost copy() {
        return new BloodshotTraineeCost(this);
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
