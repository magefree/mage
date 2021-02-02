
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class WhiptailMoloch extends CardImpl {

    public WhiptailMoloch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // When Whiptail Moloch enters the battlefield, it deals 3 damage to target creature you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(3, "it"), false);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private WhiptailMoloch(final WhiptailMoloch card) {
        super(card);
    }

    @Override
    public WhiptailMoloch copy() {
        return new WhiptailMoloch(this);
    }
}
