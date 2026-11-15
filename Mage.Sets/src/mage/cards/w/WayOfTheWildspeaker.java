package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Beast44TrampleToken;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
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
public final class WayOfTheWildspeaker extends CardImpl {

    public WayOfTheWildspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Wildspeaker enters, empower Jace 7.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(7)));

        // Planeswalkers you control have "[-4]: Create a 4/4 green Beast creature token with trample."
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
            new LoyaltyAbility(new CreateTokenEffect(new Beast44TrampleToken()), -4),
            Duration.WhileOnBattlefield, StaticFilters.FILTER_CONTROLLED_PERMANENT_PLANESWALKER
        ).setText("Planeswalkers you control have \"-4: Create a 4/4 green Beast creature token with trample.\""));
        this.addAbility(ability);
    }

    private WayOfTheWildspeaker(final WayOfTheWildspeaker card) {
        super(card);
    }

    @Override
    public WayOfTheWildspeaker copy() {
        return new WayOfTheWildspeaker(this);
    }
}
