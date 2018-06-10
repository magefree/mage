

package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.PutLibraryIntoGraveTargetEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.WolfToken;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Loki
 */
public final class SwordOfBodyAndMind extends CardImpl {

    public SwordOfBodyAndMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+2 and has protection from green and from blue.
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 2));
        Effect effect = new GainAbilityAttachedEffect(ProtectionAbility.from(ObjectColor.GREEN, ObjectColor.BLUE), AttachmentType.EQUIPMENT);
        effect.setText("and has protection from green and from blue");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player, you create a 2/2 green Wolf creature token and that player puts the top ten cards of their library into their graveyard.
        this.addAbility(new SwordOfBodyAndMindAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    public SwordOfBodyAndMind(final SwordOfBodyAndMind card) {
        super(card);
    }

    @Override
    public SwordOfBodyAndMind copy() {
        return new SwordOfBodyAndMind(this);
    }
}

class SwordOfBodyAndMindAbility extends TriggeredAbilityImpl {

    public SwordOfBodyAndMindAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WolfToken()));
        this.addEffect(new PutLibraryIntoGraveTargetEffect(10));
    }

    public SwordOfBodyAndMindAbility(final SwordOfBodyAndMindAbility ability) {
        super(ability);
    }

    @Override
    public SwordOfBodyAndMindAbility copy() {
        return new SwordOfBodyAndMindAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
        Permanent p = game.getPermanent(event.getSourceId());
        if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature deals combat damage to a player, you create a 2/2 green Wolf creature token and that player puts the top ten cards of their library into their graveyard.";
    }
}