
package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.DevourAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CalderaHellion extends CardImpl {

    public CalderaHellion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HELLION);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Devour 1 (As this enters the battlefield, you may sacrifice any number of creatures. This creature enters the battlefield with twice that many +1/+1 counters on it.)
        this.addAbility(new DevourAbility(1));

        // When Caldera Hellion enters the battlefield, it deals 3 damage to each creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(3, "it", new FilterCreaturePermanent())));
    }

    private CalderaHellion(final CalderaHellion card) {
        super(card);
    }

    @Override
    public CalderaHellion copy() {
        return new CalderaHellion(this);
    }
}
