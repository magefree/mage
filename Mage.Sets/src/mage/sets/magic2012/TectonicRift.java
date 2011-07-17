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
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author North
 */
public class TectonicRift extends CardImpl<TectonicRift> {

    public TectonicRift(UUID ownerId) {
        super(ownerId, 157, "Tectonic Rift", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{3}{R}");
        this.expansionSetCode = "M12";

        this.color.setRed(true);

        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent());
        this.getSpellAbility().addEffect(new TectonicRiftEffect());
    }

    public TectonicRift(final TectonicRift card) {
        super(card);
    }

    @Override
    public TectonicRift copy() {
        return new TectonicRift(this);
    }
}

class TectonicRiftEffect extends RestrictionEffect<TectonicRiftEffect> {

    TectonicRiftEffect() {
        super(Duration.EndOfTurn);
    }

    TectonicRiftEffect(final TectonicRiftEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (!permanent.getAbilities().contains(FlyingAbility.getInstance())) {
            return true;
        }
        return false;
    }

    @Override
    public TectonicRiftEffect copy() {
        return new TectonicRiftEffect(this);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Game game) {
        return false;
    }

    @Override
    public String getText(Ability source) {
        return "Creatures without flying can't block this turn";
    }
}
