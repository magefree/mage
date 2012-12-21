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

package mage.sets.mirrodinbesieged;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.functions.ApplyToPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public class Cryptoplasm extends CardImpl<Cryptoplasm> {
    final static FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AnotherPredicate());
    }

    public Cryptoplasm(UUID ownerId) {
        super(ownerId, 23, "Cryptoplasm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Shapeshifter");
        this.color.setBlue(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your upkeep, you may have Cryptoplasm become a copy of another target creature. If you do, Cryptoplasm gains this ability.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new CryptoplasmTransformEffect(), Constants.TargetController.YOU, true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public Cryptoplasm(final Cryptoplasm card) {
        super(card);
    }

    @Override
    public Cryptoplasm copy() {
        return new Cryptoplasm(this);
    }

}

class CryptoplasmTransformEffect extends ContinuousEffectImpl<CryptoplasmTransformEffect> {

    CryptoplasmTransformEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Layer.CopyEffects_1, Constants.SubLayer.NA, Constants.Outcome.Copy);
        staticText = "you may have {this} become a copy of another target creature. If you do, {this} gains this ability";
    }

    CryptoplasmTransformEffect(final CryptoplasmTransformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, final Ability source) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (creature == null || permanent == null) {
            return false;
        }

        game.copyPermanent(creature, permanent, source, new ApplyToPermanent() {
            @Override
            public Boolean apply(Game game, Permanent permanent) {
                Ability upkeepAbility = new BeginningOfUpkeepTriggeredAbility(new CryptoplasmTransformEffect(), Constants.TargetController.YOU, true);
                upkeepAbility.addTarget(new TargetCreaturePermanent());
                permanent.addAbility(upkeepAbility, source.getSourceId(), game);
                return true;
            }
        });


        return true;
    }

    @Override
    public CryptoplasmTransformEffect copy() {
        return new CryptoplasmTransformEffect(this);
    }

}
