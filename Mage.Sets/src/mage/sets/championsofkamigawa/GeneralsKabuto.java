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

package mage.sets.championsofkamigawa;

/**
 *
 * @author Ludwig
 */
import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamageEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class GeneralsKabuto extends CardImpl<GeneralsKabuto> {

    public GeneralsKabuto (UUID ownerId) {
        super(ownerId, 251, "General's Kabuto", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Equipment");

        // Equipped creature has shroud. (It can't be the target of spells or abilities.)
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ShroudAbility.getInstance(), Constants.AttachmentType.EQUIPMENT)));

        // Prevent all combat damage that would be dealt to equipped creature
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GeneralsKabutoEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.PreventDamage, new GenericManaCost(2)));
    }

    public GeneralsKabuto (final GeneralsKabuto card) {
        super(card);
    }

    @Override
    public GeneralsKabuto copy() {
        return new GeneralsKabuto(this);
    }
}

class GeneralsKabutoEffect extends PreventionEffectImpl<GeneralsKabutoEffect> {

    public GeneralsKabutoEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Prevent all combat damage that would be dealt to equipped creature";
    }

    public GeneralsKabutoEffect(final GeneralsKabutoEffect effect) {
        super(effect);
    }

    @Override
    public GeneralsKabutoEffect copy() {
        return new GeneralsKabutoEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        GameEvent preventEvent = new GameEvent(
                        GameEvent.EventType.PREVENT_DAMAGE,
                        source.getFirstTarget(),
                        source.getId(),
                        source.getControllerId(),
                        event.getAmount(),
                        false);
        if (!game.replaceEvent(preventEvent)) {
            int damage = event.getAmount();
            event.setAmount(0);
            game.fireEvent(GameEvent.getEvent(
                                GameEvent.EventType.PREVENTED_DAMAGE,
                                source.getFirstTarget(),
                                source.getId(),
                                source.getControllerId(),
                                damage));
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game) && event instanceof DamageEvent) {
            DamageEvent damageEvent = (DamageEvent) event;
            if (damageEvent.isCombatDamage()) {
                Permanent aura = game.getPermanent(source.getSourceId());
                if (aura != null && aura.getAttachedTo() != null) {
                    if (event.getTargetId().equals(aura.getAttachedTo())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}

