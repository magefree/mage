package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.decorator.ConditionalAsThoughEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PlatypusBear extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.LESSON));
    private static final Hint hint = new ConditionHint(condition, "There is a Lesson card in your graveyard");

    public PlatypusBear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G/U}");

        this.subtype.add(SubType.PLATYPUS);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // When this creature enters, mill two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(2)));

        // As long as there is a Lesson card in your graveyard, this creature can attack as though it didn't have defender.
        this.addAbility(new SimpleStaticAbility(new ConditionalAsThoughEffect(
                new CanAttackAsThoughItDidntHaveDefenderSourceEffect(Duration.WhileOnBattlefield), condition
        ).setText("as long as there is a Lesson card in your graveyard, " +
                "this creature can attack as though it didn't have defender")).addHint(hint));
    }

    private PlatypusBear(final PlatypusBear card) {
        super(card);
    }

    @Override
    public PlatypusBear copy() {
        return new PlatypusBear(this);
    }
}
