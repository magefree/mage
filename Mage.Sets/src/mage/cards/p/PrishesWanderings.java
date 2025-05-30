package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PrishesWanderings extends CardImpl {

    public PrishesWanderings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Search your library for a basic land card or Town card, put it onto the battlefield tapped, then shuffle. When you search your library this way, put a +1/+1 counter on target creature you control.
        this.getSpellAbility().addEffect(new PrishesWanderingsEffect());
    }

    private PrishesWanderings(final PrishesWanderings card) {
        super(card);
    }

    @Override
    public PrishesWanderings copy() {
        return new PrishesWanderings(this);
    }
}

class PrishesWanderingsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("basic land card or Town card");

    static {
        filter.add(Predicates.or(
                Predicates.and(
                        SuperType.BASIC.getPredicate(),
                        CardType.LAND.getPredicate()
                ),
                SubType.TOWN.getPredicate()
        ));
    }

    PrishesWanderingsEffect() {
        super(Outcome.Benefit);
        staticText = "search your library for a basic land card or Town card, put it onto the battlefield tapped, " +
                "then shuffle. When you search your library this way, put a +1/+1 counter on target creature you control";
    }

    private PrishesWanderingsEffect(final PrishesWanderingsEffect effect) {
        super(effect);
    }

    @Override
    public PrishesWanderingsEffect copy() {
        return new PrishesWanderingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, source, game)) {
            ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                    new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false
            );
            ability.addTarget(new TargetControlledCreaturePermanent());
            game.fireReflexiveTriggeredAbility(ability, source);
        }
        Card card = player.getLibrary().getCard(target.getFirstTarget(), game);
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(
                    card, Zone.BATTLEFIELD, source, game, true,
                    false, false, null
            );
        }
        player.shuffleLibrary(source, game);
        return true;
    }
}
