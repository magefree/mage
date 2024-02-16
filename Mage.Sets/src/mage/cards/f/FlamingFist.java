package mage.cards.f;

import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlamingFist extends CardImpl {

    public FlamingFist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "Whenever this creature attacks, it gains double strike until end of turn."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new AttacksTriggeredAbility(new GainAbilitySourceEffect(
                        DoubleStrikeAbility.getInstance(), Duration.EndOfTurn
                ).setText("it gains double strike until end of turn")).setTriggerPhrase("Whenever this creature attacks, "),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private FlamingFist(final FlamingFist card) {
        super(card);
    }

    @Override
    public FlamingFist copy() {
        return new FlamingFist(this);
    }
}
