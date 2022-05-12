package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class TormentorsHelm extends CardImpl {

    public TormentorsHelm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // Whenever equipped creature becomes blocked, it deals 1 damage to defending player.
        this.addAbility(new TormentorsHelmTriggeredAbility());

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private TormentorsHelm(final TormentorsHelm card) {
        super(card);
    }

    @Override
    public TormentorsHelm copy() {
        return new TormentorsHelm(this);
    }
}

class TormentorsHelmTriggeredAbility extends TriggeredAbilityImpl {

    public TormentorsHelmTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private TormentorsHelmTriggeredAbility(final TormentorsHelmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TormentorsHelmTriggeredAbility copy() {
        return new TormentorsHelmTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREATURE_BLOCKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(sourceId);
        if (equipment != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null && creature.getId().equals(event.getTargetId())) {
                this.getEffects().clear();
                TormentorsHelmEffect effect = new TormentorsHelmEffect(creature.getId());
                effect.setTargetPointer(new FixedTarget(game.getCombat().getDefendingPlayerId(creature.getId(), game)));
                this.addEffect(effect);
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature becomes blocked, it deals 1 damage to defending player.";
    }
}

class TormentorsHelmEffect extends OneShotEffect {

    private final UUID creatureId;

    public TormentorsHelmEffect(UUID creatureId) {
        super(Outcome.Damage);
        this.creatureId = creatureId;
    }

    private TormentorsHelmEffect(final TormentorsHelmEffect effect) {
        super(effect);
        this.creatureId = effect.creatureId;
    }

    @Override
    public TormentorsHelmEffect copy() {
        return new TormentorsHelmEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            player.damage(1, creatureId, source, game);
            return true;
        }
        return false;
    }
}
