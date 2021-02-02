
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class StranglerootGeist extends CardImpl {

    public StranglerootGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(HasteAbility.getInstance());
        // Undying
        this.addAbility(new UndyingAbility());
    }

    private StranglerootGeist(final StranglerootGeist card) {
        super(card);
    }

    @Override
    public StranglerootGeist copy() {
        return new StranglerootGeist(this);
    }
}
