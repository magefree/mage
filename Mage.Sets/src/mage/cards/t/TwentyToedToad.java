package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.effects.common.continuous.MaximumHandSizeControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TwentyToedToad extends CardImpl {

    public TwentyToedToad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.FROG, SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Your maximum hand size is twenty.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MaximumHandSizeControllerEffect(20, Duration.WhileOnBattlefield, MaximumHandSizeControllerEffect.HandSizeModification.SET)));

        // Whenever you attack with two or more creatures, put a +1/+1 counter on Twenty-Toed Toad and draw a card.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), 2);
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy(" and"));
        this.addAbility(ability);

        // Whenever Twenty-Toed Toad attacks, you win the game if there are twenty or more counters on it or you have twenty or more cards in hand.
        this.addAbility(new AttacksTriggeredAbility(new ConditionalOneShotEffect(
                new WinGameSourceControllerEffect(), TwentyToedToadCondition.instance
        ).setText("you win the game if there are twenty or more counters on it or you have twenty or more cards in hand")));
    }

    private TwentyToedToad(final TwentyToedToad card) {
        super(card);
    }

    @Override
    public TwentyToedToad copy() {
        return new TwentyToedToad(this);
    }
}

enum TwentyToedToadCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return player != null && (player.getHand().size() >= 20 || (permanent != null && permanent.getCounters(game).getTotalCount() >= 20));
    }
}
