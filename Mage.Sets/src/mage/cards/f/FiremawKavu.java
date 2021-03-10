
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.EchoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FiremawKavu extends CardImpl {

    public FiremawKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}");
        this.subtype.add(SubType.KAVU);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Echo {5}{R}
        this.addAbility(new EchoAbility("{5}{R}"));
        
        // When Firemaw Kavu enters the battlefield, it deals 2 damage to target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // When Firemaw Kavu leaves the battlefield, it deals 4 damage to target creature.
        ability = new LeavesBattlefieldTriggeredAbility(new DamageTargetEffect(4, "it"), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FiremawKavu(final FiremawKavu card) {
        super(card);
    }

    @Override
    public FiremawKavu copy() {
        return new FiremawKavu(this);
    }
}
