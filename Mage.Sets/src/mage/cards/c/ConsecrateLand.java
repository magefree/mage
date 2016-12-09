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
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantBeEnchantedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author MTGfan
 */
public class ConsecrateLand extends CardImpl {

    public ConsecrateLand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");
        
        this.subtype.add("Aura");

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted land is indestructible and can't be enchanted by other Auras.
        Ability ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(IndestructibleAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield, "{this} is indestructible and can't be enchanted by other Auras."));
        ability2.addEffect(new CantBeEnchantedSourceEffect());
        ability2.addEffect(new ConsecrateLandEffect());
        this.addAbility(ability);
    }

    public ConsecrateLand(final ConsecrateLand card) {
        super(card);
    }

    @Override
    public ConsecrateLand copy() {
        return new ConsecrateLand(this);
    }
}

// 9/25/2006 ruling: If Consecrate Land enters the battlefield attached to a land that’s enchanted by other Auras, those Auras are put into their owners’ graveyards.

class ConsecrateLandEffect extends ContinuousEffectImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent();

    static {
        filter.add(new SubtypePredicate("Aura"));
    }

    public ConsecrateLandEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    public ConsecrateLandEffect(final ConsecrateLandEffect effect) {
        super(effect);
    }

    @Override
    public ConsecrateLandEffect copy() {
        return new ConsecrateLandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
        	Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        	Permanent enchanted = game.getPermanentOrLKIBattlefield(enchantment.getAttachedTo());
                if (enchanted != null) {
                    Cards removeAuras = new CardsImpl(enchanted.getAttachments());
                    controller.moveCards(removeAuras, Zone.GRAVEYARD, source, game);
                }
            return true;
        }
        return false;
    }
}
