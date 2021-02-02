
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddPoisonCounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class MarshViper extends CardImpl {

    public MarshViper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Whenever Marsh Viper deals damage to a player, that player gets two poison counters.
        Effect effect = new AddPoisonCounterTargetEffect(2);
        effect.setText("that player gets two poison counters");
        this.addAbility(new DealsDamageToAPlayerTriggeredAbility(effect, false, true));
    }

    private MarshViper(final MarshViper card) {
        super(card);
    }

    @Override
    public MarshViper copy() {
        return new MarshViper(this);
    }
}
