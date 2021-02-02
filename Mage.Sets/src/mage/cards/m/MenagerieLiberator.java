
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.MeleeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author emerald000
 */
public final class MenagerieLiberator extends CardImpl {

    public MenagerieLiberator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Melee
        this.addAbility(new MeleeAbility());
    }

    private MenagerieLiberator(final MenagerieLiberator card) {
        super(card);
    }

    @Override
    public MenagerieLiberator copy() {
        return new MenagerieLiberator(this);
    }
}
