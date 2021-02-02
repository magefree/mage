
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author cbt33
 */
public final class ShiftingSliver extends CardImpl {

    private static final FilterCreaturePermanent filterCreatures = new FilterCreaturePermanent("Slivers");
    private static final FilterCreaturePermanent filterBlockedBy = new FilterCreaturePermanent("except by Slivers");

    static {
        filterCreatures.add(SubType.SLIVER.getPredicate());
        filterBlockedBy.add(Predicates.not(SubType.SLIVER.getPredicate()));
    }

    public ShiftingSliver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.SLIVER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Slivers can't be blocked except by Slivers.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByCreaturesAllEffect(filterCreatures, filterBlockedBy, Duration.WhileOnBattlefield)));
    }

    private ShiftingSliver(final ShiftingSliver card) {
        super(card);
    }

    @Override
    public ShiftingSliver copy() {
        return new ShiftingSliver(this);
    }
}
