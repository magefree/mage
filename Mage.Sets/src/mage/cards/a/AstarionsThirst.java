package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.CommanderPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AstarionsThirst extends CardImpl {

    public AstarionsThirst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{B}");

        // Exile target creature. Put X +1/+1 counters on a commander creature you control, where X is the power of the creature exiled this way.
        this.getSpellAbility().addEffect(new AstarionsThirstEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AstarionsThirst(final AstarionsThirst card) {
        super(card);
    }

    @Override
    public AstarionsThirst copy() {
        return new AstarionsThirst(this);
    }
}

class AstarionsThirstEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("commander creature you control");

    static {
        filter.add(CommanderPredicate.instance);
    }

    AstarionsThirstEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature. Put X +1/+1 counters on a commander creature you control, " +
                "where X is the power of the creature exiled this way";
    }

    private AstarionsThirstEffect(final AstarionsThirstEffect effect) {
        super(effect);
    }

    @Override
    public AstarionsThirstEffect copy() {
        return new AstarionsThirstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || creature == null) {
            return false;
        }
        player.moveCards(creature, Zone.EXILED, source, game);
        if (creature.getPower().getValue() < 1) {
            return true;
        }
        List<Permanent> commanders = game
                .getBattlefield()
                .getActivePermanents(filter, source.getSourceId(), source, game);
        Permanent commander;
        switch (commanders.size()) {
            case 0:
                return true;
            case 1:
                commander = commanders.get(0);
                break;
            default:
                TargetPermanent target = new TargetPermanent(filter);
                target.setNotTarget(true);
                player.choose(outcome, target, source, game);
                commander = game.getPermanent(target.getFirstTarget());
        }
        if (commander != null) {
            commander.addCounters(CounterType.P1P1.createInstance(creature.getPower().getValue()), source, game);
        }
        return true;
    }
}
