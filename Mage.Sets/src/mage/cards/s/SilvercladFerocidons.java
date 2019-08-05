
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author L_J
 */
public final class SilvercladFerocidons extends CardImpl {

    public SilvercladFerocidons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(5);

        // <i>Enrage</i> &mdash; Whenever Silverclad Ferocidon is dealt damage, each opponent sacrifices a permanent.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new SacrificeOpponentsEffect(new FilterPermanent()), false, true));
    }

    public SilvercladFerocidons(final SilvercladFerocidons card) {
        super(card);
    }

    @Override
    public SilvercladFerocidons copy() {
        return new SilvercladFerocidons(this);
    }
}
