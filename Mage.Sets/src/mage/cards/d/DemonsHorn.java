

package mage.cards.d;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class DemonsHorn extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a black spell");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    public DemonsHorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        
        // Whenever a player casts a black spell, you may gain 1 life.
        this.addAbility(new SpellCastAllTriggeredAbility(new GainLifeEffect(StaticValue.get(1), "you may gain 1 life"), filter, true));
    }

    private DemonsHorn(final DemonsHorn card) {
        super(card);
    }

    @Override
    public DemonsHorn copy() {
        return new DemonsHorn(this);
    }

}
