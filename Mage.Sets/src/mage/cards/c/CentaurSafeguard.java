
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class CentaurSafeguard extends CardImpl {

    public CentaurSafeguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G/W}");
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Centaur Safeguard dies, you may gain 3 life.
        this.addAbility(new DiesSourceTriggeredAbility(new GainLifeEffect(3), true));
    }

    private CentaurSafeguard(final CentaurSafeguard card) {
        super(card);
    }

    @Override
    public CentaurSafeguard copy() {
        return new CentaurSafeguard(this);
    }
}
