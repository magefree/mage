package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceImpl;
import mage.choices.VoteHandler;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.*;

/**
 * @author Alex-Vasile
 */
public class MasterOfCeremonies extends CardImpl {
    public MasterOfCeremonies(UUID ownderId, CardSetInfo setInfo) {
        super(ownderId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.RHINO);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, each opponent chooses money, friends, or secrets.
        // For each player who chose money, you and that player each create a Treasure token.
        // For each player who chose friends, you and that player each create a 1/1 green and white Citizen creature token.
        // For each player who chose secrets, you and that player each draw a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new MasterOfCeremoniesChoiceEffect(),
                TargetController.YOU,
                false)
        );
    }

    private MasterOfCeremonies(final MasterOfCeremonies card) {
        super(card);
    }

    @Override
    public MasterOfCeremonies copy() {
        return new MasterOfCeremonies(this);
    }
}

class MasterOfCeremoniesChoiceEffect extends OneShotEffect {

    MasterOfCeremoniesChoiceEffect() {
        super(Outcome.Benefit);
        this.staticText = "each opponent chooses money, friends, or secrets. " +
                          "For each player who chose money, you and that player each create a Treasure token. " +
                          "For each player who chose friends, you and that player each create a 1/1 green and white Citizen creature token.  " +
                          "For each player who chose secrets, you and that player each draw a card.";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        // Ask the players to vote
        MasterOfCeremoniesVote vote = new MasterOfCeremoniesVote();
        // TODO: Add AI hint
        vote.doVotes(source, game);

        // Go through each vote and apply effects
        for (UUID opponentId : game.getOpponents(controller.getId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            // Iterate through each vote that opponent took, may have multiple votes from certain events
            // E.g. Ballot Broker
            List<String> opponentsVotes = vote.getVotes(opponentId);
            for (String opponentsVote : opponentsVotes) {
                switch (opponentsVote) {
                    case "Money": // You and that player each create a Treasure token.

                        break;
                    case "Friends": // You and that player each create a 1/1 green and white Citizen creature token.

                        break;
                    case "": // You and that player each draw a card.

                        break;
                    default:
                        // TODO: Do I need this here?
                }

            }
        }

        return true;
    }

    private MasterOfCeremoniesChoiceEffect(final MasterOfCeremoniesChoiceEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfCeremoniesChoiceEffect copy() {
        return new MasterOfCeremoniesChoiceEffect(this);
    }
}
// Expropriate
class MasterOfCeremoniesVote extends VoteHandler<String> {

    @Override
    protected Set<String> getPossibleVotes(Ability source, Game game) {
        return new LinkedHashSet<>(Arrays.asList("Money", "Friends", "Secrets"));
    }

    @Override
    protected String playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        // TODO: Limit the vote to only opponents

        // Create the chooser
        ChoiceImpl chooseOutcome = new ChoiceImpl();
        chooseOutcome.setMessage("Choose money, friends, or secrets");
        chooseOutcome.setChoices(new LinkedHashSet<>(Arrays.asList("Money", "Friends", "Secrets")));

        decidingPlayer.choose(Outcome.AIDontUseIt, chooseOutcome, game);

        return chooseOutcome.getChoice();
    }

    @Override
    protected String voteName(String vote) {
        return vote;
    }
}