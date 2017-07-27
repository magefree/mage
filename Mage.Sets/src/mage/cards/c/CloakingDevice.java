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
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class CloakingDevice extends CardImpl {

    public CloakingDevice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't be blocked.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedAttachedEffect(AttachmentType.AURA)));

        // Whenever enchanted creature attacks, defending player loses 1 life.
        this.addAbility(new AttacksAttachedTriggeredAbility(new CloakingDeviceLoseLifeDefendingPlayerEffect(1, true), AttachmentType.AURA, false));

    }

    public CloakingDevice(final CloakingDevice card) {
        super(card);
    }

    @Override
    public CloakingDevice copy() {
        return new CloakingDevice(this);
    }
}

class CloakingDeviceLoseLifeDefendingPlayerEffect extends OneShotEffect {

    private DynamicValue amount;
    private boolean attackerIsSource;

    /**
     *
     * @param amount
     * @param attackerIsSource true if the source.getSourceId() contains the
     * attacker false if attacker has to be taken from targetPointer
     */
    public CloakingDeviceLoseLifeDefendingPlayerEffect(int amount, boolean attackerIsSource) {
        this(new StaticValue(amount), attackerIsSource);
    }

    public CloakingDeviceLoseLifeDefendingPlayerEffect(DynamicValue amount, boolean attackerIsSource) {
        super(Outcome.Damage);
        this.amount = amount;
        this.attackerIsSource = attackerIsSource;
    }

    public CloakingDeviceLoseLifeDefendingPlayerEffect(final CloakingDeviceLoseLifeDefendingPlayerEffect effect) {
        super(effect);
        this.amount = effect.amount.copy();
        this.attackerIsSource = effect.attackerIsSource;
    }

    @Override
    public CloakingDeviceLoseLifeDefendingPlayerEffect copy() {
        return new CloakingDeviceLoseLifeDefendingPlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Player defender = game.getPlayer(game.getCombat().getDefendingPlayerId(enchantment.getAttachedTo(), game));
            if (defender != null) {
                defender.loseLife(amount.calculate(game, source, this), game, false);
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "defending player loses " + amount + " life";
    }

}
