
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.PersistAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class SafeholdElite extends CardImpl {

    public SafeholdElite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Persist
        this.addAbility(new PersistAbility());
    }

    private SafeholdElite(final SafeholdElite card) {
        super(card);
    }

    @Override
    public SafeholdElite copy() {
        return new SafeholdElite(this);
    }
}
