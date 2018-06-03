
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author TheElk801
 */
public final class KinjallisCaller extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dinosaur spells");

    static {
        filter.add(new SubtypePredicate(SubType.DINOSAUR));
    }

    public KinjallisCaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Dinosaur spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    public KinjallisCaller(final KinjallisCaller card) {
        super(card);
    }

    @Override
    public KinjallisCaller copy() {
        return new KinjallisCaller(this);
    }
}
