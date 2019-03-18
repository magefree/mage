
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.permanent.token.CrestedSunmareToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

/**
 *
 * @author emerald000
 */
public final class CrestedSunmare extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Horses you control");

    static {
        filter.add(new SubtypePredicate(SubType.HORSE));
    }

    public CrestedSunmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Other Horses you control have indestructible.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true)));

        // At the beginning of each end step, if you gained life this turn, create a 5/5 white Horse creature token.
        this.addAbility(
                new ConditionalInterveningIfTriggeredAbility(
                    new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new CrestedSunmareToken()), TargetController.ANY, false),
                    new YouGainedLifeCondition(ComparisonType.MORE_THAN, 0),
                    "At the beginning of each end step, if you gained life this turn, create a 5/5 white Horse creature token."),
                new PlayerGainedLifeWatcher());
    }

    public CrestedSunmare(final CrestedSunmare card) {
        super(card);
    }

    @Override
    public CrestedSunmare copy() {
        return new CrestedSunmare(this);
    }
}
