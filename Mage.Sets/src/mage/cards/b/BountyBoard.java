package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * @author TheElk801
 */
public final class BountyBoard extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("a creature with a bounty counter on it");

    static {
        filter.add(CounterType.BOUNTY.getPredicate());
    }

    public BountyBoard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());

        // {1}, {T}: Put a bounty counter on target creature. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever a creature with a bounty counter on it dies, each of its controller's opponents draws a card and gains 2 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new BountyBoardEffect(), false, filter));
    }

    private BountyBoard(final BountyBoard card) {
        super(card);
    }

    @Override
    public BountyBoard copy() {
        return new BountyBoard(this);
    }
}

class BountyBoardEffect extends OneShotEffect {

    BountyBoardEffect() {
        super(Outcome.Benefit);
        staticText = "each of its controller's opponents draws a card and gains 2 life";
    }

    private BountyBoardEffect(final BountyBoardEffect effect) {
        super(effect);
    }

    @Override
    public BountyBoardEffect copy() {
        return new BountyBoardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getValue("creatureDied"))
                .map(Permanent.class::cast)
                .map(Controllable::getControllerId)
                .map(game::getOpponents)
                .map(Collection::stream)
                .orElseGet(Stream::empty)
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player -> {
                    player.drawCards(1, source, game);
                    player.gainLife(2, game, source);
                });
        return true;
    }
}
