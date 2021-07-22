package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
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

import java.util.UUID;

/**
 * @author North
 */
public final class BloodshotTrainee extends CardImpl {

    public BloodshotTrainee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(4),
                new TapSourceCost(), BloodshotTraineeCondition.instance
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private BloodshotTrainee(final BloodshotTrainee card) {
        super(card);
    }

    @Override
    public BloodshotTrainee copy() {
        return new BloodshotTrainee(this);
    }
}

enum BloodshotTraineeCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.getPower().getValue() >= 4;
    }

    @Override
    public String toString() {
        return "{this}'s power is 4 or greater";
    }
}
