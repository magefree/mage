package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.SaddleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.SaddledSourceThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetSacrifice;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheGitrogRavenousRide extends CardImpl {

    public TheGitrogRavenousRide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.HORROR);
        this.subtype.add(SubType.MOUNT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever The Gitrog, Ravenous Ride deals combat damage to a player, you may sacrifice a creature that saddled it this turn. If you do, draw X cards, then put up to X land cards from your hand onto the battlefield tapped, where X is the sacrificed creature's power.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new TheGitrogRavenousRideEffect(), false));

        // Saddle 1
        this.addAbility(new SaddleAbility(1));
    }

    private TheGitrogRavenousRide(final TheGitrogRavenousRide card) {
        super(card);
    }

    @Override
    public TheGitrogRavenousRide copy() {
        return new TheGitrogRavenousRide(this);
    }
}

class TheGitrogRavenousRideEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature that saddled it this turn");

    static {
        filter.add(SaddledSourceThisTurnPredicate.instance);
    }

    TheGitrogRavenousRideEffect() {
        super(Outcome.Benefit);
        staticText = "you may sacrifice a creature that saddled it this turn. " +
                "If you do, draw X cards, then put up to X land cards from your hand onto the battlefield tapped, " +
                "where X is the sacrificed creature's power";
    }

    private TheGitrogRavenousRideEffect(final TheGitrogRavenousRideEffect effect) {
        super(effect);
    }

    @Override
    public TheGitrogRavenousRideEffect copy() {
        return new TheGitrogRavenousRideEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetSacrifice target = new TargetSacrifice(0, 1, filter);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        int power = permanent.getPower().getValue();
        permanent.sacrifice(source, game);
        player.drawCards(power, source, game);
        TargetCard targetCard = new TargetCardInHand(0, power, StaticFilters.FILTER_CARD_LANDS);
        player.choose(outcome, player.getHand(), targetCard, source, game);
        Cards cards = new CardsImpl(targetCard.getTargets());
        player.moveCards(
                cards.getCards(game), Zone.BATTLEFIELD, source, game,
                true, false, false, null
        );
        return true;
    }
}
