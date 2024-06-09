package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GreenhiltTrainee extends CardImpl {

    public GreenhiltTrainee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new BoostTargetEffect(4, 4, Duration.EndOfTurn),
                new TapSourceCost(), GreenhiltTraineeCondition.instance
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private GreenhiltTrainee(final GreenhiltTrainee card) {
        super(card);
    }

    @Override
    public GreenhiltTrainee copy() {
        return new GreenhiltTrainee(this);
    }
}

enum GreenhiltTraineeCondition implements Condition {
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
