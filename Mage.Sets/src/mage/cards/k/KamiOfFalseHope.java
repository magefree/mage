
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.PreventAllDamageByAllPermanentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Plopman
 */
public final class KamiOfFalseHope extends CardImpl {

    public KamiOfFalseHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Kami of False Hope: Prevent all combat damage that would be dealt this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageByAllPermanentsEffect(Duration.EndOfTurn, true), new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private KamiOfFalseHope(final KamiOfFalseHope card) {
        super(card);
    }

    @Override
    public KamiOfFalseHope copy() {
        return new KamiOfFalseHope(this);
    }
}
