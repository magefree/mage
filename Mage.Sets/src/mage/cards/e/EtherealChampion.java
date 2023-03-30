
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Quercitron
 */
public final class EtherealChampion extends CardImpl {

    public EtherealChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}{W}{W}");
        this.subtype.add(SubType.AVATAR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Pay 1 life: Prevent the next 1 damage that would be dealt to Ethereal Champion this turn.
        Effect effect = new PreventDamageToSourceEffect(Duration.EndOfTurn, 1);
        effect.setText("Prevent the next 1 damage that would be dealt to {this} this turn");
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new PayLifeCost(1)));
    }

    private EtherealChampion(final EtherealChampion card) {
        super(card);
    }

    @Override
    public EtherealChampion copy() {
        return new EtherealChampion(this);
    }
}
