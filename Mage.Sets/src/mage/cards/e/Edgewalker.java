
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;

/**
 *
 * @author fireshoes
 */
public final class Edgewalker extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Cleric spells");
    static {
        filter.add(SubType.CLERIC.getPredicate());
    }

    public Edgewalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Cleric spells you cast cost {W}{B} less to cast. This effect reduces only the amount of colored mana you pay.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, new ManaCostsImpl<>("{W}{B}"))));
    }

    private Edgewalker(final Edgewalker card) {
        super(card);
    }

    @Override
    public Edgewalker copy() {
        return new Edgewalker(this);
    }
}
