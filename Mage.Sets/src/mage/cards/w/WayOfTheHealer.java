package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.CadetToken;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WayOfTheHealer extends CardImpl {

    public WayOfTheHealer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Healer enters, empower Jace 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(5)));

        // Planeswalkers you control have "[-2]: Create a 2/2 colorless Wizard Soldier creature token named Cadet. Surveil 1."
        Ability loyaltyAbility = new LoyaltyAbility(new CreateTokenEffect(new CadetToken()), -2);
        loyaltyAbility.addEffect(new SurveilEffect(1));

        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            loyaltyAbility,
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"[-2]: Create a 2/2 colorless Wizard Soldier creature token named Cadet. Surveil 1.\""));
        this.addAbility(ability);
    }

    private WayOfTheHealer(final WayOfTheHealer card) {
        super(card);
    }

    @Override
    public WayOfTheHealer copy() {
        return new WayOfTheHealer(this);
    }
}
