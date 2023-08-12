
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author Loki
 */
public final class VendilionClique extends CardImpl {

    public VendilionClique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Vendilion Clique enters the battlefield, look at target player's hand. You may choose a nonland card from it. If you do, that player reveals the chosen card, puts it on the bottom of their library, then draws a card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new VendilionCliqueEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private VendilionClique(final VendilionClique card) {
        super(card);
    }

    @Override
    public VendilionClique copy() {
        return new VendilionClique(this);
    }
}

class VendilionCliqueEffect extends OneShotEffect {

    VendilionCliqueEffect() {
        super(Outcome.Discard);
        staticText = "look at target player's hand. You may choose a nonland card from it. If you do, that player reveals the chosen card, puts it on the bottom of their library, then draws a card";
    }

    VendilionCliqueEffect(final VendilionCliqueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (player != null && controller != null && sourceObject != null) {
            TargetCard targetCard = new TargetCard(Zone.ALL, new FilterNonlandCard());
            targetCard.setRequired(false);
            if (controller.choose(Outcome.Discard, player.getHand(), targetCard, source, game)) {
                Card card = game.getCard(targetCard.getFirstTarget());
                if (card != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(card);
                    player.revealCards(sourceObject.getIdName(), cards, game);
                    player.putCardsOnBottomOfLibrary(cards, game, source, true);
                    player.drawCards(1, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public VendilionCliqueEffect copy() {
        return new VendilionCliqueEffect(this);
    }
}
