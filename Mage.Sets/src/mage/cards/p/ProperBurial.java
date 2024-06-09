package mage.cards.p;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author North
 */
public final class ProperBurial extends CardImpl {

    public ProperBurial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");


        // Whenever a creature you control dies, you gain life equal to that creature's toughness.
        this.addAbility(new ProperBurialTriggeredAbility());
    }

    private ProperBurial(final ProperBurial card) {
        super(card);
    }

    @Override
    public ProperBurial copy() {
        return new ProperBurial(this);
    }
}

class ProperBurialTriggeredAbility extends TriggeredAbilityImpl {

    public ProperBurialTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    private ProperBurialTriggeredAbility(final ProperBurialTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ProperBurialTriggeredAbility copy() {
        return new ProperBurialTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        if (zoneChangeEvent.isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null
                    && permanent.isControlledBy(this.getControllerId())
                    && permanent.isCreature(game)) {
                this.getEffects().clear();
                this.addEffect(new GainLifeEffect(permanent.getToughness().getValue()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies, you gain life equal to that creature's toughness.";
    }
}
