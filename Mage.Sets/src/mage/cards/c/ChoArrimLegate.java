
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class ChoArrimLegate extends CardImpl {
    
    private static final FilterPermanent filterPlains = new FilterPermanent();
    private static final FilterPermanent filterSwamp = new FilterPermanent();

    static {
        filterPlains.add(SubType.PLAINS.getPredicate());
        filterSwamp.add(SubType.SWAMP.getPredicate());
    }

    public ChoArrimLegate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));
        
        // If an opponent controls a Swamp and you control a Plains, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Swamp and you control a Plains", 
                new OpponentControlsPermanentCondition(filterSwamp),
                new PermanentsOnTheBattlefieldCondition(filterPlains));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));
    }

    private ChoArrimLegate(final ChoArrimLegate card) {
        super(card);
    }

    @Override
    public ChoArrimLegate copy() {
        return new ChoArrimLegate(this);
    }
}
