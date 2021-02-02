
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureAllTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author cbt33
 */
public final class ToxinSliver extends CardImpl {

    public ToxinSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a Sliver deals combat damage to a creature, destroy that creature. It can't be regenerated.
        this.addAbility(new DealsDamageToACreatureAllTriggeredAbility(
                new DestroyTargetEffect(true), false,
                new FilterCreaturePermanent(SubType.SLIVER, "a Sliver"),
                SetTargetPointer.PERMANENT_TARGET, true));

    }

    private ToxinSliver(final ToxinSliver card) {
        super(card);
    }

    @Override
    public ToxinSliver copy() {
        return new ToxinSliver(this);
    }
}
