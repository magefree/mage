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

package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfDrawTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.continious.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class RighteousAuthority extends CardImpl<RighteousAuthority> {

    public RighteousAuthority (UUID ownerId) {
        super(ownerId, 189, "Righteous Authority", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Aura");
        this.color.setWhite(true);
        this.color.setBlue(true);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 for each card in its controller's hand.
        CardsInEnchantedControllerHandCount boost = new CardsInEnchantedControllerHandCount();
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(boost, boost, Duration.WhileOnBattlefield)));

        // At the beginning of the draw step of enchanted creature's controller, that player draws an additional card.
        this.addAbility(new BeginningOfDrawTriggeredAbility(new DrawCardTargetEffect(1), TargetController.CONTROLLER_ATTACHED_TO, false));
    }

    public RighteousAuthority (final RighteousAuthority card) {
        super(card);
    }

    @Override
    public RighteousAuthority copy() {
        return new RighteousAuthority(this);
    }
}


class CardsInEnchantedControllerHandCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility) {
        if (sourceAbility != null) {
            Permanent attachment = game.getPermanent(sourceAbility.getSourceId());
            if (attachment != null && attachment.getAttachedTo() != null) {
                Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
                if (attachedTo != null) {
                    Player controller = game.getPlayer(attachedTo.getControllerId());
                    if (controller != null) {
                        return controller.getHand().size();
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new mage.abilities.dynamicvalue.common.CardsInControllerHandCount();
    }

    @Override
    public String getMessage() {
        return "card in its controller's hand";
    }

    @Override
    public String toString() {
        return "1";
    }
}