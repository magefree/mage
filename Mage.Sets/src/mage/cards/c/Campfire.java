package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Campfire extends CardImpl {

    public Campfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}: You gain 2 life.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(2), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // {2}, {T}: Exile Campfire: Put all commanders you own from the command zone and from your graveyard into your hand. Then shuffle your graveyard into your library.
        ability = new SimpleActivatedAbility(new CampfireEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private Campfire(final Campfire card) {
        super(card);
    }

    @Override
    public Campfire copy() {
        return new Campfire(this);
    }
}

class CampfireEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(CommanderPredicate.instance);
    }

    CampfireEffect() {
        super(Outcome.Benefit);
        staticText = "put all commanders you own from the command zone and from " +
                "your graveyard into your hand. Then shuffle your graveyard into your library";
    }

    private CampfireEffect(final CampfireEffect effect) {
        super(effect);
    }

    @Override
    public CampfireEffect copy() {
        return new CampfireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getCommanderCardsFromCommandZone(player, CommanderCardType.ANY));
        cards.addAllCards(player.getGraveyard().getCards(filter, game));
        player.moveCards(cards, Zone.HAND, source, game);
        player.putCardsOnBottomOfLibrary(player.getGraveyard(), game, source, false);
        player.shuffleLibrary(source, game);
        return true;
    }
}
