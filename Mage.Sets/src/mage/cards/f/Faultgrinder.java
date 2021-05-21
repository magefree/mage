
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Faultgrinder extends CardImpl {

    public Faultgrinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Faultgrinder enters the battlefield, destroy target land.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(),false);
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);

        // Evoke {4}{R}
        this.addAbility(new EvokeAbility("{4}{R}"));
    }

    private Faultgrinder(final Faultgrinder card) {
        super(card);
    }

    @Override
    public Faultgrinder copy() {
        return new Faultgrinder(this);
    }
}
