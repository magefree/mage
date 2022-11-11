
package mage.cards.k;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.Watcher;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * @author LevelX2
 */
public final class KumanosBlessing extends CardImpl {

    public KumanosBlessing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.subtype.add(SubType.AURA);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // If a creature dealt damage by enchanted creature this turn would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KumanosBlessingEffect()), new DamagedByEnchantedWatcher());

    }

    private KumanosBlessing(final KumanosBlessing card) {
        super(card);
    }

    @Override
    public KumanosBlessing copy() {
        return new KumanosBlessing(this);
    }
}

class KumanosBlessingEffect extends ReplacementEffectImpl {

    public KumanosBlessingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a creature dealt damage by enchanted creature this turn would die, exile it instead";
    }

    public KumanosBlessingEffect(final KumanosBlessingEffect effect) {
        super(effect);
    }

    @Override
    public KumanosBlessingEffect copy() {
        return new KumanosBlessingEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zce = (ZoneChangeEvent) event;
        if (zce.isDiesEvent()) {
            DamagedByEnchantedWatcher watcher = game.getState().getWatcher(DamagedByEnchantedWatcher.class, source.getSourceId());
            if (watcher != null) {
                return watcher.wasDamaged(zce.getTarget(), game);
            }
        }
        return false;
    }

}

class DamagedByEnchantedWatcher extends Watcher {

    private final Set<MageObjectReference> damagedCreatures = new HashSet<>();

    public DamagedByEnchantedWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_PERMANENT) {
            Permanent enchantment = game.getPermanent(this.getSourceId());
            if (enchantment != null && enchantment.isAttachedTo(event.getSourceId())) {
                Permanent permanent = game.getPermanent(event.getTargetId());
                if (permanent != null && permanent.isCreature(game)) {
                    MageObjectReference mor = new MageObjectReference(event.getTargetId(), game);
                    damagedCreatures.add(mor);
                }
            }
        }
    }


    @Override
    public void reset() {
        super.reset();
        damagedCreatures.clear();
    }

    public boolean wasDamaged(Permanent permanent, Game game) {
        return damagedCreatures.contains(new MageObjectReference(permanent, game));
    }
}
