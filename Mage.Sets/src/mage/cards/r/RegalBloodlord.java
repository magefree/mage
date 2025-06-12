package mage.cards.r;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.permanent.token.BatToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RegalBloodlord extends CardImpl {

    private static final Condition condition = new YouGainedLifeCondition();

    public RegalBloodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of each end step, if you gained life this turn, create a 1/1 black Bat creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new BatToken()), false, condition
        ).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private RegalBloodlord(final RegalBloodlord card) {
        super(card);
    }

    @Override
    public RegalBloodlord copy() {
        return new RegalBloodlord(this);
    }
}
