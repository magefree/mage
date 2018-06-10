
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.EchoAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

/**
 *
 * @author Plopman
 */
public final class RavenFamiliar extends CardImpl {

    public RavenFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Echo {2}{U}
        this.addAbility(new EchoAbility("{2}{U}"));
        // When Raven Familiar enters the battlefield, look at the top three cards of your library. Put one of them into your hand and the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new LookLibraryAndPickControllerEffect(new StaticValue(3), false, new StaticValue(1), StaticFilters.FILTER_CARD, Zone.LIBRARY, false, false),
                false));
    }

    public RavenFamiliar(final RavenFamiliar card) {
        super(card);
    }

    @Override
    public RavenFamiliar copy() {
        return new RavenFamiliar(this);
    }
}
