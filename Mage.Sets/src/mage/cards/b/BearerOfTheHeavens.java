package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class BearerOfTheHeavens extends CardImpl {

    public BearerOfTheHeavens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{R}");
        this.subtype.add(SubType.GIANT);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When Bearer of the Heavens dies, destroy all permanents at the beginning of the next end step.
        DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new DestroyAllEffect(StaticFilters.FILTER_PERMANENT));
        Effect effect = new CreateDelayedTriggeredAbilityEffect(delayedAbility);
        effect.setText("destroy all permanents at the beginning of the next end step");
        this.addAbility(new DiesSourceTriggeredAbility(effect, false));
    }

    private BearerOfTheHeavens(final BearerOfTheHeavens card) {
        super(card);
    }

    @Override
    public BearerOfTheHeavens copy() {
        return new BearerOfTheHeavens(this);
    }
}
