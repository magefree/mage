
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class RiftwingCloudskate extends CardImpl {

    public RiftwingCloudskate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}{U}");
        this.subtype.add(SubType.ILLUSION);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Riftwing Cloudskate enters the battlefield, return target permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Suspend 3-{1}{U}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl<>("{1}{U}"), this));
    }

    private RiftwingCloudskate(final RiftwingCloudskate card) {
        super(card);
    }

    @Override
    public RiftwingCloudskate copy() {
        return new RiftwingCloudskate(this);
    }
}
