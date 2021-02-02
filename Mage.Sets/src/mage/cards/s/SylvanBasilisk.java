
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author anonymous
 */
public final class SylvanBasilisk extends CardImpl {

    public SylvanBasilisk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}{G}");
        this.subtype.add(SubType.BASILISK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Sylvan Basilisk becomes blocked by a creature, destroy that creature.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new DestroyTargetEffect(), false));
    }

    private SylvanBasilisk(final SylvanBasilisk card) {
        super(card);
    }

    @Override
    public SylvanBasilisk copy() {
        return new SylvanBasilisk(this);
    }
}
