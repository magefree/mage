
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class SedraxisAlchemist extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("blue permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }
    
    private static final String rule = "When {this} enters the battlefield, if you control a blue permanent, return target nonland permanent to its owner's hand.";

    public SedraxisAlchemist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Sedraxis Alchemist enters the battlefield, if you control a blue permanent, return target nonland permanent to its owner's hand.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new PermanentsOnTheBattlefieldCondition(filter), rule));
        
    }

    private SedraxisAlchemist(final SedraxisAlchemist card) {
        super(card);
    }

    @Override
    public SedraxisAlchemist copy() {
        return new SedraxisAlchemist(this);
    }
}
