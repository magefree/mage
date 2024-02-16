package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.choices.VoteHandler;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Styxo, TheElk801
 */
public final class CouncilGuardian extends CardImpl {

    public CouncilGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Will of the council - When Council Guardian enters the battlefield, starting with you, each player votes for blue, black, red, or green. Council Guardian gains protection from each color with the most votes or tied for most votes.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new CouncilsGuardianEffect(), false)
                .setAbilityWord(AbilityWord.WILL_OF_THE_COUNCIL)
        );
    }

    private CouncilGuardian(final CouncilGuardian card) {
        super(card);
    }

    @Override
    public CouncilGuardian copy() {
        return new CouncilGuardian(this);
    }
}

class CouncilsGuardianEffect extends OneShotEffect {

    CouncilsGuardianEffect() {
        super(Outcome.Benefit);
        this.staticText = "starting with you, each player votes for blue, black, red, or green. " +
                "{this} gains protection from each color with the most votes or tied for most votes";
    }

    private CouncilsGuardianEffect(final CouncilsGuardianEffect effect) {
        super(effect);
    }

    @Override
    public CouncilsGuardianEffect copy() {
        return new CouncilsGuardianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CouncilGuardianVote vote = new CouncilGuardianVote();
        vote.doVotes(source, game);

        for (String color : vote.getMostVoted()) {
            if (color == null) {
                continue;
            }
            game.addEffect(new GainAbilitySourceEffect(
                    ProtectionAbility.from(ChoiceColor.getColorFromString(color)), Duration.Custom
            ), source);
        }
        return true;
    }
}

class CouncilGuardianVote extends VoteHandler<String> {

    @Override
    protected Set<String> getPossibleVotes(Ability source, Game game) {
        return new LinkedHashSet<>(Arrays.asList("Blue", "Black", "Red", "Green"));
    }

    @Override
    public String playerChoose(String voteInfo, Player player, Player decidingPlayer, Ability source, Game game) {
        ChoiceColor choice = new ChoiceColor();
        choice.getChoices().remove("White");
        choice.setSubMessage(voteInfo);
        decidingPlayer.choose(Outcome.AIDontUseIt, choice, game); // TODO: add AI hint logic in the choice method, see Tyrant's Choice as example
        return choice.getChoice();
    }

    @Override
    protected String voteName(String vote) {
        return vote;
    }
}
