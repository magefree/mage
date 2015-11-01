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
package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * FAQ 2013/01/11
 *
 * 702.97. Cipher
 *
 * 702.97a Cipher appears on some instants and sorceries. It represents two
 * static abilities, one that functions while the spell is on the stack and one
 * that functions while the card with cipher is in the exile zone. "Cipher"
 * means "If this spell is represented by a card, you may exile this card
 * encoded on a creature you control" and "As long as this card is encoded on
 * that creature, that creature has 'Whenever this creature deals combat damage
 * to a player, you may copy this card and you may cast the copy without paying
 * its mana cost.'"
 *
 * 702.97b The term "encoded" describes the relationship between the card with
 * cipher while in the exile zone and the creature chosen when the spell
 * represented by that card resolves.
 *
 * 702.97c The card with cipher remains encoded on the chosen creature as long
 * as the card with cipher remains exiled and the creature remains on the
 * battlefield. The card remains encoded on that object even if it changes
 * controller or stops being a creature, as long as it remains on the
 * battlefield.
 *
 * TODO: Implement Cipher as two static abilities concerning the rules.
 *
 * @author LevelX2
 */
public class CipherEffect extends OneShotEffect {

    public CipherEffect() {
        super(Outcome.Copy);
        staticText = "<br><br/>Cipher <i>(Then you may exile this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)</i>";
    }

    public CipherEffect(final CipherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
            target.setNotTarget(true);
            if (target.canChoose(source.getControllerId(), game)
                    && controller.chooseUse(outcome, "Cipher this spell to a creature?", source, game)) {
                controller.chooseTarget(outcome, target, source, game);
                Card sourceCard = game.getCard(source.getSourceId());
                Permanent targetCreature = game.getPermanent(target.getFirstTarget());
                if (targetCreature != null && sourceCard != null) {
                    String ruleText = new StringBuilder("you may cast a copy of ").append(sourceCard.getLogName()).append(" without paying its mana cost").toString();
                    Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new CipherStoreEffect(source.getSourceId(), ruleText), true);
                    ContinuousEffect effect = new GainAbilityTargetEffect(ability, Duration.Custom);
                    effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                    game.addEffect(effect, source);
                    if (!game.isSimulation()) {
                        game.informPlayers(new StringBuilder(sourceCard.getLogName()).append(": Spell ciphered to ").append(targetCreature.getLogName()).toString());
                    }
                    return controller.moveCards(sourceCard, null, Zone.EXILED, source, game);
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public CipherEffect copy() {
        return new CipherEffect(this);
    }
}

class CipherStoreEffect extends OneShotEffect {

    private final UUID cipherCardId;

    public CipherStoreEffect(UUID cipherCardId, String ruleText) {
        super(Outcome.Copy);
        this.cipherCardId = cipherCardId;
        staticText = ruleText;
    }

    public CipherStoreEffect(final CipherStoreEffect effect) {
        super(effect);
        this.cipherCardId = effect.cipherCardId;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card cipherCard = game.getCard(cipherCardId);
        if (cipherCard != null) {
            Card copyCard = game.copyCard(cipherCard, source, controller.getId());
            SpellAbility ability = copyCard.getSpellAbility();
            // remove the cipher effect from the copy
            Effect cipherEffect = null;
            for (Effect effect : ability.getEffects()) {
                if (effect instanceof CipherEffect) {
                    cipherEffect = effect;
                }
            }
            ability.getEffects().remove(cipherEffect);
            if (ability instanceof SpellAbility) {
                controller.cast(ability, game, true);
            }
        }

        return false;
    }

    @Override
    public CipherStoreEffect copy() {
        return new CipherStoreEffect(this);
    }
}
