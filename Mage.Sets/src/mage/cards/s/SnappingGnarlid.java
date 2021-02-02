
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author fireshoes
 */
public final class SnappingGnarlid extends CardImpl {

    public SnappingGnarlid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Landfall</i>- Whenever a land enters the battlefield under your control, Snapping Gnarlid gets +1/+1 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 1, Duration.EndOfTurn), false));
    }

    private SnappingGnarlid(final SnappingGnarlid card) {
        super(card);
    }

    @Override
    public SnappingGnarlid copy() {
        return new SnappingGnarlid(this);
    }
}
