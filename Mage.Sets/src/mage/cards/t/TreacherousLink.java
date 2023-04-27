package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author L_J
 */
public final class TreacherousLink extends CardImpl {

    public TreacherousLink(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // All damage that would be dealt to enchanted creature is dealt to its controller instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TreacherousLinkEffect()));
    }

    private TreacherousLink(final TreacherousLink card) {
        super(card);
    }

    @Override
    public TreacherousLink copy() {
        return new TreacherousLink(this);
    }
}

class TreacherousLinkEffect extends ReplacementEffectImpl {

    public TreacherousLinkEffect() {
        super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
        staticText = "All damage that would be dealt to enchanted creature is dealt to its controller instead";
    }

    public TreacherousLinkEffect(final TreacherousLinkEffect effect) {
        super(effect);
    }

    @Override
    public TreacherousLinkEffect copy() {
        return new TreacherousLinkEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGE_PERMANENT;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamageEvent damageEvent = (DamageEvent) event;
        Permanent enchantedCreature = game.getPermanentOrLKIBattlefield(damageEvent.getTargetId());
        if (enchantedCreature == null) {
            return false;
        }
        Player controller = game.getPlayer(enchantedCreature.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.damage(damageEvent.getAmount(), damageEvent.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable(), damageEvent.getAppliedEffects());
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        }
        Player controller = game.getPlayer(source.getControllerId());
        DamageEvent damageEvent = (DamageEvent) event;
        if (controller != null && enchantment != null) {
            Permanent enchantedCreature = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
            if (enchantedCreature != null) {
                return enchantedCreature.getId().equals(damageEvent.getTargetId());
            }
        }
        return false;
    }
}
