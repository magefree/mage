
package mage.cards.h;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.RedirectionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSource;
import mage.target.common.TargetAnyTarget;

/**
 * @author noxx
 */
public final class HarmsWay extends CardImpl {

    public HarmsWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to any target instead.
        this.getSpellAbility().addEffect(new HarmsWayPreventDamageTargetEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private HarmsWay(final HarmsWay card) {
        super(card);
    }

    @Override
    public HarmsWay copy() {
        return new HarmsWay(this);
    }
}

class HarmsWayPreventDamageTargetEffect extends RedirectionEffect {

    private final TargetSource damageSource;

    public HarmsWayPreventDamageTargetEffect() {
        super(Duration.EndOfTurn, 2, UsageType.ACCORDING_DURATION);
        staticText = "The next 2 damage that a source of your choice would deal to you and/or permanents you control this turn is dealt to any target instead";
        this.damageSource = new TargetSource();
    }

    public HarmsWayPreventDamageTargetEffect(final HarmsWayPreventDamageTargetEffect effect) {
        super(effect);
        this.damageSource = effect.damageSource.copy();
    }

    @Override
    public HarmsWayPreventDamageTargetEffect copy() {
        return new HarmsWayPreventDamageTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.damageSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), source, game);
        super.init(source, game);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // check source
        MageObject object = game.getObject(event.getSourceId());
        if (object == null) {
            game.informPlayers("Couldn't find source of damage");
            return false;
        }

        if (!object.getId().equals(damageSource.getFirstTarget())
                && (!(object instanceof Spell) || !((Spell) object).getSourceId().equals(damageSource.getFirstTarget()))) {
            return false;
        }
        this.redirectTarget = source.getTargets().get(0);

        // check target
        //   check permanent first
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            if (permanent.isControlledBy(source.getControllerId())) {
                // it's your permanent
                return true;
            }
        }
        //   check player
        Player player = game.getPlayer(event.getTargetId());
        if (player != null) {
            if (player.getId().equals(source.getControllerId())) {
                // it is you
                return true;
            }
        }
        return false;
    }

}
