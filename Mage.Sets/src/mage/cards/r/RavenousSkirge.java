
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Loki
 */
public final class RavenousSkirge extends CardImpl {

    public RavenousSkirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.IMP);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Ravenous Skirge attacks, it gets +2/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(2, 0, Duration.EndOfTurn), false));
    }

    private RavenousSkirge(final RavenousSkirge card) {
        super(card);
    }

    @Override
    public RavenousSkirge copy() {
        return new RavenousSkirge(this);
    }
}
