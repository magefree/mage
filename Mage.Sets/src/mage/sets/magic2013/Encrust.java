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
package mage.sets.magic2013;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SkipEnchantedUntapEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public class Encrust extends CardImpl<Encrust> {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");
    
    static {
        filter.add(Predicates.or(
        new CardTypePredicate(Constants.CardType.CREATURE),
        new CardTypePredicate(Constants.CardType.ARTIFACT)));
    }

    public Encrust(UUID ownerId) {
        super(ownerId, 49, "Encrust", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");
        this.expansionSetCode = "M13";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant artifact or creature
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted permanent doesn't untap during its controller's untap step and its activated abilities can't be activated.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new SkipEnchantedUntapEffect()));
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EncrustEffect()));
    }

    public Encrust(final Encrust card) {
        super(card);
    }

    @Override
    public Encrust copy() {
        return new Encrust(this);
    }
}

class EncrustEffect extends ReplacementEffectImpl<EncrustEffect> {

    public EncrustEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Detriment);
        staticText = "Its activated abilities can't be activated";
    }

    public EncrustEffect(final EncrustEffect effect) {
        super(effect);
    }

    @Override
    public EncrustEffect copy() {
        return new EncrustEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATE_ABILITY) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
            if (event.getSourceId().equals(enchanted.getId())) {
                return true;
            }
        }
        return false;
    }
}

