package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GrimaSarumansFootman extends CardImpl {

    public GrimaSarumansFootman(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Grima, Saruman's Footman can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // Whenever Grima deals combat damage to a player, that player exiles cards from the top of their library until they exile an instant or sorcery card. You may cast that card without paying its mana cost. Then that player puts the exiled cards that weren't cast this way on the bottom of their library in a random order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new GrimaSarumansFootmanEffect(),
                false, true
        ));
    }

    private GrimaSarumansFootman(final GrimaSarumansFootman card) {
        super(card);
    }

    @Override
    public GrimaSarumansFootman copy() {
        return new GrimaSarumansFootman(this);
    }
}

class GrimaSarumansFootmanEffect extends OneShotEffect {

    GrimaSarumansFootmanEffect() {
        super(Outcome.PlayForFree);
        staticText = "that player exiles cards from the top of their library until they exile an instant "
                + "or sorcery card. You may cast that card without paying its mana cost. Then that player "
                + "puts the exiled cards that weren't cast this way on the bottom of their library in a random order.";
    }

    private GrimaSarumansFootmanEffect(final GrimaSarumansFootmanEffect effect) {
        super(effect);
    }

    @Override
    public GrimaSarumansFootmanEffect copy() {
        return new GrimaSarumansFootmanEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (player == null || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            player.moveCards(card, Zone.EXILED, source, game);
            if (card.isInstantOrSorcery(game)) {
                CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
                break;
            }
        }
        cards.retainZone(Zone.EXILED, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}