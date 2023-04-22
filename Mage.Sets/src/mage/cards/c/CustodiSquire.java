package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.VoteHandler;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2, TheElk801
 */
public final class CustodiSquire extends CardImpl {

    public CustodiSquire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Will of the council - When Custodi Squire enters the battlefield, starting with you, each player votes for an artifact, creature, or enchantment card in your graveyard. Return each card with the most votes or tied for most votes to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CustodiSquireVoteEffect(), false)
                .setAbilityWord(AbilityWord.WILL_OF_THE_COUNCIL)
        );
    }

    private CustodiSquire(final CustodiSquire card) {
        super(card);
    }

    @Override
    public CustodiSquire copy() {
        return new CustodiSquire(this);
    }
}

class CustodiSquireVoteEffect extends OneShotEffect {

    CustodiSquireVoteEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for an artifact, creature, or enchantment card in your graveyard. " +
                "Return each card with the most votes or tied for most votes to your hand";
    }

    private CustodiSquireVoteEffect(final CustodiSquireVoteEffect effect) {
        super(effect);
    }

    @Override
    public CustodiSquireVoteEffect copy() {
        return new CustodiSquireVoteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        CustodiSquireVote vote = new CustodiSquireVote();
        vote.doVotes(source, game);

        return player.moveCards(vote.getMostVoted(), Zone.HAND, source, game);
    }
}

class CustodiSquireVote extends VoteHandler<Card> {

    private static final FilterCard filter = new FilterCard("artifact, creature, or enchantment card");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate(),
                CardType.ENCHANTMENT.getPredicate()
        ));
    }

    @Override
    protected Set<Card> getPossibleVotes(Ability source, Game game) {
        // too much permanentns on battlefield, so no need to show full list here
        return new LinkedHashSet<>();
    }

    @Override
    public Card playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || controller.getGraveyard().count(
                filter, source.getControllerId(), source, game
        ) < 1) {
            return null;
        }
        TargetCardInGraveyard target = new TargetCardInGraveyard(filter);
        target.withChooseHint(voteInfo + " (from graveyard to hand)");
        target.setNotTarget(true);
        decidingPlayer.choose(Outcome.ReturnToHand, controller.getGraveyard(), target, source, game);
        return controller.getGraveyard().get(target.getFirstTarget(), game);
    }

    @Override
    protected String voteName(Card vote) {
        return vote.getIdName();
    }
}
