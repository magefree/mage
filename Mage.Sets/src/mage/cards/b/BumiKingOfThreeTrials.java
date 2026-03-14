package mage.cards.b;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.LessonsInGraveCondition;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.keyword.EarthbendTargetEffect;
import mage.abilities.effects.keyword.ScryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledLandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BumiKingOfThreeTrials extends CardImpl {

    public BumiKingOfThreeTrials(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Bumi enters, choose up to X, where X is the number of Lesson cards in your graveyard --
        // * Put three +1/+1 counters on Bumi.
        // * Target player scries 3.
        // * Earthbend 3.
        this.addAbility(new BumiKingOfThreeTrialsTriggeredAbility());
    }

    private BumiKingOfThreeTrials(final BumiKingOfThreeTrials card) {
        super(card);
    }

    @Override
    public BumiKingOfThreeTrials copy() {
        return new BumiKingOfThreeTrials(this);
    }
}

class BumiKingOfThreeTrialsTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(new FilterCard(SubType.LESSON));

    BumiKingOfThreeTrialsTriggeredAbility() {
        super(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)));
        this.getModes().setChooseText("choose up to X, where X is the number of Lesson cards in your graveyard &mdash;");
        this.getModes().setMinModes(0);
        this.addMode(new Mode(new ScryTargetEffect(3)).addTarget(new TargetPlayer()));
        this.addMode(new Mode(new EarthbendTargetEffect(3)).addTarget(new TargetControlledLandPermanent()));
        this.addHint(LessonsInGraveCondition.getHint());
    }

    private BumiKingOfThreeTrialsTriggeredAbility(final BumiKingOfThreeTrialsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BumiKingOfThreeTrialsTriggeredAbility copy() {
        return new BumiKingOfThreeTrialsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        this.getModes().setMaxModes(xValue.calculate(game, this, null));
        return true;
    }
}
