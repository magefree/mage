package mage.cards.o;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.OphiomancerSnakeToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class Ophiomancer extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterCreaturePermanent(SubType.SNAKE, "you control no Snakes"), ComparisonType.EQUAL_TO, 0
    );

    public Ophiomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each upkeep, if you control no Snakes, create a 1/1 black Snake creature token with deathtouch.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new OphiomancerSnakeToken()), false
        ).withInterveningIf(condition));
    }

    private Ophiomancer(final Ophiomancer card) {
        super(card);
    }

    @Override
    public Ophiomancer copy() {
        return new Ophiomancer(this);
    }
}
