
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class FyndhornElves extends CardImpl {

    public FyndhornElves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private FyndhornElves(final FyndhornElves card) {
        super(card);
    }

    @Override
    public FyndhornElves copy() {
        return new FyndhornElves(this);
    }
}
