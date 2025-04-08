package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlameholdGrappler extends CardImpl {

    public FlameholdGrappler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When this creature enters, copy the next spell you cast this turn when you cast it. You may choose new targets for the copy.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new CopyNextSpellDelayedTriggeredAbility(StaticFilters.FILTER_SPELL)
        )));
    }

    private FlameholdGrappler(final FlameholdGrappler card) {
        super(card);
    }

    @Override
    public FlameholdGrappler copy() {
        return new FlameholdGrappler(this);
    }
}
