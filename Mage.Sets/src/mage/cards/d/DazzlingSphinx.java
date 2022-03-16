package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DazzlingSphinx extends CardImpl {

    public DazzlingSphinx(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Dazzling Sphinx deals combat damage to a player, that player exiles cards from the top of their library until they exile an instant or sorcery card. You may cast that card without paying its mana cost. Then that player puts the exiled cards that weren't cast this way on the bottom of their library in a random order.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new DazzlingSphinxEffect(), false, true
        ));
    }

    private DazzlingSphinx(final DazzlingSphinx card) {
        super(card);
    }

    @Override
    public DazzlingSphinx copy() {
        return new DazzlingSphinx(this);
    }
}

class DazzlingSphinxEffect extends OneShotEffect {

    DazzlingSphinxEffect() {
        super(Outcome.Benefit);
        staticText = "that player exiles cards from the top of their library until they exile an " +
                "instant or sorcery card. You may cast that card without paying its mana cost. Then that player " +
                "puts the exiled cards that weren't cast this way on the bottom of their library in a random order";
    }

    private DazzlingSphinxEffect(final DazzlingSphinxEffect effect) {
        super(effect);
    }

    @Override
    public DazzlingSphinxEffect copy() {
        return new DazzlingSphinxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (Card card : opponent.getLibrary().getCards(game)) {
            cards.add(card);
            opponent.moveCards(card, Zone.EXILED, source, game);
            if (card.isInstantOrSorcery(game)) {
                CardUtil.castSpellWithAttributesForFree(controller, source, game, card);
                break;
            }
        }
        cards.retainZone(Zone.EXILED, game);
        opponent.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
