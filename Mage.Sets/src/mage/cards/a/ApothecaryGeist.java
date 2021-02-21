
package mage.cards.a;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class ApothecaryGeist extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another Elf");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.SPIRIT.getPredicate());
    }

    public ApothecaryGeist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Apothecary Geist enters the battlefield, if you control another Spirit, you gain 3 life.
        TriggeredAbility triggeredAbility = new EntersBattlefieldTriggeredAbility(new GainLifeEffect(3));
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                triggeredAbility,
                new PermanentsOnTheBattlefieldCondition(filter),
                "When {this} enters the battlefield, if you control another Spirit, you gain 3 life."));
    }

    private ApothecaryGeist(final ApothecaryGeist card) {
        super(card);
    }

    @Override
    public ApothecaryGeist copy() {
        return new ApothecaryGeist(this);
    }
}
