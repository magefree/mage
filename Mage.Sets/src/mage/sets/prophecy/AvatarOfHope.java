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
package mage.sets.prophecy;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.common.continious.CanBlockAdditionalCreatureEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Plopman
 */
public class AvatarOfHope extends CardImpl<AvatarOfHope> {

    public AvatarOfHope(UUID ownerId) {
        super(ownerId, 3, "Avatar of Hope", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{W}{W}");
        this.expansionSetCode = "PCY";
        this.subtype.add("Avatar");

        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(9);

        // If you have 3 or less life, Avatar of Hope costs {6} less to cast.
        this.addAbility(new AdjustingCostsAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Avatar of Hope can block any number of creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CanBlockAdditionalCreatureEffect(0)));
    }

    public AvatarOfHope(final AvatarOfHope card) {
        super(card);
    }

    @Override
    public AvatarOfHope copy() {
        return new AvatarOfHope(this);
    }
}

class AdjustingCostsAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public AdjustingCostsAbility() {
        super(Constants.Zone.OUTSIDE, new AdjustingCostsEffect());
    }

    public AdjustingCostsAbility(final AdjustingCostsAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AdjustingCostsAbility(this);
    }

    @Override
    public String getRule() {
        return "If you have 3 or less life, Avatar of Hope costs {6} less to cast";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        Player player = game.getPlayer(ability.getControllerId());
        if (player != null && player.getLife() < 4) {
            CardUtil.adjustCost((SpellAbility)ability, 6);
        }
    }
}

class AdjustingCostsEffect extends CostModificationEffectImpl<AdjustingCostsEffect> {

    public AdjustingCostsEffect() {
        super(Constants.Duration.Custom, Constants.Outcome.Benefit);
    }

    public AdjustingCostsEffect(final AdjustingCostsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility)abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        Player player = game.getPlayer(source.getControllerId());
 
        if (mana.getColorless() > 0 && player != null && player.getLife() < 4) {
            int newCount = mana.getColorless() - 6;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setColorless(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility)
                && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public AdjustingCostsEffect copy() {
        return new AdjustingCostsEffect(this);
    }
}
