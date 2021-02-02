
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SupportAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class ExpeditionRaptor extends CardImpl {

    public ExpeditionRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Expedition Raptor enters the battlefield, support 2.
        this.addAbility(new SupportAbility(this, 2));
    }

    private ExpeditionRaptor(final ExpeditionRaptor card) {
        super(card);
    }

    @Override
    public ExpeditionRaptor copy() {
        return new ExpeditionRaptor(this);
    }
}
