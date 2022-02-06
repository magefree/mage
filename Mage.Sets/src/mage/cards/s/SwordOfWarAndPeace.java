package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CardsInControllerHandCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class SwordOfWarAndPeace extends CardImpl {

    public SwordOfWarAndPeace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from red and from white.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 2));
        ability.addEffect(new GainAbilityAttachedEffect(
                ProtectionAbility.from(ObjectColor.RED, ObjectColor.WHITE), AttachmentType.EQUIPMENT
        ).setText("and has protection from red and from white"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, Sword of War and Peace deals damage to that player equal to the number of cards in their hand and you gain 1 life for each card in your hand.
        this.addAbility(new SwordOfWarAndPeaceAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), false));
    }

    private SwordOfWarAndPeace(final SwordOfWarAndPeace card) {
        super(card);
    }

    @Override
    public SwordOfWarAndPeace copy() {
        return new SwordOfWarAndPeace(this);
    }

}

class SwordOfWarAndPeaceAbility extends TriggeredAbilityImpl {

    public SwordOfWarAndPeaceAbility() {
        super(Zone.BATTLEFIELD, new SwordOfWarAndPeaceDamageEffect());
        this.addEffect(new GainLifeEffect(CardsInControllerHandCount.instance));
    }

    public SwordOfWarAndPeaceAbility(final SwordOfWarAndPeaceAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfWarAndPeaceAbility copy() {
        return new SwordOfWarAndPeaceAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent) event;
        Permanent damageSource = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (damageEvent.isCombatDamage() && damageSource != null && damageSource.getAttachments().contains(this.getSourceId())) {
            getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, {this} deals damage to that player equal to the number of cards in their hand and you gain 1 life for each card in your hand.";
    }
}

class SwordOfWarAndPeaceDamageEffect extends OneShotEffect {

    SwordOfWarAndPeaceDamageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals damage to that player equal to the number of cards in their hand";
    }

    SwordOfWarAndPeaceDamageEffect(final SwordOfWarAndPeaceDamageEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(targetPlayer.getHand().size(), source.getSourceId(), source, game);
        }
        return true;
    }

    @Override
    public SwordOfWarAndPeaceDamageEffect copy() {
        return new SwordOfWarAndPeaceDamageEffect(this);
    }

}
