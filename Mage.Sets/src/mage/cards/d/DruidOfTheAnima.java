

package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DruidOfTheAnima extends CardImpl {

    public DruidOfTheAnima (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.addAbility(new RedManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private DruidOfTheAnima(final DruidOfTheAnima card) {
        super(card);
    }

    @Override
    public DruidOfTheAnima copy() {
        return new DruidOfTheAnima(this);
    }
}
