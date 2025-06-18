package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.CrestedSunmareToken;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class CrestedSunmare extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Horses you control");

    static {
        filter.add(SubType.HORSE.getPredicate());
    }

    private static final Condition condition = new YouGainedLifeCondition();

    public CrestedSunmare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Other Horses you control have indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        )));

        // At the beginning of each end step, if you gained life this turn, create a 5/5 white Horse creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new CreateTokenEffect(new CrestedSunmareToken()), false
        ).withInterveningIf(condition).addHint(ControllerGainedLifeCount.getHint()), new PlayerGainedLifeWatcher());
    }

    private CrestedSunmare(final CrestedSunmare card) {
        super(card);
    }

    @Override
    public CrestedSunmare copy() {
        return new CrestedSunmare(this);
    }
}
