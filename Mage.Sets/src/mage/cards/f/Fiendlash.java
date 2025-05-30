package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPermanentEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetPlayerOrPlaneswalker;

import java.util.UUID;

/**
 * @author zeffirojoe
 */
public final class Fiendlash extends CardImpl {

    public Fiendlash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and has reach.
        Ability staticAbility = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        staticAbility.addEffect(new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText("and has reach"));
        this.addAbility(staticAbility);

        // Whenever equipped creature is dealt damage, it deals damage equal to its power to target player or planeswalker.
        this.addAbility(new FiendlashTriggeredAbility());

        // Equip {2}{R}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{2}{R}"), new TargetControlledCreaturePermanent(), false));
    }

    private Fiendlash(final Fiendlash card) {
        super(card);
    }

    @Override
    public Fiendlash copy() {
        return new Fiendlash(this);
    }
}

class FiendlashTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<DamagedPermanentEvent> {

    FiendlashTriggeredAbility() {
        super(Zone.BATTLEFIELD, new FiendlashEffect(), false);
        this.addTarget(new TargetPlayerOrPlaneswalker());
    }

    private FiendlashTriggeredAbility(final FiendlashTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FiendlashTriggeredAbility copy() {
        return new FiendlashTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_BATCH_FOR_ONE_PERMANENT;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent equipment = getSourcePermanentIfItStillExists(game);
        if (equipment == null || !event.getTargetId().equals(equipment.getAttachedTo())) {
            return false;
        }
        game.getState().setValue("Fiendlash" + equipment.getId(), equipment.getAttachedTo());
        return true;
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
        Permanent creature = game
                .getPermanentOrLKIBattlefield((UUID) game.getState().getValue("Fiendlash" + source.getSourceId()));
        if (creature == null) {
            return false;
        }

        int damage = creature.getPower().getValue();
        if (damage < 1) {
            return false;
        }

        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            permanent.damage(damage, creature.getId(), source, game);
            return true;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(damage, creature.getId(), source, game);
            return true;
        }
        return false;
    }
}
