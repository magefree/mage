package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.OnLeaveReturnExiledAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.token.RobotToken;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PinnacleStarcage extends CardImpl {

    public PinnacleStarcage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}{W}");

        // When this artifact enters, exile all artifacts and creatures with mana value 2 or less until this artifact leaves the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new PinnacleStarcageExileEffect()));

        // {6}{W}{W}: Put each card exiled with this artifact into its owner's graveyard, then create a 2/2 colorless Robot artifact token for each card put into a graveyard this way. Sacrifice this artifact.
        Ability ability = new SimpleActivatedAbility(new PinnacleStarcageTokenEffect(), new ManaCostsImpl<>("{6}{W}{W}"));
        ability.addEffect(new SacrificeSourceEffect());
        this.addAbility(ability);
    }

    private PinnacleStarcage(final PinnacleStarcage card) {
        super(card);
    }

    @Override
    public PinnacleStarcage copy() {
        return new PinnacleStarcage(this);
    }
}

class PinnacleStarcageExileEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    PinnacleStarcageExileEffect() {
        super(Outcome.Benefit);
        staticText = "exile all artifacts and creatures with mana value 2 or less until {this} leaves the battlefield";
    }

    private PinnacleStarcageExileEffect(final PinnacleStarcageExileEffect effect) {
        super(effect);
    }

    @Override
    public PinnacleStarcageExileEffect copy() {
        return new PinnacleStarcageExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || source.getSourcePermanentIfItStillExists(game) == null) {
            return false;
        }
        Cards cards = new CardsImpl(game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCardsToExile(
                cards.getCards(game), source, game, true,
                CardUtil.getExileZoneId(game, source),
                CardUtil.getSourceName(game, source)
        );
        game.addDelayedTriggeredAbility(new OnLeaveReturnExiledAbility(), source);
        return true;
    }
}

class PinnacleStarcageTokenEffect extends OneShotEffect {

    PinnacleStarcageTokenEffect() {
        super(Outcome.Benefit);
        staticText = "put each card exiled with this artifact into its owner's graveyard, " +
                "then create a 2/2 colorless Robot artifact creature token for each card put into a graveyard this way";
    }

    private PinnacleStarcageTokenEffect(final PinnacleStarcageTokenEffect effect) {
        super(effect);
    }

    @Override
    public PinnacleStarcageTokenEffect copy() {
        return new PinnacleStarcageTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Optional.ofNullable(CardUtil.getExileZoneId(game, source))
                .map(game.getExile()::getExileZone)
                .map(e -> e.getCards(game))
                .ifPresent(cards::addAllCards);
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        cards.retainZone(Zone.GRAVEYARD, game);
        if (cards.size() > 0) {
            new RobotToken().putOntoBattlefield(cards.size(), game, source);
        }
        return true;
    }
}
