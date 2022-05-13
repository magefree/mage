package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.TwoChoiceVote;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author fireshoes, TheElk801
 */
public final class MagisterOfWorth extends CardImpl {

    public MagisterOfWorth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{B}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Will of the council - When Magister of Worth enters the battlefield, starting with you, each player votes for grace or condemnation. If grace gets more votes, each player returns each creature card from their graveyard to the battlefield. If condemnation gets more votes or the vote is tied, destroy all creatures other than Magister of Worth.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new MagisterOfWorthEffect(), false)
                .setAbilityWord(AbilityWord.WILL_OF_THE_COUNCIL)
        );
    }

    private MagisterOfWorth(final MagisterOfWorth card) {
        super(card);
    }

    @Override
    public MagisterOfWorth copy() {
        return new MagisterOfWorth(this);
    }
}

class MagisterOfWorthEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    MagisterOfWorthEffect() {
        super(Outcome.Benefit);
        staticText = "starting with you, each player votes for grace or condemnation. " +
                "If grace gets more votes, each player returns each creature card from their graveyard to the battlefield. " +
                "If condemnation gets more votes or the vote is tied, destroy all creatures other than {this}.";
    }

    private MagisterOfWorthEffect(final MagisterOfWorthEffect effect) {
        super(effect);
    }

    @Override
    public MagisterOfWorthEffect copy() {
        return new MagisterOfWorthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Outcome.Benefit - AI will return from graveyard all the time (Grace choice)
        // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        TwoChoiceVote vote = new TwoChoiceVote("Grace (return from graveyard)", "Condemnation (destroy all)", Outcome.Benefit);
        vote.doVotes(source, game);

        int graceCount = vote.getVoteCount(true);
        int condemnationCount = vote.getVoteCount(false);
        if (condemnationCount >= graceCount) {
            return new DestroyAllEffect(filter).apply(game, source);
        }

        // grace win - each player returns each creature card from their graveyard to the battlefield
        Cards cards = new CardsImpl();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .filter(card -> card.isCreature(game))
                .forEach(cards::add);
        return controller.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                false, false, true, null
        );
    }
}
