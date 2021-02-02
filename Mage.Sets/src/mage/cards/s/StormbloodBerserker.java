
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author nantuko
 */
public final class StormbloodBerserker extends CardImpl {

    public StormbloodBerserker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.BERSERKER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new BloodthirstAbility(2));
        // Menace (This creature can't be blocked except by two or more creatures.)
        this.addAbility(new MenaceAbility());
    }

    private StormbloodBerserker(final StormbloodBerserker card) {
        super(card);
    }

    @Override
    public StormbloodBerserker copy() {
        return new StormbloodBerserker(this);
    }
}
