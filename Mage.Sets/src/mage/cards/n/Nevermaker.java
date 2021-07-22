
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Nevermaker extends CardImpl {

    public Nevermaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Nevermaker leaves the battlefield, put target nonland permanent on top of its owner's library.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new PutOnLibraryTargetEffect(true), false);
        Target target = new TargetNonlandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        // Evoke {3}{U}
        this.addAbility(new EvokeAbility("{3}{U}"));
    }

    private Nevermaker(final Nevermaker card) {
        super(card);
    }

    @Override
    public Nevermaker copy() {
        return new Nevermaker(this);
    }
}
