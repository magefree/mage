
package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.PreventAllDamageFromChosenSourceToYouEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ConsulateSurveillance extends CardImpl {

    public ConsulateSurveillance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When Consulate Surveillance enters the battlefield, you get {E}{E}{E}{E}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GetEnergyCountersControllerEffect(4)));

        // Pay {E}{E}: Prevent all damage that would be dealt to you this turn by a source of your choice.
        this.addAbility(new SimpleActivatedAbility(
                new PreventAllDamageFromChosenSourceToYouEffect(Duration.EndOfTurn),
                new PayEnergyCost(2)));

    }

    private ConsulateSurveillance(final ConsulateSurveillance card) {
        super(card);
    }

    @Override
    public ConsulateSurveillance copy() {
        return new ConsulateSurveillance(this);
    }
}
