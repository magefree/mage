
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.SetBaseToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class PeopleOfTheWoods extends CardImpl {
    
    static final FilterControlledPermanent filterLands = new FilterControlledPermanent("Forests you control");

    static {
        filterLands.add(SubType.FOREST.getPredicate());
    }

    public PeopleOfTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        this.subtype.add(SubType.HUMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(0);

        // People of the Woods's toughness is equal to the number of Forests you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBaseToughnessSourceEffect(new PermanentsOnBattlefieldCount(filterLands))));
    }

    private PeopleOfTheWoods(final PeopleOfTheWoods card) {
        super(card);
    }

    @Override
    public PeopleOfTheWoods copy() {
        return new PeopleOfTheWoods(this);
    }
}
