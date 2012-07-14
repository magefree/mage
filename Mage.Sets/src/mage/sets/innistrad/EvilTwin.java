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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward
 */
public class EvilTwin extends CardImpl<EvilTwin> {

    public EvilTwin(UUID ownerId) {
        super(ownerId, 212, "Evil Twin", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Shapeshifter");

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Evil Twin enter the battlefield as a copy of any creature on the battlefield except it gains "{U}{B}, {T}: Destroy target creature with the same name as this creature.
        Ability ability1 = new EntersBattlefieldAbility(new EntersBattlefieldEffect(new CopyPermanentEffect()), "You may have {this} enter the battlefield as a copy of any creature on the battlefield except it gains {U}{B}, {T}: Destroy target creature with the same name as this creature");
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DestroyTargetEffect(), new ManaCostsImpl("{U}{B}"));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent(new EvilTwinFilter()));
        ability1.addEffect(new GainAbilitySourceEffect(ability2));
        this.addAbility(ability1);
    }

    public EvilTwin(final EvilTwin card) {
        super(card);
    }

    @Override
    public EvilTwin copy() {
        return new EvilTwin(this);
    }
}

class EvilTwinFilter extends FilterCreaturePermanent {

    public EvilTwinFilter() {
        super("creature with the same name as this creature");
    }

    @Override
    public boolean match(Permanent permanent, UUID sourceId, UUID playerId, Game game) {
        if (!super.match(permanent, game))
            return notFilter;

        Permanent twin = game.getPermanent(sourceId);
        if (twin != null) {
            if (!permanent.getName().equals(twin.getName()))
                return false;
        }

        return !notFilter;
    }

}
