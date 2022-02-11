package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class StingingStudy extends CardImpl {

    public StingingStudy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}");

        // You draw X cards and you lose X life, where X is the mana value of a commander you own on the battlefield or in the command zone.
        this.getSpellAbility().addEffect(new StingingStudyEffect());
    }

    private StingingStudy(final StingingStudy card) {
        super(card);
    }

    @Override
    public StingingStudy copy() {
        return new StingingStudy(this);
    }
}

class StingingStudyEffect extends OneShotEffect {

    StingingStudyEffect() {
        super(Outcome.Benefit);
        staticText = "you draw X cards and you lose X life, where X is " +
                "the mana value of a commander you own on the battlefield or in the command zone";
    }

    private StingingStudyEffect(final StingingStudyEffect effect) {
        super(effect);
    }

    @Override
    public StingingStudyEffect copy() {
        return new StingingStudyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Set<Integer> manaValues = new HashSet<>();
        for (Card commander : game.getCommanderCardsFromAnyZones(player, CommanderCardType.ANY, Zone.BATTLEFIELD, Zone.COMMAND)) {
            manaValues.add(commander.getManaValue());
        }
        int chosenValue;
        if (manaValues.size() > 1) {
            Choice choice = new ChoiceImpl(true);
            choice.setChoices(manaValues.stream().map(x -> "" + x).collect(Collectors.toSet()));
            player.choose(outcome, choice, game);
            chosenValue = Integer.parseInt(choice.getChoice());
        } else {
            chosenValue = manaValues.stream().findFirst().orElse(0);
        }
        if (chosenValue == 0) {
            return false;
        }
        player.drawCards(chosenValue, source, game);
        player.loseLife(chosenValue, game, source, false);
        return true;
    }
}
