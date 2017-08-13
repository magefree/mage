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

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;
import java.util.UUID;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.filter.common.FilterNonlandPermanent;
import mage.players.Player;

/**
 *
 * @author Saga
 */
public class CurseOfBounty extends CardImpl {

    public CurseOfBounty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}");
        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Whenever enchanted player is attacked, untap all nonland permanents you control. 
        // Each opponent attacking that player untaps all nonland permanents he or she controls.
        Ability ability = new CurseOfBountyTriggeredAbility();
        ability.addEffect(new UntapAllControllerEffect(new FilterNonlandPermanent()));
        this.addAbility(ability);
    }

    public CurseOfBounty(final CurseOfBounty card) {
        super(card);
    }

    @Override
    public CurseOfBounty copy() {
        return new CurseOfBounty(this);
    }
}

class CurseOfBountyTriggeredAbility extends TriggeredAbilityImpl {

    public CurseOfBountyTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapAllNonlandsTargetEffect(), false);
    }

    public CurseOfBountyTriggeredAbility(final CurseOfBountyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent enchantment = game.getPermanent(this.getSourceId());
        UUID controller = this.getControllerId();
        if (enchantment != null
                && enchantment.getAttachedTo() != null
                && game.getCombat().getPlayerDefenders(game).contains(enchantment.getAttachedTo())) {
            if (game.getCombat().getAttackerId() != controller) {
                for (Effect effect: this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(game.getCombat().getAttackerId()));
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted player is attacked, untap all nonland permanents you control. "
                + "Each opponent attacking that player untaps all nonland permanents he or she controls.";
    }

    @Override
    public CurseOfBountyTriggeredAbility copy() {
        return new CurseOfBountyTriggeredAbility(this);
    }
}

class UntapAllNonlandsTargetEffect extends OneShotEffect {
    
    public UntapAllNonlandsTargetEffect() {
        super(Outcome.Untap);
    }

    public UntapAllNonlandsTargetEffect(final UntapAllNonlandsTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Permanent nonland: game.getBattlefield().getAllActivePermanents(new FilterNonlandPermanent(), player.getId(), game)) {
                nonland.untap(game);
            }
            return true;
        }
        return false;
    }

    @Override
    public UntapAllNonlandsTargetEffect copy() {
        return new UntapAllNonlandsTargetEffect(this);
    }

}
