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
package mage.sets.magic2012;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.functions.ApplyToPermanent;

/**
 *
 * @author North
 */
public class PhantasmalImage extends CardImpl {

    private static final String effectText = "a copy of any creature on the battlefield, except it's an Illusion in addition to its other types and it gains \"When this creature becomes the target of a spell or ability, sacrifice it.\"";

    ApplyToPermanent phantasmalImageApplier = new ApplyToPermanent() {
        @Override
        public Boolean apply(Game game, Permanent permanent) {
            if (!permanent.getSubtype().contains("Illusion")) {
                permanent.getSubtype().add("Illusion");
            }
            // Add directly because the created permanent is only used to copy from, so there is no need to add the ability to e.g. TriggeredAbilities
            permanent.getAbilities().add(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
            //permanent.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()), game);
            return true;
        }

        @Override
        public Boolean apply(Game game, MageObject mageObject) {
            if (!mageObject.getSubtype().contains("Illusion")) {
                mageObject.getSubtype().add("Illusion");
            }
            // Add directly because the created permanent is only used to copy from, so there is no need to add the ability to e.g. TriggeredAbilities
            mageObject.getAbilities().add(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()));
            //permanent.addAbility(new BecomesTargetTriggeredAbility(new SacrificeSourceEffect()), game);
            return true;
        }
    };

    public PhantasmalImage(UUID ownerId) {
        super(ownerId, 72, "Phantasmal Image", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "M12";
        this.subtype.add("Illusion");

        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Phantasmal Image enter the battlefield as a copy of any creature
        // on the battlefield, except it's an Illusion in addition to its other types and
        // it gains "When this creature becomes the target of a spell or ability, sacrifice it."
        Effect effect = new CopyPermanentEffect(new FilterCreaturePermanent(), phantasmalImageApplier);
        effect.setText(effectText);
        this.addAbility(new EntersBattlefieldAbility(effect, true));
    }

    public PhantasmalImage(final PhantasmalImage card) {
        super(card);
    }

    @Override
    public PhantasmalImage copy() {
        return new PhantasmalImage(this);
    }
}
