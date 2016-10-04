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
package mage.sets.fallenempires;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.AssignNoCombatDamageSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.game.permanent.Permanent;

/**
 *
 * @author MarcoMarin
 */
public class FarrelsMantle extends CardImpl {

    
    public FarrelsMantle(UUID ownerId) {
        super(ownerId, 138, "Farrel's Mantle", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "FEM";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Whenever enchanted creature attacks and isn't blocked, its controller may have it deal damage equal to its power plus 2 to another target creature. If that player does, the attacking creature assigns no combat damage this turn.
        FilterPermanent filter = new FilterCreaturePermanent();
        filter.add(Predicates.not(new AttachmentByUUIDPredicate(this.getId()))); 
        
        Ability ability2 = new AttacksAndIsNotBlockedTriggeredAbility(new FarrelsMantleEffect(), true);
        ability2.addTarget(new TargetPermanent(filter));
        ability2.addEffect(new AssignNoCombatDamageSourceEffect(Duration.EndOfTurn));
        
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.AURA)));
        
    }

    public FarrelsMantle(final FarrelsMantle card) {
        super(card);
    }

    @Override
    public FarrelsMantle copy() {
        return new FarrelsMantle(this);
    }
}
class AttachmentByUUIDPredicate implements Predicate<Permanent> {

    private final UUID id;

    public AttachmentByUUIDPredicate(UUID id) {
        this.id = id;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments().contains(id);            
    }

    @Override
    public String toString() {
        return "AttachmentUUID(" + id + ')';
    }
}

class FarrelsMantleEffect extends OneShotEffect{
                      
    public FarrelsMantleEffect() {
        super(Outcome.Damage);
        this.setText("its controller may have it deal damage equal to its power plus 2 to another target creature.");
    }
    
    public FarrelsMantleEffect(final FarrelsMantleEffect effect) {
        super(effect);
    }
    
    @Override
    public FarrelsMantleEffect copy() {
        return new FarrelsMantleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent perm = game.getPermanent(source.getSourceId());
        DamageTargetEffect dmgEffect = new DamageTargetEffect(2+perm.getPower().getValue());
        return dmgEffect.apply(game, source);        
    }
}
