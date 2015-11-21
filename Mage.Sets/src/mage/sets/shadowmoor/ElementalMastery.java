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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class ElementalMastery extends CardImpl {

    public ElementalMastery(UUID ownerId) {
        super(ownerId, 90, "Elemental Mastery", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has "{tap}: Put X 1/1 red Elemental creature tokens with haste onto the battlefield, where X is this creature's power. Exile them at the beginning of the next end step."
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ElementalMasteryEffect(), new TapSourceCost());
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ability2, AttachmentType.AURA)));

    }

    public ElementalMastery(final ElementalMastery card) {
        super(card);
    }

    @Override
    public ElementalMastery copy() {
        return new ElementalMastery(this);
    }
}

class ElementalMasteryEffect extends OneShotEffect {

    public ElementalMasteryEffect() {
        super(Outcome.Benefit);
        staticText = "Put X 1/1 red Elemental creature tokens with haste onto the battlefield, where X is this creature's power. Exile them at the beginning of the next end step";
    }

    public ElementalMasteryEffect(final ElementalMasteryEffect effect) {
        super(effect);
    }

    @Override
    public ElementalMasteryEffect copy() {
        return new ElementalMasteryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creatureAttached = game.getPermanent(source.getSourceId());
        if (creatureAttached != null) {
            int power = creatureAttached.getPower().getValue();
            if (power > 0) {
                ElementalToken token = new ElementalToken();
                token.putOntoBattlefield(power, game, creatureAttached.getId(), creatureAttached.getControllerId());
                for (UUID tokenId : token.getLastAddedTokenIds()) {
                    Permanent tokenPermanent = game.getPermanent(tokenId);
                    if (tokenPermanent != null) {
                        ExileTargetEffect exileEffect = new ExileTargetEffect();
                        exileEffect.setTargetPointer(new FixedTarget(tokenPermanent, game));
                        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(exileEffect), source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class ElementalToken extends Token {

    public ElementalToken() {
        super("Elemental", "1/1 red Elemental creature token with haste");
        cardType.add(CardType.CREATURE);
        subtype.add("Elemental");
        color.setRed(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(HasteAbility.getInstance());
    }
}
