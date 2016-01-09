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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockAttachedEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class VisionsOfBrutality extends CardImpl {

    public VisionsOfBrutality(UUID ownerId) {
        super(ownerId, 81, "Visions of Brutality", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "OGW";
        this.subtype.add("Aura");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));
        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't block.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBlockAttachedEffect(AttachmentType.AURA)));

        // Whenever enchanted creature deals damage, its controller loses that much life.
        this.addAbility(new DealsDamageAttachedTriggeredAbility(Zone.BATTLEFIELD, new VisionsOfBrutalityEffect(), false));
    }

    public VisionsOfBrutality(final VisionsOfBrutality card) {
        super(card);
    }

    @Override
    public VisionsOfBrutality copy() {
        return new VisionsOfBrutality(this);
    }
}

class VisionsOfBrutalityEffect extends OneShotEffect {

    public VisionsOfBrutalityEffect() {
        super(Outcome.Benefit);
        this.staticText = "its controller loses that much life";
    }

    public VisionsOfBrutalityEffect(final VisionsOfBrutalityEffect effect) {
        super(effect);
    }

    @Override
    public VisionsOfBrutalityEffect copy() {
        return new VisionsOfBrutalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (enchantment != null && enchantment.getAttachedTo() != null) {
                Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
                if (enchanted != null) {
                    Player controllerEnchanted = game.getPlayer(enchanted.getControllerId());
                    if (controllerEnchanted != null) {
                        int damage = (Integer) getValue("damage");
                        if (damage > 0) {
                            controllerEnchanted.loseLife(damage, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
