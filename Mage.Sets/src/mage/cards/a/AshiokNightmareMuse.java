package mage.cards.a;

import java.util.UUID;
import mage.ApprovingObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AshiokNightmareMuseToken;
import mage.players.Player;
import mage.target.common.TargetCardInExile;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetNonlandPermanent;

/**
 * @author TheElk801
 */
public final class AshiokNightmareMuse extends CardImpl {

    public AshiokNightmareMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ASHIOK);
        this.setStartingLoyalty(5);

        // +1: Create a 2/3 blue and black Nightmare creature token with "Whenever this creature attacks or blocks, each opponent exiles the top two cards of their library."
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new AshiokNightmareMuseToken()), 1));

        // −3: Return target nonland permanent to its owner's hand, then that player exiles a card from their hand.
        Ability ability = new LoyaltyAbility(new AshiokNightmareMuseBounceEffect(), -3);
        ability.addTarget(new TargetNonlandPermanent());
        this.addAbility(ability);

        // −7: You may cast up to three spells from among face-up cards your opponents own from exile without paying their mana costs.
        this.addAbility(new LoyaltyAbility(new AshiokNightmareMuseCastEffect(), -7));
    }

    private AshiokNightmareMuse(final AshiokNightmareMuse card) {
        super(card);
    }

    @Override
    public AshiokNightmareMuse copy() {
        return new AshiokNightmareMuse(this);
    }
}

class AshiokNightmareMuseBounceEffect extends OneShotEffect {

    AshiokNightmareMuseBounceEffect() {
        super(Outcome.Discard);
        staticText = "return target nonland permanent to its owner's hand, "
                + "then that player exiles a card from their hand";
    }

    private AshiokNightmareMuseBounceEffect(final AshiokNightmareMuseBounceEffect effect) {
        super(effect);
    }

    @Override
    public AshiokNightmareMuseBounceEffect copy() {
        return new AshiokNightmareMuseBounceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(game.getOwnerId(source.getFirstTarget()));
        if (permanent == null || player == null) {
            return false;
        }
        player.moveCards(permanent, Zone.HAND, source, game);
        if (player.getHand().isEmpty()) {
            return true;
        }
        TargetCardInHand target = new TargetCardInHand();
        if (!player.choose(outcome, player.getHand(), target, game)) {
            return false;
        }
        return player.moveCards(game.getCard(target.getFirstTarget()), Zone.EXILED, source, game);
    }
}

class AshiokNightmareMuseCastEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("face-up cards your opponents own from exile");

    static {
        filter.add(TargetController.OPPONENT.getOwnerPredicate());
    }

    AshiokNightmareMuseCastEffect() {
        super(Outcome.Discard);
        staticText = "You may cast up to three spells from among face-up cards your opponents own from exile without paying their mana costs.";
    }

    private AshiokNightmareMuseCastEffect(final AshiokNightmareMuseCastEffect effect) {
        super(effect);
    }

    @Override
    public AshiokNightmareMuseCastEffect copy() {
        return new AshiokNightmareMuseCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetCardInExile target = new TargetCardInExile(0, 3, filter, null);
        target.setNotTarget(true);
        if (!controller.chooseTarget(outcome, target, source, game)) { // method is fine, controller is still choosing the card
            return false;
        }
        for (UUID targetId : target.getTargets()) {
            if (targetId != null) {
                Card chosenCard = game.getCard(targetId);
                if (chosenCard != null
                        && game.getState().getZone(chosenCard.getId()) == Zone.EXILED // must be exiled
                        && game.getOpponents(controller.getId()).contains(chosenCard.getOwnerId()) // must be owned by an opponent
                        && controller.chooseUse(outcome, "Cast " + chosenCard.getName() + " without paying its mana cost?", source, game)) {
                    game.getState().setValue("PlayFromNotOwnHandZone" + chosenCard.getId(), Boolean.TRUE);
                    controller.cast(controller.chooseAbilityForCast(chosenCard, game, true),
                            game, true, new ApprovingObject(source, game));
                    game.getState().setValue("PlayFromNotOwnHandZone" + chosenCard.getId(), null);
                }
            }
        }
        return true;
    }
}
