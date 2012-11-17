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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class KaijinOfTheVanishingTouch extends CardImpl<KaijinOfTheVanishingTouch> {

    public KaijinOfTheVanishingTouch(UUID ownerId) {
        super(ownerId, 39, "Kaijin of the Vanishing Touch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Spirit");
        this.color.setBlue(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender (This creature can't attack.)
        this.addAbility(DefenderAbility.getInstance());

        // Whenever Kaijin of the Vanishing Touch blocks a creature, return that creature to its owner's hand at end of combat. (Return it only if it's on the battlefield.)
        Ability ability = new BlocksTriggeredAbility(new KaijinOfTheVanishingTouchEffect(), false, true);
        this.addAbility(ability);

    }

    public KaijinOfTheVanishingTouch(final KaijinOfTheVanishingTouch card) {
        super(card);
    }

    @Override
    public KaijinOfTheVanishingTouch copy() {
        return new KaijinOfTheVanishingTouch(this);
    }
}

class KaijinOfTheVanishingTouchEffect extends OneShotEffect<KaijinOfTheVanishingTouchEffect> {

    KaijinOfTheVanishingTouchEffect() {
        super(Outcome.ReturnToHand);
        staticText = "return that creature to its owner's hand at end of combat";
    }

    KaijinOfTheVanishingTouchEffect(final KaijinOfTheVanishingTouchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetCreature = game.getPermanent(this.getTargetPointer().getFirst(game, source));
        if (targetCreature != null) {
            AtTheEndOfCombatDelayedTriggeredAbility delayedAbility = new AtTheEndOfCombatDelayedTriggeredAbility(new ReturnToHandTargetEffect());
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            delayedAbility.getEffects().get(0).setTargetPointer(new FixedTarget(targetCreature.getId()));
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }

    @Override
    public KaijinOfTheVanishingTouchEffect copy() {
        return new KaijinOfTheVanishingTouchEffect(this);
    }
}