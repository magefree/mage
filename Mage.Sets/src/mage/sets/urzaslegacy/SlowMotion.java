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
package mage.sets.urzaslegacy;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public class SlowMotion extends CardImpl<SlowMotion> {

    public SlowMotion(UUID ownerId) {
        super(ownerId, 42, "Slow Motion", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "ULG";
        this.subtype.add("Aura");

        this.color.setBlue(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Constants.Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // At the beginning of the upkeep of enchanted creature's controller, that player sacrifices that creature unless he or she pays {2}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEquipedUnlessPaysEffect(new GenericManaCost(2)), Constants.TargetController.CONTROLLER_ATTACHED_TO, false ));

        // When Slow Motion is put into a graveyard from the battlefield, return Slow Motion to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    public SlowMotion(final SlowMotion card) {
        super(card);
    }

    @Override
    public SlowMotion copy() {
        return new SlowMotion(this);
    }
}

class SacrificeEquipedUnlessPaysEffect extends OneShotEffect<SacrificeEquipedUnlessPaysEffect> {
    protected Cost cost;

    public SacrificeEquipedUnlessPaysEffect(Cost cost) {
        super(Constants.Outcome.Sacrifice);
        this.cost = cost;
        staticText = "that player sacrifices that creature unless he or she pays {2}";
     }

    public SacrificeEquipedUnlessPaysEffect(final SacrificeEquipedUnlessPaysEffect effect) {
        super(effect);
        this.cost = effect.cost;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            Player player = game.getPlayer(equipped.getControllerId());
            if (player != null && equipped != null) {
                if (player.chooseUse(Constants.Outcome.Benefit, "Pay " + cost.getText() + "? (Or " + equipped.getName() + " will be sacrificed.)", game)) {
                    cost.clearPaid();
                    if (cost.pay(source, game, source.getSourceId(), equipped.getControllerId(), false)) {
                        return true;
                    }
                }
                equipped.sacrifice(source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public SacrificeEquipedUnlessPaysEffect copy() {
        return new SacrificeEquipedUnlessPaysEffect(this);
    }
 }