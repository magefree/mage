package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.YouGainedOrLostLifeCondition;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarlitSoothsayer extends CardImpl {

    public StarlitSoothsayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.BAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, if you gained or lost life this turn, surveil 1.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new SurveilEffect(1),
                false, YouGainedOrLostLifeCondition.instance
        ).addHint(YouGainedOrLostLifeCondition.getHint()), new PlayerGainedLifeWatcher());
    }

    private StarlitSoothsayer(final StarlitSoothsayer card) {
        super(card);
    }

    @Override
    public StarlitSoothsayer copy() {
        return new StarlitSoothsayer(this);
    }
}
