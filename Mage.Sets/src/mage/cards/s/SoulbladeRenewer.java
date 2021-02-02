
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PartnerWithAbility;
import mage.abilities.keyword.SupportAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class SoulbladeRenewer extends CardImpl {

    public SoulbladeRenewer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Soulblade Corrupter (When this creature enters the battlefield, target player may put Soulblade Corrupter into their hand from their library, then shuffle.)
        this.addAbility(new PartnerWithAbility("Soulblade Corrupter"));

        // When Soulblade Renewer enters the battlefield, support 2. (Put a +1/+1 counter on each of up to two target creatures.)
        this.addAbility(new SupportAbility(this, 2));
    }

    private SoulbladeRenewer(final SoulbladeRenewer card) {
        super(card);
    }

    @Override
    public SoulbladeRenewer copy() {
        return new SoulbladeRenewer(this);
    }
}
