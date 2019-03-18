
package mage.cards.p;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.NamePredicate;

/**
 *
 * @author TheElk801
 */
public final class PowerstoneShard extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control named Powerstone Shard");

    static {
        filter.add(new NamePredicate("Powerstone Shard"));
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public PowerstoneShard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add {C} for each artifact you control named Powerstone Shard.
        this.addAbility(new DynamicManaAbility(Mana.ColorlessMana(1), new PermanentsOnBattlefieldCount(filter)));
    }

    public PowerstoneShard(final PowerstoneShard card) {
        super(card);
    }

    @Override
    public PowerstoneShard copy() {
        return new PowerstoneShard(this);
    }
}
