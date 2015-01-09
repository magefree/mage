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
package mage.sets.magic2015;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObjectReference;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class PolymorphistsJest extends CardImpl {

    public PolymorphistsJest(UUID ownerId) {
        super(ownerId, 75, "Polymorphist's Jest", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}{U}");
        this.expansionSetCode = "M15";

        this.color.setBlue(true);

        // Until end of turn, each creature target player controls loses all abilities and becomes a blue Frog with base power and toughness 1/1.
        this.getSpellAbility().addEffect(new PolymorphistsJestEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());

    }

    public PolymorphistsJest(final PolymorphistsJest card) {
        super(card);
    }

    @Override
    public PolymorphistsJest copy() {
        return new PolymorphistsJest(this);
    }
}

class PolymorphistsJestEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public PolymorphistsJestEffect() {
        super(Duration.EndOfTurn, Outcome.BecomeCreature);
        staticText = "Until end of turn, each creature target player controls loses all abilities and becomes a blue Frog with base power and toughness 1/1";
    }

    public PolymorphistsJestEffect(final PolymorphistsJestEffect effect) {
        super(effect);
    }

    @Override
    public PolymorphistsJestEffect copy() {
        return new PolymorphistsJestEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (this.affectedObjectsSet) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, getTargetPointer().getFirst(game, source), game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext();) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            permanent.getSubtype().clear();
                            permanent.getSubtype().add("Frog");
                        }

                        break;
                    case ColorChangingEffects_5:
                        if (sublayer == SubLayer.NA) {
                            permanent.getColor().setBlack(false);
                            permanent.getColor().setGreen(false);
                            permanent.getColor().setBlue(false);
                            permanent.getColor().setWhite(false);
                            permanent.getColor().setBlack(false);
                            permanent.getColor().setColor(ObjectColor.BLUE);
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        break;
                    case PTChangingEffects_7:
                        if (sublayer == SubLayer.SetPT_7b) {
                            permanent.getPower().setValue(1);
                            permanent.getToughness().setValue(1);
                        }
                }
            } else {
                it.remove();
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.ColorChangingEffects_5 || layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

}
