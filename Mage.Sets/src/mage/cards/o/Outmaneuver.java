package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Outmaneuver extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new BlockedPredicate());
    }

    public Outmaneuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}");

        // X target blocked creatures assign their combat damage this turn as though they weren't blocked.
        this.getSpellAbility().addEffect(new OutmaneuverEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));

    }

    public Outmaneuver(final Outmaneuver card) {
        super(card);
    }

    @Override
    public Outmaneuver copy() {
        return new Outmaneuver(this);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            ability.getTargets().clear();
            int numberOfTargets = ability.getManaCostsToPay().getX();
            numberOfTargets = Math.min(game.getBattlefield().getAllActivePermanents(filter,
                    ability.getControllerId(), game).size(), numberOfTargets);
            ability.addTarget(new TargetCreaturePermanent(numberOfTargets,
                    numberOfTargets, filter, false));
        }
    }
}

class OutmaneuverEffect extends AsThoughEffectImpl {

    public OutmaneuverEffect() {
        super(AsThoughEffectType.DAMAGE_NOT_BLOCKED, Duration.EndOfTurn, Outcome.Damage);
        this.staticText = "X target blocked creatures assign their combat damage this turn as though they weren't blocked.";
    }

    public OutmaneuverEffect(OutmaneuverEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        Permanent blockedCreature = game.getPermanent(sourceId);
        if (blockedCreature != null) {
            Player controller = game.getPlayer(blockedCreature.getControllerId());
            if (controller != null) {
                return controller.chooseUse(Outcome.Damage, "Do you wish to assign combat damage for "
                        + blockedCreature.getLogName() + " as though it weren't blocked?", source, game);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OutmaneuverEffect copy() {
        return new OutmaneuverEffect(this);
    }
}
