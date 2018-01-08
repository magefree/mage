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
package mage.cards.t;

import java.util.Set;
import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.watchers.common.AttackedLastTurnWatcher;

/**
 *
 * @author L_J
 */
public class TangleKelp extends CardImpl {

    public TangleKelp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // When Tangle Kelp enters the battlefield, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // Enchanted creature doesn't untap during its controller's untap step if it attacked during its controller's last turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapIfAttackedLastTurnEnchantedEffect()), new AttackedLastTurnWatcher());
    }

    public TangleKelp(final TangleKelp card) {
        super(card);
    }

    @Override
    public TangleKelp copy() {
        return new TangleKelp(this);
    }
}

class DontUntapIfAttackedLastTurnEnchantedEffect extends ContinuousRuleModifyingEffectImpl {

    public DontUntapIfAttackedLastTurnEnchantedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, true);
        staticText = "Enchanted creature doesn't untap during its controller's untap step if it attacked during its controller's last turn";
    }

    public DontUntapIfAttackedLastTurnEnchantedEffect(final DontUntapIfAttackedLastTurnEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public DontUntapIfAttackedLastTurnEnchantedEffect copy() {
        return new DontUntapIfAttackedLastTurnEnchantedEffect(this);
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (enchanted != null) {
                return enchanted.getLogName() + " doesn't untap during its controller's untap step (" + enchantment.getLogName() + ')';
            }
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.UNTAP;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getTurn().getStepType() == PhaseStep.UNTAP) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null && event.getTargetId().equals(enchantment.getAttachedTo())) {
                Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
                if (permanent != null &&  permanent.getControllerId().equals(game.getActivePlayerId())) {
                    AttackedLastTurnWatcher watcher = (AttackedLastTurnWatcher) game.getState().getWatchers().get(AttackedLastTurnWatcher.class.getSimpleName());
                    if (watcher != null) {
                        Set<MageObjectReference> attackingCreatures = watcher.getAttackedLastTurnCreatures(permanent.getControllerId());
                        MageObjectReference mor = new MageObjectReference(permanent, game);
                        if (attackingCreatures.contains(mor)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}
