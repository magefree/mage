package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.permanent.token.AngelVigilanceToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author weirddan455
 */
public final class ValkyrieHarbinger extends CardImpl {

    public ValkyrieHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of each end step, if you gained 4 or more life this turn, create a 4/4 white Angel creature token with flying and vigilance.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD,
                new CreateTokenEffect(new AngelVigilanceToken()),
                TargetController.ANY,
                new YouGainedLifeCondition(ComparisonType.MORE_THAN, 3),
                false
        ), new PlayerGainedLifeWatcher());
    }

    private ValkyrieHarbinger(final ValkyrieHarbinger card) {
        super(card);
    }

    @Override
    public ValkyrieHarbinger copy() {
        return new ValkyrieHarbinger(this);
    }
}
