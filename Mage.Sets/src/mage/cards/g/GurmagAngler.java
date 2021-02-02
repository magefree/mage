
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DelveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class GurmagAngler extends CardImpl {

    public GurmagAngler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.FISH);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Delve
        this.addAbility(new DelveAbility());
    }

    private GurmagAngler(final GurmagAngler card) {
        super(card);
    }

    @Override
    public GurmagAngler copy() {
        return new GurmagAngler(this);
    }
}
