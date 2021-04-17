
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LevelX2
 */
public final class InvasiveSpecies extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another permanent you control");
    
    static {
        filter.add(AnotherPredicate.instance);
    }
    
    public InvasiveSpecies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.INSECT);


        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Invasive Species enters the battlefield, return another permanent you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(filter)));
        
    }

    private InvasiveSpecies(final InvasiveSpecies card) {
        super(card);
    }

    @Override
    public InvasiveSpecies copy() {
        return new InvasiveSpecies(this);
    }
}
