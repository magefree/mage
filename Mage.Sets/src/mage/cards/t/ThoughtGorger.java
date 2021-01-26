package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class ThoughtGorger extends CardImpl {

    public ThoughtGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(TrampleAbility.getInstance());

        // When Thought Gorger enters the battlefield, put a +1/+1 counter on it for each card in your hand. If you do, discard your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ThoughtGorgerEffectEnters()));

        // When Thought Gorger leaves the battlefield, draw a card for each +1/+1 counter on it.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new ThoughtGorgerEffectLeaves(), false));
    }

    private ThoughtGorger(final ThoughtGorger card) {
        super(card);
    }

    @Override
    public ThoughtGorger copy() {
        return new ThoughtGorger(this);
    }

}

class ThoughtGorgerEffectEnters extends OneShotEffect {

    ThoughtGorgerEffectEnters() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on it for each card in your hand. If you do, discard your hand.";
    }

    private ThoughtGorgerEffectEnters(final ThoughtGorgerEffectEnters effect) {
        super(effect);
    }

    @Override
    public ThoughtGorgerEffectEnters copy() {
        return new ThoughtGorgerEffectEnters(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent thoughtGorger = game.getPermanent(source.getSourceId());
        if (player == null
                || player.getHand().isEmpty()
                || thoughtGorger == null
                || !thoughtGorger.addCounters(
                CounterType.P1P1.createInstance(player.getHand().size()), source.getControllerId(), source, game
        )) {
            return false;
        }
        player.discard(player.getHand(), false, source, game);
        return true;
    }
}

class ThoughtGorgerEffectLeaves extends OneShotEffect {

    ThoughtGorgerEffectLeaves() {
        super(Outcome.Neutral);
        this.staticText = "draw a card for each +1/+1 counter on it.";
    }

    private ThoughtGorgerEffectLeaves(final ThoughtGorgerEffectLeaves effect) {
        super(effect);
    }

    @Override
    public ThoughtGorgerEffectLeaves copy() {
        return new ThoughtGorgerEffectLeaves(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent thoughtGorgerLastState = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        int numberCounters = thoughtGorgerLastState.getCounters(game).getCount(CounterType.P1P1);
        if (player != null) {
            player.drawCards(numberCounters, source, game);
            return true;
        }
        return false;
    }
}

