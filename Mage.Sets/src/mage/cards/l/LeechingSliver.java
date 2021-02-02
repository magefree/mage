
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksAllTriggeredAbility;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 * @author noxx
 */
public final class LeechingSliver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Sliver you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.SLIVER.getPredicate());
    }

    public LeechingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever a Sliver you control attacks, defending player loses 1 life.
        this.addAbility(new AttacksAllTriggeredAbility(new LoseLifeDefendingPlayerEffect(1, false), false, filter, SetTargetPointer.PLAYER, false));
    }

    private LeechingSliver(final LeechingSliver card) {
        super(card);
    }

    @Override
    public LeechingSliver copy() {
        return new LeechingSliver(this);
    }
}
