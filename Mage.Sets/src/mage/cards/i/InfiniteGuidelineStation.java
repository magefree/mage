package mage.cards.i;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.StationAbility;
import mage.abilities.keyword.StationLevelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InfiniteGuidelineStation extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("multicolored permanent you control");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter);
    private static final Hint hint = new ValueHint("Multicolored permanents you control", xValue);

    public InfiniteGuidelineStation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPACECRAFT);

        // When Infinite Guideline Station enters, create a tapped 2/2 colorless Robot artifact creature token for each multicolored permanent you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new RobotToken(), xValue, true, false)
        ).addHint(hint));

        // Station
        this.addAbility(new StationAbility());

        // STATION 12+
        // Flying
        // Whenever Infinite Guideline Station attacks, draw a card for each multicolored permanent you control.
        // 7/15
        this.addAbility(new StationLevelAbility(12)
                .withLevelAbility(FlyingAbility.getInstance())
                .withLevelAbility(new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(xValue)))
                .withPT(7, 15));
    }

    private InfiniteGuidelineStation(final InfiniteGuidelineStation card) {
        super(card);
    }

    @Override
    public InfiniteGuidelineStation copy() {
        return new InfiniteGuidelineStation(this);
    }
}
