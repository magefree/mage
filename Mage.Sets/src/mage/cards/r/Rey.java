package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrLeavesSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author NinthWorld
 */
public final class Rey extends CardImpl {

    public Rey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Whenever Rey enters or leaves the battlefield, reveal the top card of target player's library. You gain life equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldOrLeavesSourceTriggeredAbility(new ReyEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Rey(final Rey card) {
        super(card);
    }

    @Override
    public Rey copy() {
        return new Rey(this);
    }
}

class ReyEffect extends OneShotEffect {

    public ReyEffect() {
        super(Outcome.Detriment);
        staticText = "reveal the top card of target player's library. You gain life equal to that card's mana value";
    }

    public ReyEffect(final ReyEffect effect) {
        super(effect);
    }

    @Override
    public ReyEffect copy() {
        return new ReyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if(targetPlayer != null && controller != null) {
            if(targetPlayer.getLibrary().hasCards()) {
                // reveal the top card of target player's library.
                Card topCard = targetPlayer.getLibrary().getFromTop(game);
                CardsImpl reveal = new CardsImpl();
                reveal.add(topCard);
                targetPlayer.revealCards(source, reveal, game);

                // You gain life equal to that card's converted mana cost.
                if (topCard != null) {
                    controller.gainLife(topCard.getManaValue(), game, source);
                }
            }

            return true;
        }
        return false;
    }
}