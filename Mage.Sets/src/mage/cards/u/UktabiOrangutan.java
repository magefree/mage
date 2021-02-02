
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class UktabiOrangutan extends CardImpl {

    public UktabiOrangutan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.APE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Uktabi Orangutan enters the battlefield, destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private UktabiOrangutan(final UktabiOrangutan card) {
        super(card);
    }

    @Override
    public UktabiOrangutan copy() {
        return new UktabiOrangutan(this);
    }
}
