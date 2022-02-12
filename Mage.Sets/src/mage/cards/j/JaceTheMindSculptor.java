
package mage.cards.j;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.BrainstormEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class JaceTheMindSculptor extends CardImpl {

    public JaceTheMindSculptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);

        this.setStartingLoyalty(3);

        // +2: Look at the top card of target player's library. You may put that card on the bottom of that player's library.
        LoyaltyAbility ability1 = new LoyaltyAbility(new JaceTheMindSculptorEffect1(), 2);
        ability1.addTarget(new TargetPlayer());
        this.addAbility(ability1);

        // 0: Draw three cards, then put two cards from your hand on top of your library in any order.
        LoyaltyAbility ability2 = new LoyaltyAbility(new BrainstormEffect(), 0);
        this.addAbility(ability2);

        // −1: Return target creature to its owner's hand.
        LoyaltyAbility ability3 = new LoyaltyAbility(new ReturnToHandTargetEffect(), -1);
        ability3.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability3);

        // −12: Exile all cards from target player's library, then that player shuffles their hand into their library.
        LoyaltyAbility ability4 = new LoyaltyAbility(new JaceTheMindSculptorEffect2(), -12);
        ability4.addTarget(new TargetPlayer());
        this.addAbility(ability4);

    }

    private JaceTheMindSculptor(final JaceTheMindSculptor card) {
        super(card);
    }

    @Override
    public JaceTheMindSculptor copy() {
        return new JaceTheMindSculptor(this);
    }

}

class JaceTheMindSculptorEffect1 extends OneShotEffect {

    public JaceTheMindSculptorEffect1() {
        super(Outcome.Detriment);
        staticText = "Look at the top card of target player's library. You may put that card on the bottom of that player's library";
    }

    public JaceTheMindSculptorEffect1(final JaceTheMindSculptorEffect1 effect) {
        super(effect);
    }

    @Override
    public JaceTheMindSculptorEffect1 copy() {
        return new JaceTheMindSculptorEffect1(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player player = game.getPlayer(source.getFirstTarget());
        if (controller != null && player != null) {
            Card card = player.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.lookAtCards("Jace, the Mind Sculptor", cards, game);
                if (controller.chooseUse(outcome, "Put that card on the bottom of its owner's library?", source, game)) {
                    controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, false, false);
                } else {
                    game.informPlayers(controller.getLogName() + " puts the card back on top of the library.");
                }
                return true;
            }
        }
        return false;
    }

}

class JaceTheMindSculptorEffect2 extends OneShotEffect {

    public JaceTheMindSculptorEffect2() {
        super(Outcome.DrawCard);
        staticText = "Exile all cards from target player's library, then that player shuffles their hand into their library";
    }

    public JaceTheMindSculptorEffect2(final JaceTheMindSculptorEffect2 effect) {
        super(effect);
    }

    @Override
    public JaceTheMindSculptorEffect2 copy() {
        return new JaceTheMindSculptorEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (controller != null && targetPlayer != null) {
            controller.moveCards(targetPlayer.getLibrary().getTopCards(game, targetPlayer.getLibrary().size()), Zone.EXILED, source, game);
            targetPlayer.moveCards(targetPlayer.getHand(), Zone.LIBRARY, source, game);
            targetPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

}
