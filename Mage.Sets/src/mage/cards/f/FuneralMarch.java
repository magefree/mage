package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author L_J
 */
public final class FuneralMarch extends CardImpl {

    public FuneralMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature leaves the battlefield, its controller sacrifices a creature.
        this.addAbility(new FuneralMarchTriggeredAbility());
    }

    private FuneralMarch(final FuneralMarch card) {
        super(card);
    }

    @Override
    public FuneralMarch copy() {
        return new FuneralMarch(this);
    }
}

class FuneralMarchTriggeredAbility extends ZoneChangeTriggeredAbility {

    public FuneralMarchTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "its controller"), "When enchanted creature leaves the battlefield, ", false);
    }

    public FuneralMarchTriggeredAbility(final FuneralMarchTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public FuneralMarchTriggeredAbility copy() {
        return new FuneralMarchTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(this.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
            if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
                ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
                if ((fromZone == null || zEvent.getFromZone() == fromZone) && (toZone == null || zEvent.getToZone() == toZone)) {
                    for (Effect effect : getEffects()) {
                        if (zEvent.getTarget() != null) {
                            Permanent attachedTo = (Permanent) game.getLastKnownInformation(enchantment.getAttachedTo(), Zone.BATTLEFIELD, enchantment.getAttachedToZoneChangeCounter());
                            if (attachedTo != null) {
                                effect.setTargetPointer(new FixedTarget(attachedTo.getControllerId()));
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
