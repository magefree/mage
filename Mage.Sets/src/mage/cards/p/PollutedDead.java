
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 * @author noxx
 */
public final class PollutedDead extends CardImpl {

    public PollutedDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Polluted Dead dies, destroy target land.
        Ability ability = new DiesSourceTriggeredAbility(new DestroyTargetEffect());
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private PollutedDead(final PollutedDead card) {
        super(card);
    }

    @Override
    public PollutedDead copy() {
        return new PollutedDead(this);
    }
}
