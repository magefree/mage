package mage.cards.n;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.continuous.SetCardColorAttachedEffect;
import mage.abilities.effects.common.continuous.SetCardSubtypeAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.IntimidateAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.ManaUtil;

import java.util.UUID;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author nantuko
 */
public final class NimDeathmantle extends CardImpl {

    public NimDeathmantle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2, has intimidate, and is a black Zombie.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                IntimidateAbility.getInstance(), AttachmentType.EQUIPMENT
        ).setText(", has intimidate"));
        ability.addEffect(new SetCardColorAttachedEffect(
                ObjectColor.BLACK, Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT
        ).setText(", and is"));
        ability.addEffect(new SetCardSubtypeAttachedEffect(
                Duration.WhileOnBattlefield, AttachmentType.EQUIPMENT, SubType.ZOMBIE
        ).setText("black Zombie").concatBy("a"));
        this.addAbility(ability);

        // Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {4}. If you do, return that card to the battlefield and attach Nim Deathmantle to it.
        this.addAbility(new NimDeathmantleTriggeredAbility());

        // Equip {4}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(4), new TargetControlledCreaturePermanent(), false));
    }

    private NimDeathmantle(final NimDeathmantle card) {
        super(card);
    }

    @Override
    public NimDeathmantle copy() {
        return new NimDeathmantle(this);
    }
}

class NimDeathmantleTriggeredAbility extends TriggeredAbilityImpl {

    NimDeathmantleTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NimDeathmantleEffect(), false);
    }

    NimDeathmantleTriggeredAbility(NimDeathmantleTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public NimDeathmantleTriggeredAbility copy() {
        return new NimDeathmantleTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        Permanent permanent = zEvent.getTarget();
        if (permanent != null
                && permanent.isOwnedBy(this.controllerId)
                && zEvent.isDiesEvent()
                && !(permanent instanceof PermanentToken)
                && permanent.isCreature(game)) {

            getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game) + 1));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature is put into your graveyard from the battlefield, you may pay {4}. If you do, return that card to the battlefield and attach {this} to it.";
    }
}

class NimDeathmantleEffect extends OneShotEffect {

    private final Cost cost = ManaUtil.createManaCost(4, false);

    public NimDeathmantleEffect() {
        super(Outcome.Benefit);

    }

    public NimDeathmantleEffect(NimDeathmantleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (controller != null && equipment != null) {
            if (controller.chooseUse(Outcome.Benefit, equipment.getName() + " - Pay " + cost.getText() + '?', source, game)) {
                cost.clearPaid();
                if (cost.pay(source, game, source, source.getControllerId(), false)) {
                    UUID target = targetPointer.getFirst(game, source);
                    if (target != null) {
                        Card card = game.getCard(target);
                        // check if it's still in graveyard
                        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                            if (controller.moveCards(card, Zone.BATTLEFIELD, source, game)) {
                                Permanent permanent = game.getPermanent(card.getId());
                                if (permanent != null) {
                                    permanent.addAttachment(equipment.getId(), source, game);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public NimDeathmantleEffect copy() {
        return new NimDeathmantleEffect(this);
    }

}
