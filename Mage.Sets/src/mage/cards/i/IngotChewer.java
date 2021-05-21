
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.EvokeAbility;
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
public final class IngotChewer extends CardImpl {

    public IngotChewer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Ingot Chewer enters the battlefield, destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        Target target = new TargetArtifactPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
        // Evoke {R}
        this.addAbility(new EvokeAbility("{R}"));
    }

    private IngotChewer(final IngotChewer card) {
        super(card);
    }

    @Override
    public IngotChewer copy() {
        return new IngotChewer(this);
    }
}
