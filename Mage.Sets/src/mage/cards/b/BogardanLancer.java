
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.BloodthirstAbility;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class BogardanLancer extends CardImpl {

    public BogardanLancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN, SubType.KNIGHT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Bloodthirst 1
        this.addAbility(new BloodthirstAbility(1));
        // Flanking
        this.addAbility(new FlankingAbility());
    }

    private BogardanLancer(final BogardanLancer card) {
        super(card);
    }

    @Override
    public BogardanLancer copy() {
        return new BogardanLancer(this);
    }
}
