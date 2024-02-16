package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class AmbuscadeShaman extends CardImpl {

    public AmbuscadeShaman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Ambuscade Shaman or another creature enters the battlefield under your control, that creature gets +2/+2 until end of turn.
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn)
                        .setText("that creature gets +2/+2 until end of turn"),
                StaticFilters.FILTER_PERMANENT_CREATURE, false,
                SetTargetPointer.PERMANENT, true
        ));

        // Dash {3}{B} <i>(You may cast this spell for its dash cost. If you do, it gains haste, and it's returned from the battlefield to its owner's hand at the beginning of the next end step.)</i>);
        this.addAbility(new DashAbility("{3}{B}"));
    }

    private AmbuscadeShaman(final AmbuscadeShaman card) {
        super(card);
    }

    @Override
    public AmbuscadeShaman copy() {
        return new AmbuscadeShaman(this);
    }
}
