

package mage.cards.b;

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
 * @author LevelX2
 */
public final class Batterhorn extends CardImpl {

    public Batterhorn (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Batterhorn enters the battlefield, you may destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    private Batterhorn(final Batterhorn card) {
        super(card);
    }

    @Override
    public Batterhorn copy() {
        return new Batterhorn(this);
    }
}
