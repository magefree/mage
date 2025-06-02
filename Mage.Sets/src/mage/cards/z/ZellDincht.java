package mage.cards.z;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.hint.common.LandsYouControlHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZellDincht extends CardImpl {

    public ZellDincht(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)
        ));

        // Zell Dincht gets +1/+0 for each land you control.
        this.addAbility(new SimpleStaticAbility(new BoostSourceEffect(
                LandsYouControlCount.instance, StaticValue.get(0), Duration.WhileOnBattlefield
        ).setText("{this} gets +1/+0 for each land you control")).addHint(LandsYouControlHint.instance));

        // At the beginning of your end step, return a land you control to its owner's hand.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ReturnToHandChosenControlledPermanentEffect(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND)
        ));
    }

    private ZellDincht(final ZellDincht card) {
        super(card);
    }

    @Override
    public ZellDincht copy() {
        return new ZellDincht(this);
    }
}
