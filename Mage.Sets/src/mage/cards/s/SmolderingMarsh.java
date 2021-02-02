
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class SmolderingMarsh extends CardImpl {
    
    private static final FilterLandPermanent filter = new FilterLandPermanent();
    
    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public SmolderingMarsh(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.SWAMP);
        this.subtype.add(SubType.MOUNTAIN);

        // Smoldering Marsh enters the battlefield tapped unless you control two or more basic lands.
        Condition controls = new InvertCondition(new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1));
        String abilityText = "tapped unless you control two or more basic lands";
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new TapSourceEffect(), controls, abilityText), abilityText));
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private SmolderingMarsh(final SmolderingMarsh card) {
        super(card);
    }

    @Override
    public SmolderingMarsh copy() {
        return new SmolderingMarsh(this);
    }
}
