
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author LevelX2
 */
public final class TunnelingGeopede extends CardImpl {

    public TunnelingGeopede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, Tunneling Geopede deals 1 damage to each opponent.
        this.addAbility(new LandfallAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), false));
    }
    
    private TunnelingGeopede(final TunnelingGeopede card) {
        super(card);
    }

    @Override
    public TunnelingGeopede copy() {
        return new TunnelingGeopede(this);
    }
}
