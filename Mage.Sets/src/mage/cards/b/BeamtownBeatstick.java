package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeamtownBeatstick extends CardImpl {

    public BeamtownBeatstick(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has menace.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                new MenaceAbility(), AttachmentType.EQUIPMENT
        ).setText("and has menace. <i>(It can't be blocked except by two or more creatures.)</i>"));
        this.addAbility(ability);

        // Whenever equipped creature deals combat damage to a player or battle, create a Treasure token.
        this.addAbility(new BeamtownBeatstickTriggeredAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private BeamtownBeatstick(final BeamtownBeatstick card) {
        super(card);
    }

    @Override
    public BeamtownBeatstick copy() {
        return new BeamtownBeatstick(this);
    }
}

class BeamtownBeatstickTriggeredAbility extends TriggeredAbilityImpl {

    BeamtownBeatstickTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new TreasureToken()));
        setTriggerPhrase("Whenever equipped creature deals combat damage to a player or battle, ");
    }

    private BeamtownBeatstickTriggeredAbility(final BeamtownBeatstickTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BeamtownBeatstickTriggeredAbility copy() {
        return new BeamtownBeatstickTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PERMANENT
                || event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        DamagedEvent dEvent = (DamagedEvent) event;
        if (!dEvent.isCombatDamage()) {
            return false;
        }
        Permanent equipment = getSourcePermanentOrLKI(game);
        if (!dEvent.getSourceId().equals(equipment.getAttachedTo())) {
            return false;
        }
        switch (event.getType()) {
            case DAMAGED_PERMANENT:
                Permanent permanent = game.getPermanent(dEvent.getTargetId());
                return permanent != null && permanent.isBattle(game);
            case DAMAGED_PLAYER:
                return true;
        }
        return false;
    }
}
