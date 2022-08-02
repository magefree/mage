
package mage.cards.l;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class LightmineField extends CardImpl {

    public LightmineField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Whenever one or more creatures attack, Lightmine Field deals damage to each of those creatures equal to the number of attacking creatures.
        this.addAbility(new LightmineFieldTriggeredAbility());
    }

    private LightmineField(final LightmineField card) {
        super(card);
    }

    @Override
    public LightmineField copy() {
        return new LightmineField(this);
    }
}

class LightmineFieldTriggeredAbility extends TriggeredAbilityImpl {

    public LightmineFieldTriggeredAbility() {
        super(Zone.BATTLEFIELD, new LightmineFieldEffect());
        setTriggerPhrase("Whenever one or more creatures attack, ");
    }

    public LightmineFieldTriggeredAbility(final LightmineFieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LightmineFieldTriggeredAbility copy() {
        return new LightmineFieldTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<MageObjectReference> attackSet = new HashSet<>();
        for (UUID attackerId : game.getCombat().getAttackers()) {
            Permanent attacker = game.getPermanent(attackerId);
            if (attacker != null) {
                attackSet.add(new MageObjectReference(attacker, game));
            }
        }
        for (Effect effect : getEffects()) {
            effect.setValue("Lightmine Field", attackSet);
        }
        return !attackSet.isEmpty();
    }
}

class LightmineFieldEffect extends OneShotEffect {

    public LightmineFieldEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals damage to each of those creatures equal to the number of attacking creatures";
    }

    public LightmineFieldEffect(final LightmineFieldEffect effect) {
        super(effect);
    }

    @Override
    public LightmineFieldEffect copy() {
        return new LightmineFieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> attackers = game.getCombat().getAttackers();
        int damage = attackers.size();
        Set<MageObjectReference> attackSet = (Set<MageObjectReference>) getValue("Lightmine Field");
        if (!attackers.isEmpty()) {
            for (Iterator<MageObjectReference> it = attackSet.iterator(); it.hasNext();) {
                MageObjectReference attacker = it.next();
                Permanent creature = attacker.getPermanent(game);
                if (creature != null) {
                    creature.damage(damage, source.getSourceId(), source, game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
