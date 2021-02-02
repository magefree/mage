
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterNonlandPermanent;

/**
 *
 * @author emerald000
 */
public final class CleverImpersonator extends CardImpl {

    public CleverImpersonator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(new FilterNonlandPermanent()), true));
    }

    private CleverImpersonator(final CleverImpersonator card) {
        super(card);
    }

    @Override
    public CleverImpersonator copy() {
        return new CleverImpersonator(this);
    }
}
