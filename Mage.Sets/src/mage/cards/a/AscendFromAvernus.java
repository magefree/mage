package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AscendFromAvernus extends CardImpl {

    public AscendFromAvernus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}{W}");

        // Return all creature and planeswalker cards with mana value X or less from your graveyard to the battlefield. Exile Ascend from Avernus.
        this.getSpellAbility().addEffect(new AscendFromAvernusEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private AscendFromAvernus(final AscendFromAvernus card) {
        super(card);
    }

    @Override
    public AscendFromAvernus copy() {
        return new AscendFromAvernus(this);
    }
}

class AscendFromAvernusEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate()
        ));
    }

    AscendFromAvernusEffect() {
        super(Outcome.Benefit);
        staticText = "return all creature and planeswalker cards " +
                "with mana value X or less from your graveyard to the battlefield";
    }

    private AscendFromAvernusEffect(final AscendFromAvernusEffect effect) {
        super(effect);
    }

    @Override
    public AscendFromAvernusEffect copy() {
        return new AscendFromAvernusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(filter, game));
        cards.removeIf(uuid -> game.getCard(uuid).getManaValue() > source.getManaCostsToPay().getX());
        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
