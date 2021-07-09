package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OriqLoremage extends CardImpl {

    public OriqLoremage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}: Search your library for a card, put it into your graveyard, then shuffle. If it's an instant or sorcery card, put a +1/+1 counter on Oriq Loremage.
        this.addAbility(new SimpleActivatedAbility(new OriqLoremageEffect(), new TapSourceCost()));
    }

    private OriqLoremage(final OriqLoremage card) {
        super(card);
    }

    @Override
    public OriqLoremage copy() {
        return new OriqLoremage(this);
    }
}

class OriqLoremageEffect extends OneShotEffect {

    OriqLoremageEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a card, put it into your graveyard, then shuffle. " +
                "If it's an instant or sorcery card, put a +1/+1 counter on {this}";
    }

    private OriqLoremageEffect(final OriqLoremageEffect effect) {
        super(effect);
    }

    @Override
    public OriqLoremageEffect copy() {
        return new OriqLoremageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card == null) {
            player.shuffleLibrary(source, game);
            return true;
        }
        player.moveCards(card, Zone.GRAVEYARD, source, game);
        player.shuffleLibrary(source, game);
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (!card.isInstantOrSorcery(game) || permanent == null) {
            return true;
        }
        permanent.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game);
        return true;
    }
}
