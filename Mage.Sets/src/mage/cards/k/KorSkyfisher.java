

package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Loki
 */
public final class KorSkyfisher extends CardImpl {

    public KorSkyfisher (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        
        //Flying
        this.addAbility(FlyingAbility.getInstance());
        
        //When Kor Skyfisher enters the battlefield, return a permanent you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(new FilterControlledPermanent()), false));
    }

    private KorSkyfisher(final KorSkyfisher card) {
        super(card);
    }

    @Override
    public KorSkyfisher copy() {
        return new KorSkyfisher(this);
    }

}
