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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class OppressiveRays extends CardImpl<OppressiveRays> {

    public OppressiveRays(UUID ownerId) {
        super(ownerId, 19, "Oppressive Rays", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Aura");

        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack or block unless its controller pays 3.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OppressiveRaysEffect()));
        // Activated abilities of enchanted creature cost {3} more to activate.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OppressiveRaysCostReductionEffect() ));
    }

    public OppressiveRays(final OppressiveRays card) {
        super(card);
    }

    @Override
    public OppressiveRays copy() {
        return new OppressiveRays(this);
    }
}


class OppressiveRaysEffect extends ReplacementEffectImpl<OppressiveRaysEffect> {

    private static final String effectText = "Enchanted creature can't attack or block unless its controller pays {3}";

    OppressiveRaysEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        staticText = effectText;
    }

    OppressiveRaysEffect ( OppressiveRaysEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            ManaCostsImpl attackTax = new ManaCostsImpl("{3}");
            if (attackTax.canPay(source.getSourceId(), event.getPlayerId(), game)
                    && player.chooseUse(Outcome.Neutral, "Pay {3} to attack?", game)) {
                if (attackTax.payOrRollback(source, game, source.getSourceId(), event.getPlayerId())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
            Permanent attacker = game.getPermanent(event.getTargetId());
            return attacker != null && attacker.getAttachments().contains(source.getSourceId());
        }
        return false;
    }

    @Override
    public OppressiveRaysEffect copy() {
        return new OppressiveRaysEffect(this);
    }

}

class OppressiveRaysCostReductionEffect extends CostModificationEffectImpl<OppressiveRaysCostReductionEffect> {

    OppressiveRaysCostReductionEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Activated abilities of enchanted creature cost {3} more to activate";
    }

    OppressiveRaysCostReductionEffect(OppressiveRaysCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Permanent creature = game.getPermanent(abilityToModify.getSourceId());
        if (creature != null && creature.getAttachments().contains(source.getSourceId())) {
        if (abilityToModify instanceof ActivatedAbility
                && !(abilityToModify instanceof SpellAbility)) {
            return true;
        }
        }
        return false;
    }

    @Override
    public OppressiveRaysCostReductionEffect copy() {
        return new OppressiveRaysCostReductionEffect(this);
    }

}
