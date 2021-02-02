
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class WindriderPatrol extends CardImpl {

    public WindriderPatrol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Windrider Patrol deals combat damage to a player, scry 2.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new ScryEffect(2), false));
    }

    private WindriderPatrol(final WindriderPatrol card) {
        super(card);
    }

    @Override
    public WindriderPatrol copy() {
        return new WindriderPatrol(this);
    }
}
