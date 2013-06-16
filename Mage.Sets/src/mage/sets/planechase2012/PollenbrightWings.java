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
package mage.sets.planechase2012;

import java.util.UUID;

import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapAllLandsControllerEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class PollenbrightWings extends CardImpl<PollenbrightWings> {

    public PollenbrightWings(UUID ownerId) {
        super(ownerId, 103, "Pollenbright Wings", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{W}");
        this.expansionSetCode = "PC2";
        this.subtype.add("Aura");

        this.color.setGreen(true);
        this.color.setWhite(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature has flying.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield)));
        // Whenever enchanted creature deals combat damage to a player, put that many 1/1 green Saproling creature tokens onto the battlefield.
        this.addAbility(new PollenbrightWingsAbility());
    }

    public PollenbrightWings(final PollenbrightWings card) {
        super(card);
    }

    @Override
    public PollenbrightWings copy() {
        return new PollenbrightWings(this);
    }
}

class PollenbrightWingsAbility extends TriggeredAbilityImpl<PollenbrightWingsAbility> {

    public PollenbrightWingsAbility() {
        super(Zone.BATTLEFIELD, new PollenbrightWingsEffect());
        this.addEffect(new UntapAllLandsControllerEffect());
    }

    public PollenbrightWingsAbility(final PollenbrightWingsAbility ability) {
        super(ability);
    }

    @Override
    public PollenbrightWingsAbility copy() {
        return new PollenbrightWingsAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.isCombatDamage() && p != null && p.getAttachments().contains(this.getSourceId())) {
                game.getState().setValue(new StringBuilder("Damage_").append(getSourceId().toString()).toString(), new Integer(damageEvent.getAmount()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever enchanted creature deals combat damage to a player, put that many 1/1 green Saproling creature tokens onto the battlefield.";
    }
}

class PollenbrightWingsEffect extends OneShotEffect<PollenbrightWingsEffect> {

    public PollenbrightWingsEffect() {
        super(Outcome.Benefit);
        this.staticText = "put that many 1/1 green Saproling creature tokens onto the battlefield";
    }

    public PollenbrightWingsEffect(final PollenbrightWingsEffect effect) {
        super(effect);
    }

    @Override
    public PollenbrightWingsEffect copy() {
        return new PollenbrightWingsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer damage = (Integer) game.getState().getValue(new StringBuilder("Damage_").append(source.getSourceId().toString()).toString());
        if (damage != null) {
            return (new CreateTokenEffect(new SaprolingToken(), damage.intValue()).apply(game, source));
        }
        return false;
    }
}
