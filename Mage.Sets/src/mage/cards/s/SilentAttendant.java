

package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author Backfir3
 */
public final class SilentAttendant extends CardImpl {

    public SilentAttendant (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
    this.subtype.add(SubType.HUMAN);
    this.subtype.add(SubType.CLERIC);

    this.power = new MageInt(0);
    this.toughness = new MageInt(2);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new TapSourceCost()));
    }

    private SilentAttendant(final SilentAttendant card) {
        super(card);
    }

    @Override
    public SilentAttendant copy() {
        return new SilentAttendant(this);
    }
}