
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class Clone extends CardImpl {

    public Clone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Clone enter the battlefield as a copy of any creature on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(), true));
    }

    private Clone(final Clone card) {
        super(card);
    }

    @Override
    public Clone copy() {
        return new Clone(this);
    }

}
