
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.IngestAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author fireshoes
 */
public final class CullingDrone extends CardImpl {

    public CullingDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Ingest
        this.addAbility(new IngestAbility());
    }

    private CullingDrone(final CullingDrone card) {
        super(card);
    }

    @Override
    public CullingDrone copy() {
        return new CullingDrone(this);
    }
}
