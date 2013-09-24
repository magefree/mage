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
package mage.sets.darksteel;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

/**
 *
 * @author Plopman
 */
public class Trinisphere extends CardImpl<Trinisphere> {

    public Trinisphere(UUID ownerId) {
        super(ownerId, 154, "Trinisphere", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "DST";

        // As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TrinisphereEffect()));
    }

    public Trinisphere(final Trinisphere card) {
        super(card);
    }

    @Override
    public Trinisphere copy() {
        return new Trinisphere(this);
    }
}

class TrinisphereEffect extends CostModificationEffectImpl<TrinisphereEffect> {


    public TrinisphereEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, CostModificationType.SET_COST);
        this.staticText = "As long as Trinisphere is untapped, each spell that would cost less than three mana to cast costs three mana to cast";
    }

    protected TrinisphereEffect(TrinisphereEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int manaCost = abilityToModify.getManaCostsToPay().convertedManaCost();
        if(manaCost < 3){
            CardUtil.increaseCost(abilityToModify, 3 - manaCost);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility)) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if(permanent != null && !permanent.isTapped())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public TrinisphereEffect copy() {
        return new TrinisphereEffect(this);
    }
}
