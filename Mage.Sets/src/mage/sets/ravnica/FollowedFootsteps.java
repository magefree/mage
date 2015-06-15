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
package mage.sets.ravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EmptyToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox

 */
public class FollowedFootsteps extends CardImpl {

    public FollowedFootsteps(UUID ownerId) {
        super(ownerId, 51, "Followed Footsteps", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");
        this.expansionSetCode = "RAV";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Copy));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of your upkeep, put a token that's a copy of enchanted creature onto the battlefield.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new FollowedFootstepsEffect(), false));
    }

    public FollowedFootsteps(final FollowedFootsteps card) {
        super(card);
    }

    @Override
    public FollowedFootsteps copy() {
        return new FollowedFootsteps(this);
    }
}

class FollowedFootstepsEffect extends OneShotEffect {

    public FollowedFootstepsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "You put a token onto the battlefield that's a copy of enchanted creature";
    }

    public FollowedFootstepsEffect(final FollowedFootstepsEffect effect) {
        super(effect);
    }

    @Override
    public FollowedFootstepsEffect copy() {
        return new FollowedFootstepsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Permanent target = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
        if (target != null) {
            EmptyToken token = new EmptyToken();
            CardUtil.copyTo(token).from(target);
            token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
            return true;
        }
        return false;
    }
}
