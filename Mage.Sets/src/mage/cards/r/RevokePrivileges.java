
package mage.cards.r;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantAttackBlockAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RevokePrivileges extends CardImpl {

    public RevokePrivileges(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature.
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't attack, block, or crew Vehicles.
        Effect effect = new CantAttackBlockAttachedEffect(AttachmentType.AURA);
        effect.setText("Enchanted creature can't attack, block");
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability.addEffect(new RevokePrivilegeCantCrewEffect());
        this.addAbility(ability);

    }

    private RevokePrivileges(final RevokePrivileges card) {
        super(card);
    }

    @Override
    public RevokePrivileges copy() {
        return new RevokePrivileges(this);
    }
}

class RevokePrivilegeCantCrewEffect extends ContinuousRuleModifyingEffectImpl {

    public RevokePrivilegeCantCrewEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = ", or crew Vehicles";
    }

    public RevokePrivilegeCantCrewEffect(final RevokePrivilegeCantCrewEffect effect) {
        super(effect);
    }

    @Override
    public RevokePrivilegeCantCrewEffect copy() {
        return new RevokePrivilegeCantCrewEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "The creature enchanted by " + mageObject.getIdName() + " can't crew vehicles.";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CREW_VEHICLE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        return enchantment != null && event.getTargetId().equals(enchantment.getAttachedTo());
    }
}
