
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class AvacynsCollar extends CardImpl {

    public AvacynsCollar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 and has vigilance.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 0));
        ability.addEffect(new GainAbilityAttachedEffect(
                VigilanceAbility.getInstance(), AttachmentType.EQUIPMENT).setText("and has vigilance")
        );
        this.addAbility(ability);

        // Whenever equipped creature dies, if it was a Human, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new AvacynsCollarTriggeredAbility());

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2), false));
    }

    private AvacynsCollar(final AvacynsCollar card) {
        super(card);
    }

    @Override
    public AvacynsCollar copy() {
        return new AvacynsCollar(this);
    }
}

class AvacynsCollarTriggeredAbility extends TriggeredAbilityImpl {

    public AvacynsCollarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new SpiritWhiteToken()));
    }

    public AvacynsCollarTriggeredAbility(final AvacynsCollarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AvacynsCollarTriggeredAbility copy() {
        return new AvacynsCollarTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent.getAttachments().contains(this.getSourceId()) && permanent.hasSubtype(SubType.HUMAN, game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever equipped creature dies, if it was a Human, create a 1/1 white Spirit creature token with flying.";
    }
}
