/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
 */
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LoneFox
 */
public class BelzenlokDemonToken extends TokenImpl {

    public BelzenlokDemonToken() {
        super("Demon", "6/6 black Demon creature token with flying, trample, and "
                + "\"At the beginning of your upkeep, sacrifice another creature.  If you can't, this creature deals 6 damage to you.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DEMON);
        power = new MageInt(6);
        toughness = new MageInt(6);
        addAbility(FlyingAbility.getInstance());
        addAbility(TrampleAbility.getInstance());
        addAbility(new BeginningOfUpkeepTriggeredAbility(new BelzenlokDemonTokenEffect(), TargetController.YOU, false));
    }

    public BelzenlokDemonToken(final BelzenlokDemonToken token) {
        super(token);
    }

    @Override
    public BelzenlokDemonToken copy() {
        return new BelzenlokDemonToken(this);
    }

    @Override
    public void setExpansionSetCodeForImage(String code) {
        super.setExpansionSetCodeForImage(code);
        if (getOriginalExpansionSetCode().equals("C14")) {
            this.setTokenType(2);
        }
    }
}

class BelzenlokDemonTokenEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another creature");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new AnotherPredicate());
    }

    BelzenlokDemonTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "sacrifice another creature.  If you can't, this creature deals 6 damage to you.";
    }

    BelzenlokDemonTokenEffect(final BelzenlokDemonTokenEffect effect) {
        super(effect);
    }

    @Override
    public BelzenlokDemonTokenEffect copy() {
        return new BelzenlokDemonTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int otherCreatures = new PermanentsOnBattlefieldCount(filter).calculate(game, source, this);
        if (otherCreatures > 0) {
            new SacrificeControllerEffect(filter, 1, "").apply(game, source);
        } else {
            Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (permanent != null) {
                permanent.damage(6, permanent.getId(), game, false, true);
            }
        }
        return true;
    }
}
