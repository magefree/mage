
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.UntapLandsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class PeregrineDrake extends CardImpl {

    public PeregrineDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.DRAKE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Peregrine Drake enters the battlefield, untap up to five lands.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new UntapLandsEffect(5)));
    }

    private PeregrineDrake(final PeregrineDrake card) {
        super(card);
    }

    @Override
    public PeregrineDrake copy() {
        return new PeregrineDrake(this);
    }
}
