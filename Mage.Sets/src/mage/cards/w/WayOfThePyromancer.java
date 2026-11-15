package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class WayOfThePyromancer extends CardImpl {

    public WayOfThePyromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Pyromancer enters, empower Jace 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(2)));

        // Planeswalkers you control have "[+1]: Add {R}."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            new LoyaltyAbility(new BasicManaEffect(Mana.RedMana(1)), 1),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"+1: Add {R}.\""));
        this.addAbility(ability);
    }

    private WayOfThePyromancer(final WayOfThePyromancer card) {
        super(card);
    }

    @Override
    public WayOfThePyromancer copy() {
        return new WayOfThePyromancer(this);
    }
}
