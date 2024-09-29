package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NurturingPixie extends CardImpl {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("non-Faerie, nonland permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.not(SubType.FAERIE.getPredicate()));
    }

    public NurturingPixie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Nurturing Pixie enters the battlefield, return up to one target non-Faerie, nonland permanent you control to its owner's hand. If a permanent was returned this way, put a +1/+1 counter on Nurturing Pixie.
        Ability ability = new EntersBattlefieldTriggeredAbility(new NurturingPixieEffect());
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private NurturingPixie(final NurturingPixie card) {
        super(card);
    }

    @Override
    public NurturingPixie copy() {
        return new NurturingPixie(this);
    }
}

class NurturingPixieEffect extends OneShotEffect {

    NurturingPixieEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one target non-Faerie, nonland permanent you control " +
                "to its owner's hand. If a permanent was returned this way, put a +1/+1 counter on {this}";
    }

    private NurturingPixieEffect(final NurturingPixieEffect effect) {
        super(effect);
    }

    @Override
    public NurturingPixieEffect copy() {
        return new NurturingPixieEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.HAND, source, game);
        Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                .ifPresent(p -> p.addCounters(CounterType.P1P1.createInstance(), source, game));
        return true;
    }
}
