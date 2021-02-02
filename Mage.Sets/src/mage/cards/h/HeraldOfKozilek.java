
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 *
 * @author fireshoes
 */
public final class HeraldOfKozilek extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Colorless spells");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public HeraldOfKozilek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{R}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        
        // Colorless spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SpellsCostReductionControllerEffect(filter, 1)));
    }

    private HeraldOfKozilek(final HeraldOfKozilek card) {
        super(card);
    }

    @Override
    public HeraldOfKozilek copy() {
        return new HeraldOfKozilek(this);
    }
}
