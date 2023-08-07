
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SourceBecomesTargetTriggeredAbility;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class TetheredSkirge extends CardImpl {

    public TetheredSkirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.IMP);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Tethered Skirge becomes the target of a spell or ability, you lose 1 life.
        this.addAbility(new SourceBecomesTargetTriggeredAbility(new LoseLifeSourceControllerEffect(1)));
    }

    private TetheredSkirge(final TetheredSkirge card) {
        super(card);
    }

    @Override
    public TetheredSkirge copy() {
        return new TetheredSkirge(this);
    }
}
