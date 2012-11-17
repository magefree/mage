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
 *  CONTRIBUTORS BE LIAB8LE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UnblockableTargetEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class MinamoSightbender extends CardImpl<MinamoSightbender> {

    public MinamoSightbender(UUID ownerId) {
        super(ownerId, 41, "Minamo Sightbender", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {X}, {T}: Target creature with power X or less is unblockable this turn.
        this.addAbility(new MinamoSightbenderAbility());
    }

    public MinamoSightbender(final MinamoSightbender card) {
        super(card);
    }

    @Override
    public MinamoSightbender copy() {
        return new MinamoSightbender(this);
    }
}


class MinamoSightbenderAbility extends ActivatedAbilityImpl<MinamoSightbenderAbility> {

    public MinamoSightbenderAbility() {
        super(Constants.Zone.BATTLEFIELD,new UnblockableTargetEffect(), new ManaCostsImpl("{X}"));
        this.addCost(new TapSourceCost());
    }

    public MinamoSightbenderAbility(MinamoSightbenderAbility ability) {
        super(ability);
    }

    @Override
    public MinamoSightbenderAbility copy() {
        return new MinamoSightbenderAbility(this);
    }

    @Override
    public boolean resolve(Game game) {
        int manaX = this.getManaCostsToPay().getX();
        FilterPermanent filter = new FilterCreaturePermanent("creature with power " + manaX + " or less");
        filter.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, manaX + 1));
        Target target = new TargetPermanent(filter);
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            if (player.chooseTarget(Constants.Outcome.Benefit, target, this, game)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(target.getFirstTarget()));
                return super.resolve(game);
            }
        }
        return false;
    }

}