package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VanishingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class RealityAcid extends CardImpl {

    public RealityAcid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Vanishing 3
        this.addAbility(new VanishingAbility(3));

        // When Reality Acid leaves the battlefield, enchanted permanent's controller sacrifices it.
        Effect effect = new SacrificeTargetEffect("enchanted permanent's controller sacrifices it");
        this.addAbility(new RealityAcidTriggeredAbility(effect, false));
    }

    private RealityAcid(final RealityAcid card) {
        super(card);
    }

    @Override
    public RealityAcid copy() {
        return new RealityAcid(this);
    }
}

class RealityAcidTriggeredAbility extends ZoneChangeTriggeredAbility {

    public RealityAcidTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, null, effect, "When {this} leaves the battlefield, ", optional);
    }

    private RealityAcidTriggeredAbility(final RealityAcidTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(getSourceId());
            if (sourcePermanent != null && sourcePermanent.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(sourcePermanent.getAttachedTo());
                if (attachedTo != null) {
                    for (Effect effect : getEffects()) {
                        effect.setTargetPointer(new FixedTarget(attachedTo.getId(), attachedTo.getZoneChangeCounter(game)));
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public RealityAcidTriggeredAbility copy() {
        return new RealityAcidTriggeredAbility(this);
    }

}
