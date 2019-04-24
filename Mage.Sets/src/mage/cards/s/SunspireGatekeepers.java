

package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.KnightToken;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */


public final class SunspireGatekeepers extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
    static {
        filter.add(new SubtypePredicate(SubType.GATE));
    }

    public SunspireGatekeepers (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // When Sunspire Gatekeepers enter the battlefield, if you control two or more Gates, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KnightToken())),
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1),
                "When {this} enters the battlefield, if you control two or more Gates, create a 2/2 white Knight creature token with vigilance."));
    }

    public SunspireGatekeepers (final SunspireGatekeepers card) {
        super(card);
    }

    @Override
    public SunspireGatekeepers copy() {
        return new SunspireGatekeepers(this);
    }

}
