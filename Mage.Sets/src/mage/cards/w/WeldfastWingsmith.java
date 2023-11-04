
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author fireshoes
 */
public final class WeldfastWingsmith extends CardImpl {

    public WeldfastWingsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever an artifact enters the battlefield under your control, Weldfast Wingsmith gains flying until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), StaticFilters.FILTER_PERMANENT_ARTIFACT));
    }

    private WeldfastWingsmith(final WeldfastWingsmith card) {
        super(card);
    }

    @Override
    public WeldfastWingsmith copy() {
        return new WeldfastWingsmith(this);
    }
}
