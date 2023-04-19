package mage.cards.s;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import java.util.UUID;
import mage.abilities.mana.AnyColorLandsProduceManaAbility;
import mage.constants.TargetController;

/**
 * @author anonymous
 */
public final class StarCompass extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledLandPermanent("basic land");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }
    
    public StarCompass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Star Compass enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // {T}: Add one mana of any color that a basic land you control could produce.
        this.addAbility(new AnyColorLandsProduceManaAbility(TargetController.YOU, true, filter));
    }

    private StarCompass(final StarCompass card) {
        super(card);
    }

    @Override
    public StarCompass copy() {
        return new StarCompass(this);
    }
}