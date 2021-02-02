    
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RemoveFromCombatTargetEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author BetaSteward
 */
public final class HollowhengeSpirit extends CardImpl {

    public HollowhengeSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        // When Hollowhenge Spirit enters the battlefield, remove target attacking or blocking creature from combat.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RemoveFromCombatTargetEffect());
        Target target = new TargetAttackingOrBlockingCreature();
        ability.addTarget(target);
        this.addAbility(ability);

    }

    private HollowhengeSpirit(final HollowhengeSpirit card) {
        super(card);
    }

    @Override
    public HollowhengeSpirit copy() {
        return new HollowhengeSpirit(this);
    }
}
