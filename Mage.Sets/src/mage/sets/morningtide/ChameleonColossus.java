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
package mage.sets.morningtide;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class ChameleonColossus extends CardImpl<ChameleonColossus> {

    private final static FilterCard filter = new FilterCard("black");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public ChameleonColossus(UUID ownerId) {
        super(ownerId, 116, "Chameleon Colossus", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Shapeshifter");
        this.color.setGreen(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(ChangelingAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ChameleonColossusEffect(), new ManaCostsImpl("{2}{G}{G}")));
    }

    public ChameleonColossus(final ChameleonColossus card) {
        super(card);
    }

    @Override
    public ChameleonColossus copy() {
        return new ChameleonColossus(this);
    }
}

class ChameleonColossusEffect extends ContinuousEffectImpl<ChameleonColossusEffect> {
    public ChameleonColossusEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Layer.PTChangingEffects_7, Constants.SubLayer.ModifyPT_7c, Constants.Outcome.BoostCreature);
        staticText = "{this} gets +X/+X until end of turn, where X is its power";
    }

    public ChameleonColossusEffect(final ChameleonColossusEffect effect) {
        super(effect);
    }

    @Override
    public ChameleonColossusEffect copy() {
        return new ChameleonColossusEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            int power = sourcePermanent.getPower().getValue();
            sourcePermanent.addPower(power);
            sourcePermanent.addToughness(power);
            return true;
        }
        return false;
    }
}
