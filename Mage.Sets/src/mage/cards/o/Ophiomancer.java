
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.OphiomancerSnakeToken;

/**
 *
 * @author LevelX2
 */
public final class Ophiomancer extends CardImpl {

    public Ophiomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if you control no Snakes, create a 1/1 black Snake creature token with deathtouch.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new OphiomancerSnakeToken()), TargetController.ANY, false, false),
                new PermanentsOnTheBattlefieldCondition(new FilterCreaturePermanent(SubType.SNAKE, "no Snakes"), ComparisonType.EQUAL_TO, 0),
                "At the beginning of each upkeep, if you control no Snakes, create a 1/1 black Snake creature token with deathtouch."));
    }

    private Ophiomancer(final Ophiomancer card) {
        super(card);
    }

    @Override
    public Ophiomancer copy() {
        return new Ophiomancer(this);
    }
}
