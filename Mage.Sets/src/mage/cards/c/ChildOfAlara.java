
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class ChildOfAlara extends CardImpl {

    public ChildOfAlara(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}{U}{B}{R}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // When Child of Alara dies, destroy all nonland permanents. They can't be regenerated.
        this.addAbility(new DiesSourceTriggeredAbility(new DestroyAllEffect(new FilterNonlandPermanent("nonland permanents"), true)));
        
    }

    private ChildOfAlara(final ChildOfAlara card) {
        super(card);
    }

    @Override
    public ChildOfAlara copy() {
        return new ChildOfAlara(this);
    }
}
