package mage.cards.c;

import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
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
public final class CandlekeepSage extends CardImpl {

    public CandlekeepSage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BACKGROUND);

        // Commander creatures you own have "When this creature enters or leaves the battlefield, draw a card."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                new EntersBattlefieldOrLeavesSourceTriggeredAbility(
                        new DrawCardSourceControllerEffect(1), false
                ).setTriggerPhrase("When this creature enters or leaves the battlefield, "),
                Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURES_OWNED_COMMANDER
        )));
    }

    private CandlekeepSage(final CandlekeepSage card) {
        super(card);
    }

    @Override
    public CandlekeepSage copy() {
        return new CandlekeepSage(this);
    }
}
