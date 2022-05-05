package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.replacement.CreateTwiceThatManyTokensEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KayaGeistHunter extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature token you control");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public KayaGeistHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{W}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.KAYA);
        this.setStartingLoyalty(3);

        // +1: Creatures you control gain deathtouch until end of turn. Put a +1/+1 counter on up to one target creature token you control.
        Ability ability = new LoyaltyAbility(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ), 1);
        ability.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // −2: Until end of turn, if one or more tokens would be created under your control, twice that many of those tokens are created instead.
        this.addAbility(new LoyaltyAbility(new CreateTwiceThatManyTokensEffect(Duration.EndOfTurn), -2));

        // −6: Exile all cards from all graveyards, then create a 1/1 white Spirit creature token with flying for each card exiled this way.
        this.addAbility(new LoyaltyAbility(new KayaGeistHunterEffect(), -6));
    }

    private KayaGeistHunter(final KayaGeistHunter card) {
        super(card);
    }

    @Override
    public KayaGeistHunter copy() {
        return new KayaGeistHunter(this);
    }
}

class KayaGeistHunterEffect extends OneShotEffect {

    KayaGeistHunterEffect() {
        super(Outcome.Benefit);
        staticText = "exile all cards from all graveyards, then create " +
                "a 1/1 white Spirit creature token with flying for each card exiled this way";
    }

    private KayaGeistHunterEffect(final KayaGeistHunterEffect effect) {
        super(effect);
    }

    @Override
    public KayaGeistHunterEffect copy() {
        return new KayaGeistHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        game.getState()
                .getPlayersInRange(source.getControllerId(), game)
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getGraveyard)
                .map(g -> g.getCards(game))
                .flatMap(Collection::stream)
                .forEach(cards::add);
        player.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        if (cards.isEmpty()) {
            return true;
        }
        new SpiritWhiteToken().putOntoBattlefield(cards.size(), game, source);
        return true;
    }
}
