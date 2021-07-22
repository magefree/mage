
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;

/**
 *
 * @author Eirkei
 */
public final class KrosanDrover extends CardImpl {

    private static final FilterCard filter = new FilterCard("Creature spells with mana value 6 or greater");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 5));
    }
    
    public KrosanDrover(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Creature spells you cast with converted mana cost 6 or greater cost {2} less to cast.
        Effect effect = new SpellsCostReductionControllerEffect(filter, 2);
        effect.setText("Creature spells you cast with mana value 6 or greater cost {2} less to cast.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    private KrosanDrover(final KrosanDrover card) {
        super(card);
    }

    @Override
    public KrosanDrover copy() {
        return new KrosanDrover(this);
    }
}
