/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.betrayersofkamigawa;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class KumanosBlessing extends CardImpl<KumanosBlessing> {

    public KumanosBlessing(UUID ownerId) {
        super(ownerId, 111, "Kumano's Blessing", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Aura");

        this.color.setRed(true);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // If a creature dealt damage by enchanted creature this turn would die, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KumanosBlessingEffect()));
        this.addWatcher(new DamagedByEnchantedWatcher());
    }

    public KumanosBlessing(final KumanosBlessing card) {
        super(card);
    }

    @Override
    public KumanosBlessing copy() {
        return new KumanosBlessing(this);
    }
}

class KumanosBlessingEffect extends ReplacementEffectImpl<KumanosBlessingEffect> {

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
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent)event).getTarget();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && permanent != null) {
            return controller.moveCardToExileWithInfo(permanent, null, null, source.getSourceId(), game, Zone.BATTLEFIELD);
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            DamagedByEnchantedWatcher watcher = (DamagedByEnchantedWatcher) game.getState().getWatchers().get("DamagedByEnchantedWatcher", source.getSourceId());
            if (watcher != null) {
                return watcher.damagedCreatures.contains(event.getTargetId());
            }
        }
        return false;
    }

}

class DamagedByEnchantedWatcher extends WatcherImpl<DamagedByEnchantedWatcher> {

    public List<UUID> damagedCreatures = new ArrayList<>();

    public DamagedByEnchantedWatcher() {
        super("DamagedByEnchantedWatcher", WatcherScope.CARD);
    }

    public DamagedByEnchantedWatcher(final DamagedByEnchantedWatcher watcher) {
        super(watcher);
        this.damagedCreatures.addAll(watcher.damagedCreatures);
    }

    @Override
    public DamagedByEnchantedWatcher copy() {
        return new DamagedByEnchantedWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == EventType.DAMAGED_CREATURE) {
            Permanent enchantment = game.getPermanent(this.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                if (enchantment.getAttachedTo().equals(event.getSourceId()) && !damagedCreatures.contains(event.getTargetId())) {
                    damagedCreatures.add(event.getTargetId());
                }
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        damagedCreatures.clear();
    }

}
