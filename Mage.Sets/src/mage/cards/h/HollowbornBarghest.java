package mage.cards.h;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HollowbornBarghest extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.EQUAL_TO, 0, TargetController.ACTIVE);

    public HollowbornBarghest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.DOG);

        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // At the beginning of your upkeep, if you have no cards in hand, each opponent loses 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LoseLifeOpponentsEffect(2))
                .withInterveningIf(HellbentCondition.instance));

        // At the beginning of each opponent's upkeep, if that player has no cards in hand, they lose 2 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.OPPONENT, new LoseLifeTargetEffect(2).setText("they lose 2 life"), false
        ).withInterveningIf(condition));
    }

    private HollowbornBarghest(final HollowbornBarghest card) {
        super(card);
    }

    @Override
    public HollowbornBarghest copy() {
        return new HollowbornBarghest(this);
    }
}
