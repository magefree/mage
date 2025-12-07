package mage.cards.g;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.MillThenPutInHandEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.RoomCard;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreenhouseRicketyGazebo extends RoomCard {
    public GreenhouseRicketyGazebo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}", "{3}{G}");

        // Greenhouse
        // Lands you control have "{T}: Add one mana of any color."
        ContinuousEffect effect = new GainAbilityControlledEffect(
                new AnyColorManaAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_LANDS, false
        );
        effect.addDependedToType(DependencyType.BecomeNonbasicLand);
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(effect));

        // Rickety Gazebo
        // When you unlock this door, mill four cards, then return up to two permanent cards from among them to your hand.
        this.getRightHalfCard().addAbility(new UnlockThisDoorTriggeredAbility(new MillThenPutInHandEffect(
                4, StaticFilters.FILTER_CARD_PERMANENTS, null, true, 2
        ), false, false));
    }

    private GreenhouseRicketyGazebo(final GreenhouseRicketyGazebo card) {
        super(card);
    }

    @Override
    public GreenhouseRicketyGazebo copy() {
        return new GreenhouseRicketyGazebo(this);
    }
}
