
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.SpaceflightAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class MillenniumFalcon extends CardImpl {

    public MillenniumFalcon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.STARSHIP);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Spaceflight
        this.addAbility(SpaceflightAbility.getInstance());

        //  When Millennium Falcon enters the battlefield, creatures you control gain hexproof until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(HexproofAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES)));
    }

    private MillenniumFalcon(final MillenniumFalcon card) {
        super(card);
    }

    @Override
    public MillenniumFalcon copy() {
        return new MillenniumFalcon(this);
    }
}
