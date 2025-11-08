package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SolsticeRevelations extends CardImpl {

    public SolsticeRevelations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        this.subtype.add(SubType.LESSON);

        // Exile cards from the top of your library until you exile a nonland card. You may cast that card without paying its mana cost if the spell's mana value is less than the number of Mountains you control. If you don't cast that card this way, put it into your hand.
        this.getSpellAbility().addEffect(new SolsticeRevelationsEffect());

        // Flashback {6}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{6}{R}")));
    }

    private SolsticeRevelations(final SolsticeRevelations card) {
        super(card);
    }

    @Override
    public SolsticeRevelations copy() {
        return new SolsticeRevelations(this);
    }
}

enum SolsticeRevelationsPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;
    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.MOUNTAIN);

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return input.getObject().getManaValue()
                < game.getBattlefield().count(filter, input.getPlayerId(), input.getSource(), game);
    }
}

class SolsticeRevelationsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(SolsticeRevelationsPredicate.instance);
    }

    SolsticeRevelationsEffect() {
        super(Outcome.Benefit);
        staticText = "exile cards from the top of your library until you exile a nonland card. " +
                "You may cast that card without paying its mana cost if the spell's mana value is less than " +
                "the number of Mountains you control. If you don't cast that card this way, put it into your hand";
    }

    private SolsticeRevelationsEffect(final SolsticeRevelationsEffect effect) {
        super(effect);
    }

    @Override
    public SolsticeRevelationsEffect copy() {
        return new SolsticeRevelationsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = getCard(player, game, source);
        if (card == null) {
            return false;
        }
        if (!CardUtil.castSpellWithAttributesForFree(player, source, game, card, filter)) {
            player.moveCards(card, Zone.HAND, source, game);
        }
        return true;
    }

    private static Card getCard(Player player, Game game, Ability source) {
        for (Card card : player.getLibrary().getCards(game)) {
            player.moveCards(card, Zone.EXILED, source, game);
            game.processAction();
            if (!card.isLand(game)) {
                return card;
            }
        }
        return null;
    }

}
