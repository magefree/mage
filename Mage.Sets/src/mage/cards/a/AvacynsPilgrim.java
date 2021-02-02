

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class AvacynsPilgrim extends CardImpl {

    public AvacynsPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new WhiteManaAbility());
    }

    private AvacynsPilgrim(final AvacynsPilgrim card) {
        super(card);
    }

    @Override
    public AvacynsPilgrim copy() {
        return new AvacynsPilgrim(this);
    }

}
