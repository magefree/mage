
package mage.cards.d;

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
 * @author fireshoes
 */
public final class DragonlordsServant extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Dragon spells");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public DragonlordsServant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Dragon spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private DragonlordsServant(final DragonlordsServant card) {
        super(card);
    }

    @Override
    public DragonlordsServant copy() {
        return new DragonlordsServant(this);
    }
}
