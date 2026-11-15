package mage.cards.w;

import java.util.UUID;
import mage.constants.SuperType;
import mage.abilities.Ability;
import mage.abilities.common.ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class WayOfTheParadox extends CardImpl {

    public WayOfTheParadox(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);

        // When Way of the Paradox enters, empower Jace 5.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EmpowerJaceEffect(5)));

        // Whenever you activate a loyalty ability, you gain 1 life. You may play an additional land this turn.
        Ability ability = new ActivatePlaneswalkerLoyaltyAbilityTriggeredAbility(new GainLifeEffect(1), SetTargetPointer.NONE);
        ability.addEffect(new PlayAdditionalLandsControllerEffect(1, Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private WayOfTheParadox(final WayOfTheParadox card) {
        super(card);
    }

    @Override
    public WayOfTheParadox copy() {
        return new WayOfTheParadox(this);
    }
}
