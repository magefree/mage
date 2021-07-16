package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetPlayerOrPlaneswalker;

/**
 *
 * @author zeffirojoe
 */
public final class Fiendlash extends CardImpl {

    public Fiendlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ARTIFACT }, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has reach.
        Ability staticAbility = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        staticAbility.addEffect(new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has reach"));
        this.addAbility(staticAbility);

        // Whenever equipped creature is dealt damage, it deals damage equal to its
        // power to target player or planeswalker.
        this.addAbility(new FiendlashTriggeredAbility());

        // Equip {2}{R}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{2}{R}")));
    }

    private Fiendlash(final Fiendlash card) {
        super(card);
    }

    @Override
    public Fiendlash copy() {
        return new Fiendlash(this);
    }
}

class FiendlashTriggeredAbility extends TriggeredAbilityImpl {

    private boolean usedForCombatDamageStep = false;

    FiendlashTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FiendlashEffect(), false);
        this.addTarget(new TargetPlayerOrPlaneswalker());
    }

    private FiendlashTriggeredAbility(final FiendlashTriggeredAbility ability) {
        super(ability);
        this.usedForCombatDamageStep = ability.usedForCombatDamageStep;
    }

    @Override
    public FiendlashTriggeredAbility copy() {
        return new FiendlashTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = game.getPermanent(this.getSourceId());
        if (equipment == null || equipment.getAttachedTo() == null) {
            return false;
        }
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                && event.getTargetId().equals(equipment.getAttachedTo())) {
            this.getEffects().setValue("equipped", equipment.getAttachedTo());

            if (((DamagedEvent) event).isCombatDamage()) {
                if (!usedForCombatDamageStep) {
                    usedForCombatDamageStep = true;
                    return true;
                }
            } else {
                return true;
            }
        }
        if (event.getType() == GameEvent.EventType.COMBAT_DAMAGE_STEP_POST) {
            usedForCombatDamageStep = false;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature is dealt damage, it deals damage equal to its power to target player or planeswalker.";
    }
}

class FiendlashEffect extends OneShotEffect {

    FiendlashEffect() {
        super(Outcome.Benefit);
    }

    private FiendlashEffect(final FiendlashEffect effect) {
        super(effect);
    }

    @Override
    public FiendlashEffect copy() {
        return new FiendlashEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanentOrLKIBattlefield((UUID)getValue("equipped"));
        if (creature == null) {
            return false;
        }

        int damage = creature.getPower().getValue();
        if (damage < 1) {
            return false;
        }        

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.isPlaneswalker()) {
                permanent.damage(damage, creature.getId(), source, game);
                return true;
            }
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(damage, creature.getId(), source, game);
            return true;
        }
        return false;
    }
}
