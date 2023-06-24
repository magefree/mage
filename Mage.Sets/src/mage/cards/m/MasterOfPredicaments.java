package mage.cards.m;

import java.util.UUID;
import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class MasterOfPredicaments extends CardImpl {

    public MasterOfPredicaments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Master of Predicaments deals combat damage to a player, 
        // choose a card in your hand.  That player guesses whether the card's 
        // converted mana cost is greater than 4.  If the player guessed wrong, 
        // you may cast the card without paying its mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new MasterOfPredicamentsEffect(), false, true));
    }

    private MasterOfPredicaments(final MasterOfPredicaments card) {
        super(card);
    }

    @Override
    public MasterOfPredicaments copy() {
        return new MasterOfPredicaments(this);
    }
}

class MasterOfPredicamentsEffect extends OneShotEffect {

    public MasterOfPredicamentsEffect() {
        super(Outcome.PlayForFree);
        this.staticText = "choose a card in your hand. That player guesses whether "
                + "the card's mana value is greater than 4. If the player "
                + "guessed wrong, you may cast the card without paying its mana cost";
    }

    public MasterOfPredicamentsEffect(final MasterOfPredicamentsEffect effect) {
        super(effect);
    }

    @Override
    public MasterOfPredicamentsEffect copy() {
        return new MasterOfPredicamentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (!controller.getHand().isEmpty()) {
                Card cardFromHand = null;
                if (controller.getHand().size() > 1) {
                    TargetCard target = new TargetCardInHand(new FilterCard());
                    if (controller.choose(Outcome.PlayForFree, controller.getHand(), target, source, game)) {
                        cardFromHand = game.getCard(target.getFirstTarget());
                    }
                } else {
                    cardFromHand = controller.getHand().getRandom(game);
                }
                if (cardFromHand == null) {
                    return false;
                }
                Player attackedPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
                if (attackedPlayer == null) {
                    return false;
                }
                boolean guessWrong;
                if (attackedPlayer.chooseUse(Outcome.Detriment, "Is the chosen card's "
                        + "mana value greater than 4?", source, game)) {
                    game.informPlayers(attackedPlayer.getLogName() + " guessed that the chosen "
                            + "card's mana value is greater than 4");
                    guessWrong = cardFromHand.getManaValue() <= 4;
                } else {
                    game.informPlayers(attackedPlayer.getLogName() + " guessed that the chosen "
                            + "card's mana value is not greater than 4");
                    guessWrong = cardFromHand.getManaValue() > 4;
                }
                game.informPlayers(attackedPlayer.getLogName() + " guessed " + (guessWrong ? "wrong" : "right"));
                if (guessWrong) {
                    if (cardFromHand.isLand(game)) {
                        // If the revealed card is a land, you can't cast it. So nothing happens
                    } else {
                        if (controller.chooseUse(outcome, "Cast " + cardFromHand.getName()
                                + " without paying its mana cost?", source, game)) {
                            game.getState().setValue("PlayFromNotOwnHandZone" + cardFromHand.getId(), Boolean.TRUE);
                            controller.cast(controller.chooseAbilityForCast(cardFromHand, game, true),
                                    game, true, new ApprovingObject(source, game));
                            game.getState().setValue("PlayFromNotOwnHandZone" + cardFromHand.getId(), null);
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
