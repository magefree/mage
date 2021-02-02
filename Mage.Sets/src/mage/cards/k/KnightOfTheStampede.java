
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author North & L_J
 */
public final class KnightOfTheStampede extends CardImpl {

    private static final FilterCard filter = new FilterCard("Dinosaur spells");

    static {
        filter.add(SubType.DINOSAUR.getPredicate());
    }

    public KnightOfTheStampede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Dinosaur spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 2)));
    }

    private KnightOfTheStampede(final KnightOfTheStampede card) {
        super(card);
    }

    @Override
    public KnightOfTheStampede copy() {
        return new KnightOfTheStampede(this);
    }
}
