package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPlayer;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GluntchTheBestower extends CardImpl {

    public GluntchTheBestower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JELLYFISH);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, choose a player. They put two +1/+1 counters on a creature they control. Choose a second player to draw a card. Then choose a third player to create two Treasure tokens.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
            new GluntchTheBestowerEffect(),
            TargetController.YOU,
            false
        ));
    }

    private GluntchTheBestower(final GluntchTheBestower card) {
        super(card);
    }

    @Override
    public GluntchTheBestower copy() {
        return new GluntchTheBestower(this);
    }
}

class GluntchTheBestowerEffect extends OneShotEffect {

    GluntchTheBestowerEffect() {
        super(Outcome.Neutral);
        this.setText("choose a player. They put two +1/+1 counters on a creature they control. " +
            "Choose a second player to draw a card. Then choose a third player to create two Treasure tokens.");
    }

    private GluntchTheBestowerEffect(final GluntchTheBestowerEffect effect) {
        super(effect);
    }

    @Override
    public GluntchTheBestowerEffect copy() {
        return new GluntchTheBestowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        //
        // First effect:
        // Choose a player. They put two +1/+1 counters on a creature they control.
        //

        TargetPlayer playerChoice = new TargetPlayer();
        playerChoice.setNotTarget(true);
        playerChoice.setTargetName("a player that will put two +1/+1 counters on a creature they control");
        controller.choose(Outcome.BoostCreature, playerChoice, source, game);

        UUID firstChosenPlayerId = playerChoice.getFirstTarget();
        Player firstChosenPlayer = game.getPlayer(firstChosenPlayerId);
        if (firstChosenPlayerId == null || firstChosenPlayer == null) {
            return false;
        }

        game.informPlayers(firstChosenPlayer.getLogName() + " has been chosen for the first effect.");
        TargetPermanent targetChosenCreature = new TargetControlledCreaturePermanent();
        targetChosenCreature.setNotTarget(true);
        playerChoice.setTargetName("a creature you control to add two +1/+1 counters on it");
        firstChosenPlayer.choose(Outcome.BoostCreature, targetChosenCreature, source, game);

        Permanent chosenCreature = game.getPermanent(targetChosenCreature.getFirstTarget());
        if (chosenCreature != null) {
            chosenCreature.addCounters(CounterType.P1P1.createInstance(2), firstChosenPlayerId, source, game);
        }

        //
        // Second effect:
        // Choose a second player to draw a card.
        //

        FilterPlayer filterNotFirst = new FilterPlayer();
        filterNotFirst.add(Predicates.not(new PlayerIdPredicate(firstChosenPlayerId)));
        playerChoice = new TargetPlayer(filterNotFirst);
        playerChoice.setNotTarget(true);
        playerChoice.setTargetName("a player that will draw a card");
        controller.choose(Outcome.DrawCard, playerChoice, source, game);

        UUID secondChosenPlayerId = playerChoice.getFirstTarget();
        Player secondChosenPlayer = game.getPlayer(secondChosenPlayerId);
        if (secondChosenPlayerId == null || secondChosenPlayer == null) {
            return true;
        }

        game.informPlayers(secondChosenPlayer.getLogName() + " has been chosen for the second effect.");
        secondChosenPlayer.drawCards(1, source, game);

        //
        // Third effect:
        // Choose a third player to create two Treasure tokens.
        //

        FilterPlayer filterNotFirstNorSecond = new FilterPlayer();
        filterNotFirstNorSecond.add(Predicates.not(new PlayerIdPredicate(firstChosenPlayerId)));
        filterNotFirstNorSecond.add(Predicates.not(new PlayerIdPredicate(secondChosenPlayerId)));
        playerChoice = new TargetPlayer(filterNotFirstNorSecond);
        playerChoice.setNotTarget(true);
        playerChoice.setTargetName("a player that will create two Treasure tokens");
        controller.choose(Outcome.DrawCard, playerChoice, source, game);

        UUID thirdChosenPlayerId = playerChoice.getFirstTarget();
        Player thirdChosenPlayer = game.getPlayer(thirdChosenPlayerId);
        if (thirdChosenPlayerId == null || thirdChosenPlayer == null) {
            return true;
        }

        game.informPlayers(thirdChosenPlayer.getLogName() + " has been chosen for the third effect.");
        new TreasureToken().putOntoBattlefield(2, game, source, thirdChosenPlayerId);

        return true;
    }
}