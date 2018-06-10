
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author Backfir3
 */
public final class TreefolkSeedlings extends CardImpl {

    final static FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(new SubtypePredicate(SubType.FOREST));
    }

    public TreefolkSeedlings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Treefolk Seedlings's toughness is equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.EndOfGame)));
    }

    public TreefolkSeedlings(final TreefolkSeedlings card) {
        super(card);
    }

    @Override
    public TreefolkSeedlings copy() {
        return new TreefolkSeedlings(this);
    }

}
