
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class LlanowarDead extends CardImpl {

    public LlanowarDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}{G}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.ELF);


        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(new BlackManaAbility());
    }

    private LlanowarDead(final LlanowarDead card) {
        super(card);
    }

    @Override
    public LlanowarDead copy() {
        return new LlanowarDead(this);
    }
}
