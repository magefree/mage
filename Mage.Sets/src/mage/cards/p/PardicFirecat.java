
package mage.cards.p;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class PardicFirecat extends CardImpl {

    public PardicFirecat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());
        // If Pardic Firecat is in a graveyard, effects from spells named Flame Burst count it as a card named Flame Burst.
        this.addAbility(mage.cards.f.FlameBurst.getCountAsAbility());
    }

    private PardicFirecat(final PardicFirecat card) {
        super(card);
    }

    @Override
    public PardicFirecat copy() {
        return new PardicFirecat(this);
    }
}