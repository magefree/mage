package mage.cards.l;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInGraveyardEffect;
import mage.abilities.keyword.SpreeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.Set;
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
        this.getSpellAbility().addEffect(new SearchLibraryPutInGraveyardEffect(false));
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

    private static final FilterCard filterStatic
            = new FilterCreatureCard("creature cards with total mana value 4 or less from your graveyard");

    LivelyDirgeTarget() {
        super(0, 2, filterStatic, true);
    }

    private LivelyDirgeTarget(final LivelyDirgeTarget target) {
        super(target);
    }

    @Override
    public LivelyDirgeTarget copy() {
        return new LivelyDirgeTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        return super.canTarget(playerId, id, source, game)
                && CardUtil.checkCanTargetTotalValueLimit(
                this.getTargets(), id, MageObject::getManaValue, 4, game);
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        return CardUtil.checkPossibleTargetsTotalValueLimit(this.getTargets(),
                super.possibleTargets(sourceControllerId, source, game),
                MageObject::getManaValue, 4, game);
    }

    @Override
    public String getMessage(Game game) {
        // shows selected total
        int selectedValue = this.getTargets().stream()
                .map(game::getObject)
                .filter(Objects::nonNull)
                .mapToInt(MageObject::getManaValue)
                .sum();
        return super.getMessage(game) + " (selected total mana value " + selectedValue + ")";
    }
}
