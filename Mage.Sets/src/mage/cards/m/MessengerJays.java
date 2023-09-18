package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author JRHerlehy, TheElk801
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
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MessengerJaysEffect(), false)
                .setAbilityWord(AbilityWord.COUNCILS_DILEMMA)
        );
    }

    private MessengerJays(final MessengerJays card) {
        super(card);
    }

    @Override
    public MessengerJays copy() {
        return new MessengerJays(this);
    }
}

class MessengerJaysEffect extends OneShotEffect {

    MessengerJaysEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for feather or quill. " +
                "Put a +1/+1 counter on {this} for each feather vote " +
                "and draw a card for each quill vote. For each card drawn this way, discard a card.";
    }

    private MessengerJaysEffect(final MessengerJaysEffect effect) {
        super(effect);
    }

    @Override
    public MessengerJaysEffect copy() {
        return new MessengerJaysEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Outcome.Benefit - AI will boost all the time (Feather choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Feather (+1/+1 counter)", "Quill (draw a card)", Outcome.Benefit);
        vote.doVotes(source, game);

        int featherCount = vote.getVoteCount(true);
        int quillCount = vote.getVoteCount(false);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (featherCount > 0 && permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(featherCount), source.getControllerId(), source, game);
        }

        Player player = game.getPlayer(source.getControllerId());
        if (quillCount > 0 && player != null) {
            int drawn = player.drawCards(quillCount, source, game);
            player.discard(drawn, false, false, source, game);
        }

        return featherCount + quillCount > 0;
    }
}
