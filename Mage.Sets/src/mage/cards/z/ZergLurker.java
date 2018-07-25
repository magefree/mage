package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class ZergLurker extends CardImpl {

    public ZergLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.ZERG);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Zerg Lurker doesn't untap during your untap step.
        // At the beginning of your upkeep, if Zerg Lurker is tapped, target opponent loses 2 life.
    }

    public ZergLurker(final ZergLurker card) {
        super(card);
    }

    @Override
    public ZergLurker copy() {
        return new ZergLurker(this);
    }
}
