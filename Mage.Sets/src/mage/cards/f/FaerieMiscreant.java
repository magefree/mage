
package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class FaerieMiscreant extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("you control another creature named Faerie Miscreant");

    static {
        filter.add(new NamePredicate("Faerie Miscreant"));
        filter.add(new AnotherPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public FaerieMiscreant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Faerie Miscreant enters the battlefield, if you control another creature named Faerie Miscreant, draw a card.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1), false),
                new PermanentsOnTheBattlefieldCondition(filter),
                "When Faerie Miscreant enters the battlefield, if you control another creature named Faerie Miscreant, draw a card");
        this.addAbility(ability);
    }

    public FaerieMiscreant(final FaerieMiscreant card) {
        super(card);
    }

    @Override
    public FaerieMiscreant copy() {
        return new FaerieMiscreant(this);
    }
}
