

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class NulltreadGargantuan extends CardImpl {

    public NulltreadGargantuan (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{U}");
        this.subtype.add(SubType.BEAST);


        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // When Nulltread Gargantuan enters the battlefield, put a creature you control on top of its owner's library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true), false);
        Target target = new TargetControlledCreaturePermanent();
        target.setNotTarget(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public NulltreadGargantuan (final NulltreadGargantuan card) {
        super(card);
    }

    @Override
    public NulltreadGargantuan copy() {
        return new NulltreadGargantuan(this);
    }

}
