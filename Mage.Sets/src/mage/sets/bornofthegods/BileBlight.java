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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class BileBlight extends CardImpl {

    public BileBlight(UUID ownerId) {
        super(ownerId, 61, "Bile Blight", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}{B}");
        this.expansionSetCode = "BNG";

        this.color.setBlack(true);

        // Target creature and all creatures with the same name as that creature get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BileBlightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public BileBlight(final BileBlight card) {
        super(card);
    }

    @Override
    public BileBlight copy() {
        return new BileBlight(this);
    }
}

class BileBlightEffect extends BoostAllEffect {

    public BileBlightEffect() {
        super(-3, -3, Duration.EndOfTurn);
        staticText = "Target creature and all creatures with the same name as that creature get -3/-3 until end of turn";
    }

    public BileBlightEffect(final BileBlightEffect effect) {
        super(effect);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.clear();
        if (this.affectedObjectsSet) {
            Permanent target = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (target != null) {
                if (target.getName().isEmpty()) { // face down creature
                    affectedObjectList.add(new MageObjectReference(target, game));
                } else {
                    String name = target.getLogName();
                    for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                        if (perm.getLogName().equals(name)) {
                            affectedObjectList.add(new MageObjectReference(perm, game));
                        }
                    }
                }
            }
        }
    }

    @Override
    public BileBlightEffect copy() {
        return new BileBlightEffect(this);
    }
}
