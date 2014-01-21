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
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class BileBlight extends CardImpl<BileBlight> {

    public BileBlight(UUID ownerId) {
        super(ownerId, 61, "Bile Blight", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}{B}");
        this.expansionSetCode = "BNG";

        this.color.setBlack(true);

        // Target creature and all creatures with the same name as that creature get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new BileBlightEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
    }

    public BileBlight(final BileBlight card) {
        super(card);
    }

    @Override
    public BileBlight copy() {
        return new BileBlight(this);
    }
}

class BileBlightEffect extends ContinuousEffectImpl<BileBlightEffect> {

    public BileBlightEffect() {
        super(Duration.EndOfTurn, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.UnboostCreature);
        staticText = "Target creature and all creatures with the same name as that creature get -3/-3 until end of turn";
    }

    public BileBlightEffect(final BileBlightEffect effect) {
        super(effect);
    }

    @Override
    public BileBlightEffect copy() {
        return new BileBlightEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        if (this.affectedObjectsSet) {
            UUID permanentId = targetPointer.getFirst(game, source);
            Permanent target = game.getPermanent(permanentId);
            if (target != null) {
                String name = target.getName();
                for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                    if (perm.getName().equals(name)) {
                        this.objects.add(perm.getId());
                    }
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (!this.affectedObjectsSet || objects.contains(perm.getId())) {
                perm.addPower(-3);
                perm.addToughness(-3);
            }
        }
        return true;
    }
}
