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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki
 */
public class HoardSmelterDragon extends CardImpl<HoardSmelterDragon> {
    private static FilterPermanent filter = new FilterPermanent("artifact");

    static {
        filter.getCardType().add(CardType.ARTIFACT);
    }

    public HoardSmelterDragon (UUID ownerId) {
        super(ownerId, 93, "Hoard-Smelter Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Dragon");
        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{3}{R}"));
        ability.addTarget(new TargetPermanent(filter));
        ability.addEffect(new HoardSmelterEffect());
        this.addAbility(ability);
    }

    public HoardSmelterDragon (final HoardSmelterDragon card) {
        super(card);
    }

    @Override
    public HoardSmelterDragon copy() {
        return new HoardSmelterDragon(this);
    }
}

class HoardSmelterEffect extends ContinuousEffectImpl<HoardSmelterEffect> {
    private int costValue = 0;

    HoardSmelterEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.ModifyPT_7c, Constants.Outcome.BoostCreature);
        staticText = "{this} gets +X/+0 until end of turn, where X is that artifact's converted mana cost";
    }

    HoardSmelterEffect(final HoardSmelterEffect effect) {
        super(effect);
        this.costValue = effect.costValue;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getSourceId());
        if (target != null) {
            target.addPower(costValue);
            return true;
        }
        return false;
    }

    @Override
    public void init(Ability source, Game game) {
        Card targeted = game.getCard(source.getFirstTarget());
        if (targeted != null) {
            costValue = targeted.getManaCost().convertedManaCost();
        }
    }

    @Override
    public HoardSmelterEffect copy() {
        return new HoardSmelterEffect(this);
    }

}