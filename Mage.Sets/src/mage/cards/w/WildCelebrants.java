
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetArtifactPermanent;

/**
 *
 * @author LevelX2
 */
public final class WildCelebrants extends CardImpl {

    public WildCelebrants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.SATYR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // When Wild Celebrants enters the battlefield, you may destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), true);
        Target target = new TargetArtifactPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private WildCelebrants(final WildCelebrants card) {
        super(card);
    }

    @Override
    public WildCelebrants copy() {
        return new WildCelebrants(this);
    }
}
