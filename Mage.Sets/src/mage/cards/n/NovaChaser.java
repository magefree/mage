
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChampionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class NovaChaser extends CardImpl {

    public NovaChaser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Champion an Elemental
        this.addAbility(new ChampionAbility(this, SubType.ELEMENTAL));
    }

    private NovaChaser(final NovaChaser card) {
        super(card);
    }

    @Override
    public NovaChaser copy() {
        return new NovaChaser(this);
    }
}
