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
package mage.sets.tempest;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.SpecialAction;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import static mage.sets.tempest.VolrathsCurse.keyString;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class VolrathsCurse extends CardImpl {

    public static final String keyString = "_ignoreEffectForTurn";

    public VolrathsCurse(UUID ownerId) {
        super(ownerId, 101, "Volrath's Curse", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "TMP";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature can't attack or block, and its activated abilities can't be activated. That creature's controller may sacrifice a permanent for that player to ignore this effect until end of turn.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new VolrathsCurseRestrictionEffect());
        ability.addEffect(new VolrathsCurseCantActivateAbilitiesEffect());
        this.addAbility(ability);
        this.addAbility(new VolrathsCurseSpecialAction());

        // {1}{U}: Return Volrath's Curse to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl("{1}{U}")));

    }

    public VolrathsCurse(final VolrathsCurse card) {
        super(card);
    }

    @Override
    public VolrathsCurse copy() {
        return new VolrathsCurse(this);
    }
}

class VolrathsCurseRestrictionEffect extends RestrictionEffect {

    public VolrathsCurseRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Enchanted creature can't attack or block";
    }

    public VolrathsCurseRestrictionEffect(final VolrathsCurseRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null
                && permanent.getId().equals(attachment.getAttachedTo())) {
            String key = source.getSourceId().toString() + attachment.getZoneChangeCounter(game) + keyString + game.getTurnNum() + permanent.getControllerId();
            return game.getState().getValue(key) == null;
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }

    @Override
    public VolrathsCurseRestrictionEffect copy() {
        return new VolrathsCurseRestrictionEffect(this);
    }
}

class VolrathsCurseCantActivateAbilitiesEffect extends ContinuousRuleModifyingEffectImpl {

    public VolrathsCurseCantActivateAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.UnboostCreature);
        staticText = ", and its activated abilities can't be activated";
    }

    public VolrathsCurseCantActivateAbilitiesEffect(final VolrathsCurseCantActivateAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public VolrathsCurseCantActivateAbilitiesEffect copy() {
        return new VolrathsCurseCantActivateAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATE_ABILITY;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (event.getSourceId().equals(enchantment.getAttachedTo())) {
                Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
                if (enchanted != null) {
                    String key = source.getSourceId().toString() + enchantment.getZoneChangeCounter(game) + keyString + game.getTurnNum() + enchanted.getControllerId();
                    return game.getState().getValue(key) == null;
                }
            }
        }
        return false;
    }
}

class VolrathsCurseSpecialAction extends SpecialAction {

    public VolrathsCurseSpecialAction() {
        super(Zone.BATTLEFIELD);
        this.addCost(new SacrificeTargetCost(new TargetControlledPermanent(), true));
        this.addEffect(new VolrathsCurseIgnoreEffect(keyString));
        this.setMayActivate(TargetController.CONTROLLER_ATTACHED_TO);
    }

    public VolrathsCurseSpecialAction(final VolrathsCurseSpecialAction ability) {
        super(ability);
    }

    @Override
    public VolrathsCurseSpecialAction copy() {
        return new VolrathsCurseSpecialAction(this);
    }
}

class VolrathsCurseIgnoreEffect extends OneShotEffect {

    public VolrathsCurseIgnoreEffect(final String keyString) {
        super(Outcome.Benefit);
        this.staticText = "That creature's controller may sacrifice a permanent for that player to ignore this effect until end of turn";
    }

    public VolrathsCurseIgnoreEffect(final VolrathsCurseIgnoreEffect effect) {
        super(effect);
    }

    @Override
    public VolrathsCurseIgnoreEffect copy() {
        return new VolrathsCurseIgnoreEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        String key = source.getSourceId().toString() + source.getSourceObjectZoneChangeCounter() + keyString + game.getTurnNum() + ((ActivatedAbilityImpl)source).getActivatorId();
        game.getState().setValue(key,true);
        return true;
    }
}
