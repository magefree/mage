package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.CanBeYourCommanderAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceLeftOrRight;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Colin Redman
 */
public class AminatouTheFateshifter extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target permanent you own");

    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public AminatouTheFateshifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{W}{U}{B}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.AMINATOU);

        this.setStartingLoyalty(3);

        // +1: Draw a card, then put a card from your hand on top of your library.
        Ability ability = new LoyaltyAbility(new AminatouPlusEffect(), +1);
        this.addAbility(ability);

        // −1: Exile another target permanent you own, then return it to the battlefield under your control.
        ability = new LoyaltyAbility(new ExileTargetForSourceEffect(), -1);
        ability.addEffect(new ReturnToBattlefieldUnderYourControlTargetEffect(false));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // -6: Choose left or right. Each player gains control of all nonland permanents other than Aminatou, the
        // Fateshifter controlled by the next player in the chosen direction.
        ability = new LoyaltyAbility(new AminatouUltimateEffect(), -6);
        this.addAbility(ability);

        // Aminatou, the Fateshifter can be your commander.
        this.addAbility(CanBeYourCommanderAbility.getInstance());
    }

    private AminatouTheFateshifter(final AminatouTheFateshifter card) {
        super(card);
    }

    @Override
    public AminatouTheFateshifter copy() {
        return new AminatouTheFateshifter(this);
    }
}

class AminatouPlusEffect extends OneShotEffect {

    public AminatouPlusEffect() {
        super(Outcome.DrawCard);
        staticText = "draw a card, then put a card from your hand on top of your library";
    }

    public AminatouPlusEffect(final AminatouPlusEffect effect) {
        super(effect);
    }

    @Override
    public AminatouPlusEffect copy() {
        return new AminatouPlusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
            putOnLibrary(player, source, game);
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)) {
            player.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                return player.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
            }
        }
        return false;
    }
}

class AminatouUltimateEffect extends OneShotEffect {

    public AminatouUltimateEffect() {
        super(Outcome.Benefit);
        staticText = "Choose left or right. Each player gains control of all nonland permanents other than Aminatou,"
                + " the Fateshifter controlled by the next player in the chosen direction.";
    }

    public AminatouUltimateEffect(final AminatouUltimateEffect effect) {
        super(effect);
    }

    @Override
    public AminatouUltimateEffect copy() {
        return new AminatouUltimateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Choice choice = new ChoiceLeftOrRight();
            if (!controller.choose(Outcome.Neutral, choice, game)) {
                return false;
            }
            boolean left = choice.getChoice().equals("Left");
            PlayerList playerList = game.getState().getPlayerList().copy();
            // set playerlist to controller
            while (!playerList.get().equals(source.getControllerId())) {
                playerList.getNext();
            }
            UUID currentPlayer = playerList.get();
            UUID nextPlayer;
            UUID firstNextPlayer = null;
            while (!getNextPlayerInDirection(left, playerList, game).equals(firstNextPlayer)) {
                nextPlayer = playerList.get();
                if (nextPlayer == null) {
                    return false;
                }
                // skip players out of range
                if (!game.getState().getPlayersInRange(controller.getId(), game).contains(nextPlayer)) {
                    continue;
                }
                // save first next player to check for iteration stop
                if (firstNextPlayer == null) {
                    firstNextPlayer = nextPlayer;
                }
                FilterNonlandPermanent nextPlayerNonlandPermanentsFilter = new FilterNonlandPermanent();
                nextPlayerNonlandPermanentsFilter.add(new ControllerIdPredicate(nextPlayer));
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(nextPlayerNonlandPermanentsFilter, game)) {
                    if (permanent.getId().equals(source.getSourceId())) {
                        continue;
                    }
                    ContinuousEffect effect = new GainControlTargetEffect(Duration.EndOfGame, currentPlayer);
                    effect.setTargetPointer(new FixedTarget(permanent, game));
                    game.addEffect(effect, source);
                }
                currentPlayer = nextPlayer;
            }
            return true;
        }
        return false;
    }

    private UUID getNextPlayerInDirection(boolean left, PlayerList playerList, Game game) {
        UUID nextPlayerId;
        if (left) {
            nextPlayerId = playerList.getNext();
        } else {
            nextPlayerId = playerList.getPrevious();
        }
        return nextPlayerId;
    }
}
