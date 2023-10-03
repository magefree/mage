

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class GuardiansOfAkrasa extends CardImpl {

    public GuardiansOfAkrasa (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(4);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(new ExaltedAbility());
    }

    private GuardiansOfAkrasa(final GuardiansOfAkrasa card) {
        super(card);
    }

    @Override
    public GuardiansOfAkrasa copy() {
        return new GuardiansOfAkrasa(this);
    }
}
