
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MeleeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class DeputizedProtester extends CardImpl {

    public DeputizedProtester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());
        // Melee
        this.addAbility(new MeleeAbility());
    }

    private DeputizedProtester(final DeputizedProtester card) {
        super(card);
    }

    @Override
    public DeputizedProtester copy() {
        return new DeputizedProtester(this);
    }
}
