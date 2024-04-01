package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInGraveyardEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LivelyDirge extends CardImpl {

    public LivelyDirge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Spree
        this.addAbility(new SpreeAbility(this));

        // + {1} -- Search your library for a card, put it into your graveyard, then shuffle.
        this.getSpellAbility().addEffect(new SearchLibraryPutInGraveyardEffect());
        this.getSpellAbility().withFirstModeCost(new GenericManaCost(1));

        // + {2} -- Return up to two creature cards with total mana value 4 or less from your graveyard to the battlefield.
        this.getSpellAbility().addMode(new Mode(new LivelyDirgeEffect()).withCost(new GenericManaCost(2)));
    }

    private LivelyDirge(final LivelyDirge card) {
        super(card);
    }

    @Override
    public LivelyDirge copy() {
        return new LivelyDirge(this);
    }
}

class LivelyDirgeEffect extends OneShotEffect {

    LivelyDirgeEffect() {
        super(Outcome.Benefit);
        staticText = "return up to two creature cards with total mana value 4 or less from your graveyard to the battlefield";
    }

    private LivelyDirgeEffect(final LivelyDirgeEffect effect) {
        super(effect);
    }

    @Override
    public LivelyDirgeEffect copy() {
        return new LivelyDirgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard targetCard = new LivelyDirgeTarget();
        player.choose(outcome, targetCard, source, game);
        Cards cards = new CardsImpl(targetCard.getTargets());
        return player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}

class LivelyDirgeTarget extends TargetCardInYourGraveyard {

    private static final FilterCard filter
            = new FilterCreatureCard("creature cards with total mana value 4 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 5));
    }

    LivelyDirgeTarget() {
        super(0, 2, filter, true);
    }

    private LivelyDirgeTarget(final LivelyDirgeTarget target) {
        super(target);
    }

    @Override
    public LivelyDirgeTarget copy() {
        return new LivelyDirgeTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(controllerId, id, source, game)) {
            return false;
        }
        Card card = game.getCard(id);
        return card != null &&
                this.getTargets()
                        .stream()
                        .map(game::getCard)
                        .mapToInt(Card::getManaValue)
                        .sum() + card.getManaValue() <= 4;
    }
}
