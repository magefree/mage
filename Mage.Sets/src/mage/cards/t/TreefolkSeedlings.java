
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Backfir3
 */
public final class TreefolkSeedlings extends CardImpl {

    static final FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(SubType.FOREST.getPredicate());
    }

    public TreefolkSeedlings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(0);

        // Treefolk Seedlings's toughness is equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBaseToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands), Duration.EndOfGame)));
    }

    private TreefolkSeedlings(final TreefolkSeedlings card) {
        super(card);
    }

    @Override
    public TreefolkSeedlings copy() {
        return new TreefolkSeedlings(this);
    }

}
