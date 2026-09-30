package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.CopyNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WayOfTheCryomancer extends CardImpl {

    public WayOfTheCryomancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Cryomancer enters, empower Jace 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(5)));

        // Planeswalkers you control have "[−3]: When you next cast an instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(new CopyNextSpellDelayedTriggeredAbility()), -3),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"-3: When you next cast an instant or sorcery spell this turn, copy that spell. You may choose new targets for the copy.\""));
        this.addAbility(ability);
    }

    private WayOfTheCryomancer(final WayOfTheCryomancer card) {
        super(card);
    }

    @Override
    public WayOfTheCryomancer copy() {
        return new WayOfTheCryomancer(this);
    }
}
