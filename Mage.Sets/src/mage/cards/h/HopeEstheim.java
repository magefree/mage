package mage.cards.h;

import mage.MageInt;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HopeEstheim extends CardImpl {

    public HopeEstheim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, each opponent mills X cards, where X is the amount of life you gained this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new MillCardsEachPlayerEffect(
                ControllerGainedLifeCount.instance, TargetController.OPPONENT
        )), new PlayerGainedLifeWatcher());
    }

    private HopeEstheim(final HopeEstheim card) {
        super(card);
    }

    @Override
    public HopeEstheim copy() {
        return new HopeEstheim(this);
    }
}
