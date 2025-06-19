package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StalwartPathlighter extends CardImpl {

    public StalwartPathlighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Coven — At the beginning of combat on your turn, if you control three or more creatures with different powers, creatures you control gain indestructible until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new GainAbilityControlledEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        )).withInterveningIf(CovenCondition.instance).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private StalwartPathlighter(final StalwartPathlighter card) {
        super(card);
    }

    @Override
    public StalwartPathlighter copy() {
        return new StalwartPathlighter(this);
    }
}
