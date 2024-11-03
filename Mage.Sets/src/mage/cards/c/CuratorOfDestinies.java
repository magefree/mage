package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class CuratorOfDestinies extends CardImpl {

    public CuratorOfDestinies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // This spell can't be countered.
        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, look at the top five cards of your library and separate them into a face-down pile and a face-up pile. An opponent chooses one of those piles. Put that pile into your hand and the other into your graveyard.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CuratorOfDestiniesEffect()));
    }

    private CuratorOfDestinies(final CuratorOfDestinies card) {
        super(card);
    }

    @Override
    public CuratorOfDestinies copy() {
        return new CuratorOfDestinies(this);
    }
}

class CuratorOfDestiniesEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("cards for the face-down pile");

    CuratorOfDestiniesEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top five cards of your library and separate them into a face-down pile "
                + "and a face-up pile. An opponent chooses one of those piles. Put that pile into your hand "
                + "and the other into your graveyard";
    }

    private CuratorOfDestiniesEffect(final CuratorOfDestiniesEffect effect) {
        super(effect);
    }

    @Override
    public CuratorOfDestiniesEffect copy() {
        return new CuratorOfDestiniesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, filter);
        controller.choose(outcome, cards, target, source, game);
        Cards faceDownPile = new CardsImpl(target.getTargets());
        cards.removeAll(target.getTargets());
        controller.revealCards(sourceObject.getIdName() + " - face-up pile", cards, game);
        game.informPlayers(controller.getLogName() + " puts " + faceDownPile.size() + " card" + (faceDownPile.size() == 1 ? "" : "s") + " into the face-down pile");

        Player opponent;
        Set<UUID> opponents = game.getOpponents(controller.getId());
        if (opponents.size() == 1) {
            opponent = game.getPlayer(opponents.iterator().next());
        } else {
            Target targetOpponent = new TargetOpponent(true);
            controller.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
            opponent = game.getPlayer(targetOpponent.getFirstTarget());
        }
        if (opponent == null) {
            return false;
        }
        boolean choice = opponent.chooseUse(outcome, "Choose a pile to put into " + controller.getLogName() + "'s hand.", null, "Face-down", "Face-up", source, game);
        controller.moveCards(faceDownPile, choice ? Zone.HAND : Zone.GRAVEYARD, source, game);
        controller.moveCards(cards, choice ? Zone.GRAVEYARD : Zone.HAND, source, game);
        return true;
    }
}