package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class CrosstownCourier extends CardImpl {

    public CrosstownCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.VEDALKEN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Crosstown Courier deals combat damage to a player, that player puts that many cards from the top of their library into their graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MillCardsTargetEffect(SavedDamageValue.MANY), false, true));
    }

    private CrosstownCourier(final CrosstownCourier card) {
        super(card);
    }

    @Override
    public CrosstownCourier copy() {
        return new CrosstownCourier(this);
    }

}
