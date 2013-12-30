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

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class AvatarOfFury extends CardImpl<AvatarOfFury> {

    public AvatarOfFury(UUID ownerId) {
        super(ownerId, 82, "Avatar of Fury", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{6}{R}{R}");
        this.expansionSetCode = "PCY";
        this.subtype.add("Avatar");

        this.color.setRed(true);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // If an opponent controls seven or more lands, Avatar of Fury costs {6} less to cast.
         this.addAbility(new AvatarOfFuryAdjustingCostsAbility());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {R}: Avatar of Fury gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(1,0, Duration.EndOfTurn), new ManaCostsImpl("{R}")));
    }

    public AvatarOfFury(final AvatarOfFury card) {
        super(card);
    }

    @Override
    public AvatarOfFury copy() {
        return new AvatarOfFury(this);
    }
}

class AvatarOfFuryAdjustingCostsAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public AvatarOfFuryAdjustingCostsAbility() {
        super(Zone.OUTSIDE, null /*new AvatarOfFuryAdjustingCostsEffect()*/);
    }

    public AvatarOfFuryAdjustingCostsAbility(final AvatarOfFuryAdjustingCostsAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new AvatarOfFuryAdjustingCostsAbility(this);
    }

    @Override
    public String getRule() {
        return "If an opponent controls seven or more lands, Avatar of Fury costs {6} less to cast";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        FilterPermanent filter = new FilterLandPermanent();
        for (UUID playerId: game.getOpponents(ability.getControllerId())) {
            if (game.getBattlefield().countAll(filter, playerId, game) > 6) {
                CardUtil.adjustCost((SpellAbility)ability, 6);
                break;
            }
        }
    }
}

//class AvatarOfFuryAdjustingCostsEffect extends CostModificationEffectImpl<AvatarOfFuryAdjustingCostsEffect> {
//
//    public AvatarOfFuryAdjustingCostsEffect() {
//        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
//    }
//
//    public AvatarOfFuryAdjustingCostsEffect(final AvatarOfFuryAdjustingCostsEffect effect) {
//        super(effect);
//    }
//
//    @Override
//    public boolean apply(Game game, Ability source, Ability abilityToModify) {
//        SpellAbility spellAbility = (SpellAbility)abilityToModify;
//        Mana mana = spellAbility.getManaCostsToPay().getMana();
//
//        boolean condition = false;
//        FilterPermanent filter = new FilterLandPermanent();
//        for (UUID playerId: game.getOpponents(source.getControllerId())) {
//            if (game.getBattlefield().countAll(filter, playerId, game) > 6) {
//                condition = true;
//                break;
//            }
//        }
//
//        if (mana.getColorless() > 0 && condition) {
//            int newCount = mana.getColorless() - 6;
//            if (newCount < 0) {
//                newCount = 0;
//            }
//            mana.setColorless(newCount);
//            spellAbility.getManaCostsToPay().load(mana.toString());
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public boolean applies(Ability abilityToModify, Ability source, Game game) {
//        if ((abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility)
//                && abilityToModify.getSourceId().equals(source.getSourceId())) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public AvatarOfFuryAdjustingCostsEffect copy() {
//        return new AvatarOfFuryAdjustingCostsEffect(this);
//    }
//}
