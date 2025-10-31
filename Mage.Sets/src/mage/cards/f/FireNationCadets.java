package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInControllerGraveyardCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FirebendingAbility;
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
public final class FireNationCadets extends CardImpl {

    private static final Condition condition = new CardsInControllerGraveyardCondition(1, new FilterCard(SubType.LESSON));
    private static final Hint hint = new ConditionHint(condition, "There's a Lesson card in your graveyard");

    public FireNationCadets(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // This creature has firebending 2 as long as there's a Lesson card in your graveyard.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new FirebendingAbility(2)), condition,
                "{this} has firebending 2 as long as there's a Lesson card in your graveyard"
        )).addHint(hint));

        // {2}: This creature gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(2, 0, Duration.EndOfTurn), new GenericManaCost(2)
        ));
    }

    private FireNationCadets(final FireNationCadets card) {
        super(card);
    }

    @Override
    public FireNationCadets copy() {
        return new FireNationCadets(this);
    }
}
