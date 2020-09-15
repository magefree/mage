
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CouncilsDilemmaVoteEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author JRHerlehy
 */
public final class MessengerJays extends CardImpl {

    public MessengerJays(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // <i>Council's dilemma &mdash; When Messenger Jays enters the battlefield, starting with you, each player votes for feather or quill. Put a +1/+1 counter on Messenger Jays for each feather vote and draw a card for each quill vote. For each card drawn this way, discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MessengerJaysDilemmaEffect(), false, "<i>Council's dilemma</i> &mdash; "));
    }

    public MessengerJays(final MessengerJays card) {
        super(card);
    }

    @Override
    public MessengerJays copy() {
        return new MessengerJays(this);
    }
}

class MessengerJaysDilemmaEffect extends CouncilsDilemmaVoteEffect {

    public MessengerJaysDilemmaEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for feather or quill. Put a +1/+1 counter on {this} for each feather vote and draw a card for each quill vote. For each card drawn this way, discard a card.";
    }

    public MessengerJaysDilemmaEffect(final MessengerJaysDilemmaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());

        //If no controller, exit out here and do not vote.
        if (controller == null) return false;

        this.vote("feather", "quill", controller, game, source);

        Permanent permanent = game.getPermanent(source.getSourceId());

        //Feathers Votes
        //If feathers received zero votes or the permanent is no longer on the battlefield, do not attempt to put P1P1 counter on it.
        if (voteOneCount > 0 && permanent != null)
            permanent.addCounters(CounterType.P1P1.createInstance(voteOneCount), source, game);

        //Quill Votes
        //Only let the controller loot the appropriate amount of cards if it was voted for.
        if (voteTwoCount > 0) {
            Effect lootCardsEffect = new DrawDiscardControllerEffect(voteTwoCount, voteTwoCount);
            lootCardsEffect.apply(game, source);
        }

        return true;
    }

    @Override
    public MessengerJaysDilemmaEffect copy() {
        return new MessengerJaysDilemmaEffect(this);
    }
}
