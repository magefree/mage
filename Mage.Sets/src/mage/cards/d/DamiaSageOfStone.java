package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.effects.common.DrawCardsEqualToDifferenceEffect;
import mage.abilities.effects.common.SkipDrawStepEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class DamiaSageOfStone extends CardImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.FEWER_THAN, 7);

    public DamiaSageOfStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{G}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GORGON);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Skip your draw step.
        this.addAbility(new SimpleStaticAbility(new SkipDrawStepEffect()));

        // At the beginning of your upkeep, if you have fewer than seven cards in hand, draw cards equal to the difference.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardsEqualToDifferenceEffect(7)).withInterveningIf(condition));
    }

    private DamiaSageOfStone(final DamiaSageOfStone card) {
        super(card);
    }

    @Override
    public DamiaSageOfStone copy() {
        return new DamiaSageOfStone(this);
    }
}
