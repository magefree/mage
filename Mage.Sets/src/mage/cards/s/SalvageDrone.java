
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.IngestAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class SalvageDrone extends CardImpl {

    public SalvageDrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Ingest (Whenever this creature deals combat damage to a player, that player exiles the top card of their library.)
        this.addAbility(new IngestAbility());
        // When Salvage Drone dies, you may draw a card. If you do, discard a card.
        this.addAbility(new DiesSourceTriggeredAbility(new DrawDiscardControllerEffect(1, 1, true), false));

    }

    private SalvageDrone(final SalvageDrone card) {
        super(card);
    }

    @Override
    public SalvageDrone copy() {
        return new SalvageDrone(this);
    }
}
