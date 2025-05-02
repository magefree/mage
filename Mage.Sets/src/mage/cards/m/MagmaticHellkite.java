package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmaticHellkite extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("nonbasic land an opponent controls");

    static {
        filter.add(Predicates.not(SuperType.BASIC.getPredicate()));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public MagmaticHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, destroy target nonbasic land an opponent controls. Its controller searches their library for a basic land card, puts it onto the battlefield tapped with a stun counter on it, then shuffles.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MagmaticHellkiteEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MagmaticHellkite(final MagmaticHellkite card) {
        super(card);
    }

    @Override
    public MagmaticHellkite copy() {
        return new MagmaticHellkite(this);
    }
}

class MagmaticHellkiteEffect extends OneShotEffect {

    MagmaticHellkiteEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target nonbasic land an opponent controls. " +
                "Its controller searches their library for a basic land card, " +
                "puts it onto the battlefield tapped with a stun counter on it, then shuffles";
    }

    private MagmaticHellkiteEffect(final MagmaticHellkiteEffect effect) {
        super(effect);
    }

    @Override
    public MagmaticHellkiteEffect copy() {
        return new MagmaticHellkiteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        permanent.destroy(source, game);
        if (player == null) {
            return true;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND);
        player.searchLibrary(target, source, game);
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card == null) {
            player.shuffleLibrary(source, game);
            return true;
        }
        game.setEnterWithCounters(card.getId(), new Counters().addCounter(CounterType.STUN.createInstance()));
        player.moveCards(
                card, Zone.BATTLEFIELD, source, game, true,
                false, false, null
        );
        player.shuffleLibrary(source, game);
        return true;
    }
}
