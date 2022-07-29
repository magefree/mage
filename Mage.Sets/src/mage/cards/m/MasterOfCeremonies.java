package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.choices.VoteHandler;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.CitizenGreenWhiteToken;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

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

        HashSet<UUID> moneyChoosers   = new HashSet<>();
        HashSet<UUID> friendChoosers  = new HashSet<>();
        HashSet<UUID> secretsChoosers = new HashSet<>();

        // Get opponents choices
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }

            Choice choice = new ChoiceImpl(true);
            choice.getChoices().add("Money");
            choice.getChoices().add("Friends");
            choice.getChoices().add("Secrets");

            if (opponent.choose(Outcome.Neutral, choice, game)) {
                game.informPlayers(opponent.getLogName() + " chooses " + choice.getChoice());
                switch (choice.getChoice()) {
                    case "Money":
                        moneyChoosers.add(opponentId);
                        break;
                    case "Friends":
                        friendChoosers.add(opponentId);
                        break;
                    case "Secrets":
                        secretsChoosers.add(opponentId);
                        break;
                }
            }
        }

        // Money - You and that player each create a Treasure token.
        for (UUID opponentId : moneyChoosers) {
            Token treasureController = new TreasureToken();
            treasureController.putOntoBattlefield(1, game, source, controller.getId());

            Token treasureOpponent = new TreasureToken();
            treasureOpponent.putOntoBattlefield(1, game, source, opponentId);
        }
        game.applyEffects();

        // Friends - You and that player each create a 1/1 green and white Citizen creature token.
        for (UUID opponentId : friendChoosers) {
            Token citizenOwner = new CitizenGreenWhiteToken();
            citizenOwner.putOntoBattlefield(1, game, source, controller.getId());

            Token citizenOpponent = new CitizenGreenWhiteToken();
            citizenOpponent.putOntoBattlefield(1, game, source, opponentId);
        }
        game.applyEffects();

        // Secrets - You and that player each draw a card.
        for (UUID opponentId : secretsChoosers) {
            Effect drawEffectController = new DrawCardTargetEffect(1);
            drawEffectController.setTargetPointer(new FixedTarget(controller.getId()));
            drawEffectController.apply(game, source);

            Effect drawEffectOpponent = new DrawCardTargetEffect(1);
            drawEffectOpponent.setTargetPointer(new FixedTarget(opponentId));
            drawEffectOpponent.apply(game, source);
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