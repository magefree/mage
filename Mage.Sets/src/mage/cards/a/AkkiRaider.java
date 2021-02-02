
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author Loki
 */
public final class AkkiRaider extends CardImpl {

    public AkkiRaider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever a land is put into a graveyard from the battlefield, Akki Raider gets +1/+0 until end of turn.
        this.addAbility(new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                new BoostSourceEffect(1,0,Duration.EndOfTurn), new FilterLandPermanent(),
                "Whenever a land is put into a graveyard from the battlefield, ", false));
    }

    private AkkiRaider(final AkkiRaider card) {
        super(card);
    }

    @Override
    public AkkiRaider copy() {
        return new AkkiRaider(this);
    }

}
