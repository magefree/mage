package mage.cards.i;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IndulgingPatrician extends CardImpl {

    public IndulgingPatrician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // At the beginning of your end step, if you gained 3 or more life this turn, each opponent loses 3 life.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new LoseLifeOpponentsEffect(3), TargetController.YOU, false
                ), IndulgingPatricianCondition.instance, "At the beginning of your end step, " +
                "if you gained 3 or more life this turn, each opponent loses 3 life."
        ), new PlayerGainedLifeWatcher());
    }

    private IndulgingPatrician(final IndulgingPatrician card) {
        super(card);
    }

    @Override
    public IndulgingPatrician copy() {
        return new IndulgingPatrician(this);
    }
}

enum IndulgingPatricianCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        PlayerGainedLifeWatcher watcher = game.getState().getWatcher(PlayerGainedLifeWatcher.class);
        return watcher != null && watcher.getLifeGained(source.getControllerId()) >= 3;
    }
}
