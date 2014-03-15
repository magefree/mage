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

package mage.sets.magic2011;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continious.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class NecroticPlague extends CardImpl<NecroticPlague> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public NecroticPlague(UUID ownerId) {
        super(ownerId, 107, "Necrotic Plague", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");
        this.expansionSetCode = "M11";
        this.color.setBlack(true);
        this.subtype.add("Aura");

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        // Enchanted creature has "At the beginning of your upkeep, sacrifice this creature."
        // When enchanted creature dies, its controller chooses target creature one of his or her opponents controls. Return Necrotic Plague from its owner's graveyard to the battlefield attached to that creature.
        Ability gainedAbility = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU, false);
        Effect effect = new GainAbilityAttachedEffect(gainedAbility, AttachmentType.AURA, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature has \"At the beginning of your upkeep, sacrifice this creature.\"");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        this.addAbility(new DiesAttachedTriggeredAbility(new NecroticPlagueEffect(),"enchanted creature", false));

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof DiesAttachedTriggeredAbility) {
            Permanent attachedTo = null;
            for (Effect effect :ability.getEffects()) {
                attachedTo = (Permanent) effect.getValue("attachedTo");
            }
            if (attachedTo != null) {
                Player creatureController = game.getPlayer(attachedTo.getControllerId());
                if (creatureController != null) {
                    ability.setControllerId(creatureController.getId());
                    ability.getTargets().clear();
                    TargetCreaturePermanent target = new TargetCreaturePermanent(filter);
                    target.setRequired(true);
                    ability.getTargets().add(target);
                }
            }
        }
    }

    public NecroticPlague(final NecroticPlague card) {
        super(card);
    }

    @Override
    public NecroticPlague copy() {
        return new NecroticPlague(this);
    }

}

class NecroticPlagueEffect extends OneShotEffect<NecroticPlagueEffect> {



    public NecroticPlagueEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "its controller chooses target creature one of his or her opponents controls. Return {this} from its owner's graveyard to the battlefield attached to that creature";
    }

    public NecroticPlagueEffect(final NecroticPlagueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent attachedTo = (Permanent) this.getValue("attachedTo");
        if (attachedTo != null) {
            Player creatureController = game.getPlayer(attachedTo.getControllerId());
            if (creatureController != null) {
                Card sourceEnchantmentCard = game.getCard(source.getSourceId());
                Permanent creature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
                if (sourceEnchantmentCard != null && creature != null) {
                    game.getState().setValue("attachTo:" + sourceEnchantmentCard.getId(), creature);
                    creatureController.putOntoBattlefieldWithInfo(sourceEnchantmentCard, game, Zone.GRAVEYARD, source.getSourceId());
                    return creature.addAttachment(sourceEnchantmentCard.getId(), game);
                }
            }
        }
        return false;
    }

    @Override
    public NecroticPlagueEffect copy() {
        return new NecroticPlagueEffect(this);
    }

}
