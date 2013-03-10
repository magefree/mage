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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.Mana;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 * @author Loki
 */
public class SachiDaughterOfSeshiro extends CardImpl<SachiDaughterOfSeshiro> {


    private static final FilterCreaturePermanent snakeFilter = new FilterCreaturePermanent("Snakes");
    private static final FilterCreaturePermanent shamanFilter = new FilterCreaturePermanent("Shamans");

    static {
        snakeFilter.add(new SubtypePredicate("Snake"));
        shamanFilter.add(new SubtypePredicate("Shaman"));
    }

    public SachiDaughterOfSeshiro(UUID ownerId) {
        super(ownerId, 238, "Sachi, Daughter of Seshiro", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Snake");
        this.subtype.add("Shaman");
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        // Other Snake creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Constants.Duration.WhileOnBattlefield, snakeFilter, true)));
        // Shamans you control have "{T}: Add {G}{G} to your mana pool."
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new GainAbilityControlledEffect(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BasicManaEffect(new Mana(0, 2, 0, 0, 0, 0, 0)), new TapSourceCost()), Constants.Duration.WhileOnBattlefield, shamanFilter, false)));
    }

    public SachiDaughterOfSeshiro(final SachiDaughterOfSeshiro card) {
        super(card);
    }

    @Override
    public SachiDaughterOfSeshiro copy() {
        return new SachiDaughterOfSeshiro(this);
    }

}
