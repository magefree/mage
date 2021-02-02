

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;


/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class BloodbraidElf extends CardImpl {

    public BloodbraidElf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}{G}");


        this.subtype.add(SubType.ELF, SubType.BERSERKER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new CascadeAbility());
    }

    private BloodbraidElf(final BloodbraidElf card) {
        super(card);
    }

    @Override
    public BloodbraidElf copy() {
        return new BloodbraidElf(this);
    }

}
