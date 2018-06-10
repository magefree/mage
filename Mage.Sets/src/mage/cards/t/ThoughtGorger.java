
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class ThoughtGorger extends CardImpl {

    public ThoughtGorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(TrampleAbility.getInstance());

        // When Thought Gorger enters the battlefield, put a +1/+1 counter on it for each card in your hand. If you do, discard your hand.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(new ThoughtGorgerEffectEnters());
        this.addAbility(ability1);

        // When Thought Gorger leaves the battlefield, draw a card for each +1/+1 counter on it.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ThoughtGorgerEffectLeaves(), false);
        this.addAbility(ability2);
    }

    public ThoughtGorger(final ThoughtGorger card) {
        super(card);
    }

    @Override
    public ThoughtGorger copy() {
        return new ThoughtGorger(this);
    }

}

class ThoughtGorgerEffectEnters extends OneShotEffect {

    public ThoughtGorgerEffectEnters() {
        super(Outcome.Benefit);
        this.staticText = "put a +1/+1 counter on it for each card in your hand. If you do, discard your hand.";
    }

    public ThoughtGorgerEffectEnters(final ThoughtGorgerEffectEnters effect) {
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
        if (player != null && !player.getHand().isEmpty() && thoughtGorger != null ) {
            int cardsInHand = player.getHand().size();
            thoughtGorger.addCounters(CounterType.P1P1.createInstance(cardsInHand), source, game);
            player.discard(cardsInHand, false, source, game);
            return true;
        }
        return false;
    }
}

class ThoughtGorgerEffectLeaves extends OneShotEffect {

    public ThoughtGorgerEffectLeaves() {
        super(Outcome.Neutral);
        this.staticText = "draw a card for each +1/+1 counter on it.";
    }

    public ThoughtGorgerEffectLeaves(final ThoughtGorgerEffectLeaves effect) {
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
            player.drawCards(numberCounters, game);
            return true;
        }
        return false;
    }
}

