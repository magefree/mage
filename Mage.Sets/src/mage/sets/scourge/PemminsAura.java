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
package mage.sets.scourge;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.UntapEnchantedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class PemminsAura extends CardImpl {

    public PemminsAura(UUID ownerId) {
        super(ownerId, 45, "Pemmin's Aura", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");
        this.expansionSetCode = "SCG";
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // {U}: Untap enchanted creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new UntapEnchantedEffect(), new ManaCostsImpl("{U}")));
        
        // {U}: Enchanted creature gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(FlyingAbility.getInstance(),
            AttachmentType.AURA, Duration.EndOfTurn), new ManaCostsImpl("{U}")));
        
        // {U}: Enchanted creature gains shroud until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(ShroudAbility.getInstance(),
            AttachmentType.AURA, Duration.EndOfTurn), new ManaCostsImpl("{U}")));
        
        // {1}: Enchanted creature gets +1/-1 or -1/+1 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PemminsAuraBoostEnchantedEffect(), new ManaCostsImpl("U")));
    }

    public PemminsAura(final PemminsAura card) {
        super(card);
    }

    @Override
    public PemminsAura copy() {
        return new PemminsAura(this);
    }
}

class PemminsAuraBoostEnchantedEffect extends OneShotEffect {

    private static String CHOICE_1 = "+1/-1";
    private static String CHOICE_2 = "-1/+1";

    public PemminsAuraBoostEnchantedEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Enchanted creature gets +1/-1 or -1/+1 until end of turn";
    }

    public PemminsAuraBoostEnchantedEffect(final PemminsAuraBoostEnchantedEffect effect) {
        super(effect);
    }

    @Override
    public PemminsAuraBoostEnchantedEffect copy() {
        return new PemminsAuraBoostEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent enchantment = game.getPermanent(source.getSourceId());
        Permanent creature = game.getPermanent(enchantment.getAttachedTo());
        if (controller != null && creature != null) {            
            Choice choice = new ChoiceImpl(true);
            choice.setMessage("Select how to boost");
            choice.getChoices().add(CHOICE_1);
            choice.getChoices().add(CHOICE_2);
            while (!choice.isChosen()) {
                if (!controller.canRespond()) {
                    return false;
                }
                controller.choose(outcome, choice, game);
            }
            if (choice.getChoice().equals(CHOICE_1)) {
                game.addEffect(new BoostEnchantedEffect(+1, -1, Duration.EndOfTurn), source);
            } else {
                game.addEffect(new BoostEnchantedEffect(-1, +1, Duration.EndOfTurn), source);
            }
            return true;
        }
        return false;
    }
}