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
package mage.sets.timespiral;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class SuddenSpoiling extends CardImpl<SuddenSpoiling> {

    public SuddenSpoiling(UUID ownerId) {
        super(ownerId, 135, "Sudden Spoiling", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{B}{B}");
        this.expansionSetCode = "TSP";

        this.color.setBlack(true);

        // Split second
        this.addAbility(SplitSecondAbility.getInstance());
        // Creatures target player controls become 0/2 and lose all abilities until end of turn.
        this.getSpellAbility().addEffect(new SuddenSpoilingEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public SuddenSpoiling(final SuddenSpoiling card) {
        super(card);
    }

    @Override
    public SuddenSpoiling copy() {
        return new SuddenSpoiling(this);
    }
}

class SuddenSpoilingEffect extends ContinuousEffectImpl {

    public SuddenSpoilingEffect(Duration duration) {
        super(duration, Outcome.LoseAbility);
        staticText = "Creatures target player controls become 0/2 and lose all abilities until end of turn";
    }

    public SuddenSpoilingEffect(final SuddenSpoilingEffect effect) {
        super(effect);
    }

    @Override
    public SuddenSpoilingEffect copy() {
        return new SuddenSpoilingEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Permanent perm: game.getState().getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                objects.add(perm.getId());
            }
        }
    }

    @Override
    public boolean apply(Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), player.getId(), game)) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        break;
                    case PTChangingEffects_7:
                        if (sublayer.equals(SubLayer.SetPT_7b)) {
                            if(objects.contains(permanent.getId())){
                                permanent.getPower().setValue(0);
                                permanent.getToughness().setValue(2);
                            }
                        }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Constants.Layer.PTChangingEffects_7;
    }

}
