package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.MenaceAbility;
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
public final class CriminalPast extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);

    public CriminalPast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have menace and "This creature gets +X/+0, where X is the number of creature cards in your graveyard."
        Ability ability = new SimpleStaticAbility(new GainAbilityAllEffect(
                new MenaceAbility(false), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ));
        ability.addEffect(new GainAbilityAllEffect(
                new SimpleStaticAbility(new BoostSourceEffect(
                        xValue, StaticValue.get(0), Duration.WhileOnBattlefield, false, "this creature"
                )), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        ).setText("and \"This creature gets +X/+0, where X is the number of creature cards in your graveyard.\" " +
                "<i>(A creature with menace can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);
    }

    private CriminalPast(final CriminalPast card) {
        super(card);
    }

    @Override
    public CriminalPast copy() {
        return new CriminalPast(this);
    }
}
