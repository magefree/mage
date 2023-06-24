
package mage.cards.p;

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
import mage.game.events.DamagePlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Backfir3
 */
public final class Pariah extends CardImpl {

    public Pariah(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));
        
        // All damage that would be dealt to you is dealt to enchanted creature instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PariahEffect()));
    }

    private Pariah(final Pariah card) {
        super(card);
    }

    @Override
    public Pariah copy() {
        return new Pariah(this);
    }

    static class PariahEffect extends ReplacementEffectImpl {
        PariahEffect() {
            super(Duration.WhileOnBattlefield, Outcome.RedirectDamage);
            staticText = "All damage that would be dealt to you is dealt to enchanted creature instead";
        }

        PariahEffect(final PariahEffect effect) {
            super(effect);
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
            Permanent equipment = game.getPermanent(source.getSourceId());
            if (equipment != null) {
                Permanent permanent = game.getPermanent(equipment.getAttachedTo());
                if (permanent != null) {
                    permanent.damage(damageEvent.getAmount(), event.getSourceId(), source, game, damageEvent.isCombatDamage(), damageEvent.isPreventable());
                    return true;
                }
            }
            return true;
        }

        @Override
        public boolean checksEventType(GameEvent event, Game game) {
            return event.getType() == GameEvent.EventType.DAMAGE_PLAYER;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            return event.getPlayerId().equals(source.getControllerId());
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return true;
        }

        @Override
        public PariahEffect copy() {
            return new PariahEffect(this);
        }
    }
}
