
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class LilianaDeathWielder extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with a -1/-1 counter on it");

    static {
        filter.add(CounterType.M1M1.getPredicate());
    }

    public LilianaDeathWielder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{5}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.LILIANA);

        this.setStartingLoyalty(5);

        // +2: Put a -1/-1 counter on up to one target creature.
        LoyaltyAbility ability = new LoyaltyAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(1)), 2);
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);

        // -3: Destroy target creature with a -1/-1 counter on it.
        ability = new LoyaltyAbility(new DestroyTargetEffect(), -3);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // -10: Return all creature cards from your graveyard to the battlefield.
        this.addAbility(new LoyaltyAbility(new LilianaDeathWielderEffect(), -10));
    }

    private LilianaDeathWielder(final LilianaDeathWielder card) {
        super(card);
    }

    @Override
    public LilianaDeathWielder copy() {
        return new LilianaDeathWielder(this);
    }
}

class LilianaDeathWielderEffect extends OneShotEffect {

    public LilianaDeathWielderEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return all creature cards from your graveyard to the battlefield";
    }

    public LilianaDeathWielderEffect(final LilianaDeathWielderEffect effect) {
        super(effect);
    }

    @Override
    public LilianaDeathWielderEffect copy() {
        return new LilianaDeathWielderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.moveCards(controller.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game), Zone.BATTLEFIELD, source, game);
        }
        return true;
    }
}
