
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ZoneChangeAllTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author North
 */
public final class FangrenMarauder extends CardImpl {

    public FangrenMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever an artifact is put into a graveyard from the battlefield, you may gain 5 life.
        this.addAbility(new ZoneChangeAllTriggeredAbility(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.GRAVEYARD,
                new GainLifeEffect(5), new FilterArtifactPermanent(),
                "Whenever an artifact is put into a graveyard from the battlefield, ", true));
    }

    private FangrenMarauder(final FangrenMarauder card) {
        super(card);
    }

    @Override
    public FangrenMarauder copy() {
        return new FangrenMarauder(this);
    }
}
