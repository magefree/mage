
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class BarbarianOutcast extends CardImpl {
    
    private static final FilterControlledLandPermanent filterControlledLand = new FilterControlledLandPermanent("a Swamp");

    static {
        filterControlledLand.add(SubType.SWAMP.getPredicate());
    }

    public BarbarianOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN, SubType.BARBARIAN, SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When you control no Swamps, sacrifice Barbarian Outcast.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.SWAMP, "no Swamps"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    private BarbarianOutcast(final BarbarianOutcast card) {
        super(card);
    }

    @Override
    public BarbarianOutcast copy() {
        return new BarbarianOutcast(this);
    }
}
