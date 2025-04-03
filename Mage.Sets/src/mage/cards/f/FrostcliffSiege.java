package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author androosss
 */
public final class FrostcliffSiege extends CardImpl {

    private static final String rule1Trigger = "&bull; Jeskai &mdash; Whenever one or more creatures you control deal combat damage to a player, draw a card.";

    public FrostcliffSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{R}");
        

        // As this enchantment enters, choose Jeskai or Temur.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Jeskai or Temur?", "Jeskai", "Temur"), null,
                "As {this} enters, choose Jeskai or Temur.", ""));

        // * Jeskai -- Whenever one or more creatures you control deal combat damage to a player, draw a card.
        this.addAbility(new ConditionalTriggeredAbility(
                new OneOrMoreCombatDamagePlayerTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                new ModeChoiceSourceCondition("Jeskai"),
                rule1Trigger));

        // * Temur -- Creatures you control get +1/+0 and have trample and haste.
        Ability temurAbility = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new BoostControlledEffect(1, 0, Duration.WhileOnBattlefield),
            new ModeChoiceSourceCondition("Temur"),
            "&bull; Temur &mdash; Creatures you control get +1/+0"
        ));
        temurAbility.addEffect(new ConditionalContinuousEffect(
            new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES),
            new ModeChoiceSourceCondition("Temur"),
            "and have trample"
        ));
        temurAbility.addEffect(new ConditionalContinuousEffect(
            new GainAbilityControlledEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, StaticFilters.FILTER_PERMANENT_CREATURES),
            new ModeChoiceSourceCondition("Temur"),
            "and haste."
        ));
        this.addAbility(temurAbility);
    }

    private FrostcliffSiege(final FrostcliffSiege card) {
        super(card);
    }

    @Override
    public FrostcliffSiege copy() {
        return new FrostcliffSiege(this);
    }

}
