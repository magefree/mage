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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward
 */
public class CurseOfBloodletting extends CardImpl<CurseOfBloodletting> {

    public CurseOfBloodletting(UUID ownerId) {
        super(ownerId, 85, "Curse of Bloodletting", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Aura");
        this.subtype.add("Curse");

        this.color.setRed(true);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Damage));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));
        
        // If a source would deal damage to enchanted player, it deals double that damage to that player instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new CurseOfBloodlettingEffect()));
    }

    public CurseOfBloodletting(final CurseOfBloodletting card) {
        super(card);
    }

    @Override
    public CurseOfBloodletting copy() {
        return new CurseOfBloodletting(this);
    }
}

class CurseOfBloodlettingEffect extends ReplacementEffectImpl<CurseOfBloodlettingEffect> {

	public CurseOfBloodlettingEffect() {
		super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Damage);
		staticText = "If a source would deal damage to enchanted player, it deals double that damage to that player instead";
	}

	public CurseOfBloodlettingEffect(final CurseOfBloodlettingEffect effect) {
		super(effect);
	}

	@Override
	public CurseOfBloodlettingEffect copy() {
		return new CurseOfBloodlettingEffect(this);
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		switch (event.getType()) {
			case DAMAGE_PLAYER:
				StackObject spell = game.getStack().getStackObject(event.getSourceId());
				if (spell != null) {
                    Permanent enchantment = game.getPermanent(source.getSourceId());
                    if (enchantment != null && enchantment.getAttachedTo() != null) {
                        Player player = game.getPlayer(enchantment.getAttachedTo());
                        if (player != null && event.getTargetId().equals(player.getId())) {
                            event.setAmount(event.getAmount() * 2);
                        }
                    }
				}
		}
		return false;
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		return apply(game, source);
	}

}