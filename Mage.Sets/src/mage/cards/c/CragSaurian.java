package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SourceDealsDamageToThisTriggeredAbility;
import mage.abilities.effects.common.TargetPlayerGainControlSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author wetterlicht & L_J
 */
public final class CragSaurian extends CardImpl {

    public CragSaurian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{R}{R}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a source deals damage to Crag Saurian, that source's controller gains control of Crag Saurian.
        this.addAbility(new SourceDealsDamageToThisTriggeredAbility(new TargetPlayerGainControlSourceEffect("that source's controller")));
    }

    private CragSaurian(final CragSaurian card) {
        super(card);
    }

    @Override
    public CragSaurian copy() {
        return new CragSaurian(this);
    }
}
