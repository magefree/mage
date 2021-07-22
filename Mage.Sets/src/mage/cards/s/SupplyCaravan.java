
package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.WarriorVigilantToken;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class SupplyCaravan extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("a tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public SupplyCaravan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.CAMEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When Supply Caravan enters the battlefield, if you control a tapped creature, create a 1/1 white Warrior creature token with vigilance.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new WarriorVigilantToken())),
                new PermanentsOnTheBattlefieldCondition(filter),
                "When {this} enters the battlefield, if you control a tapped creature, create a 1/1 white Warrior creature token with vigilance."));
    }

    private SupplyCaravan(final SupplyCaravan card) {
        super(card);
    }

    @Override
    public SupplyCaravan copy() {
        return new SupplyCaravan(this);
    }
}
