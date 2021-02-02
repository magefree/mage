
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author EvilGeek
 */
public final class UrborgElf extends CardImpl {

    public UrborgElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Add {G}, {U}, or {B}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
        this.addAbility(new BlackManaAbility());
    }

    private UrborgElf(final UrborgElf card) {
        super(card);
    }

    @Override
    public UrborgElf copy() {
        return new UrborgElf(this);
    }
}
