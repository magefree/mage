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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.abilities.effects.common.continious.GainAbilityAllEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Plopman
 */
public class MirrorEntity extends CardImpl<MirrorEntity> {

    public MirrorEntity(UUID ownerId) {
        super(ownerId, 31, "Mirror Entity", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.expansionSetCode = "LRW";
        this.subtype.add("Shapeshifter");

        this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Changeling
        this.addAbility(ChangelingAbility.getInstance());
        // {X}: Creatures you control become X/X and gain all creature types until end of turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GainAbilityAllEffect(ChangelingAbility.getInstance(), Constants.Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures you control")),new VariableManaCost());
        ability.addEffect(new SetPowerToughneAllEffect());
        this.addAbility(ability);
    }

    public MirrorEntity(final MirrorEntity card) {
        super(card);
    }

    @Override
    public MirrorEntity copy() {
        return new MirrorEntity(this);
    }
}
class SetPowerToughneAllEffect extends ContinuousEffectImpl<SetPowerToughneAllEffect> {



    public SetPowerToughneAllEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.SetPT_7b, Constants.Outcome.BoostCreature);
    }



    public SetPowerToughneAllEffect(final SetPowerToughneAllEffect effect) {
        super(effect);
    }

    @Override
    public SetPowerToughneAllEffect copy() {
        return new SetPowerToughneAllEffect(this);
    }

        @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        for (Permanent perm: game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
            objects.add(perm.getId());
        }
    }
        
    @Override
    public boolean apply(Game game, Ability source) {
        int value = source.getManaCostsToPay().getX();
        for (Permanent permanent: game.getBattlefield().getActivePermanents(new FilterControlledCreaturePermanent(), source.getControllerId(), source.getSourceId(), game)) {
            if(objects.contains(permanent.getId())){
                permanent.getPower().setValue(value);
                permanent.getToughness().setValue(value);   
            }
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "Creatures you control become X/X until end of turn";
    }


}