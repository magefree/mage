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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.keyword.MonstrosityAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author LevelX2
 */
public class SealockMonster extends CardImpl {

    public SealockMonster(UUID ownerId) {
        super(ownerId, 62, "Sealock Monster", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "THS";
        this.subtype.add("Octopus");

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Sealock Monster can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent("Island","an Island"))));
        // {5}{U}{U}: Monstrosity 3.</i>
        this.addAbility(new MonstrosityAbility("{5}{U}{U}",3));
        // When Sealock Monster becomes monstrous, target land becomes an island in addition to its other types.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new SealockMonsterBecomesIslandTargetEffect());
        Target target = new TargetLandPermanent();
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public SealockMonster(final SealockMonster card) {
        super(card);
    }

    @Override
    public SealockMonster copy() {
        return new SealockMonster(this);
    }
}

class SealockMonsterBecomesIslandTargetEffect extends ContinuousEffectImpl {

    private static Ability islandAbility = new BlueManaAbility();

    public SealockMonsterBecomesIslandTargetEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        this.staticText = "target land becomes an island in addition to its other types";

    }

    public SealockMonsterBecomesIslandTargetEffect(final SealockMonsterBecomesIslandTargetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public SealockMonsterBecomesIslandTargetEffect copy() {
        return new SealockMonsterBecomesIslandTargetEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (UUID targetPermanent : targetPointer.getTargets(game, source)) {
            Permanent land = game.getPermanent(targetPermanent);
            if (land != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        if (!land.getAbilities().containsRule(islandAbility)) {
                            land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                        }
                        break;
                    case TypeChangingEffects_4:
                        if (!land.getSubtype().contains("Island")) {
                            land.getSubtype().add("Island");
                        }
                        break;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

}
