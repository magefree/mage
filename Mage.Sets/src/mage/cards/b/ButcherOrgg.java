
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ControllerDivideCombatDamageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author L_J
 */
public final class ButcherOrgg extends CardImpl {

    public ButcherOrgg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}{R}");
        this.subtype.add(SubType.ORGG);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // You may assign Butcher Orgg's combat damage divided as you choose among defending player and/or any number of creatures they control.
        this.addAbility(ControllerDivideCombatDamageAbility.getInstance());
    }

    private ButcherOrgg(final ButcherOrgg card) {
        super(card);
    }

    @Override
    public ButcherOrgg copy() {
        return new ButcherOrgg(this);
    }
}
