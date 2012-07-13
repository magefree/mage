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
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.effects.common.continious.SetCardColorTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetSpellOrPermanent;

/**
 *
 * @author LevelX
 */
public class EightAndAHalfTale extends CardImpl<EightAndAHalfTale> {

    private final static FilterCard filter = new FilterCard("white");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public EightAndAHalfTale(UUID ownerId) {
        super(ownerId, 8, "Eight-and-a-Half-Tails", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Fox");
        this.subtype.add("Cleric");
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        // {1}{W}: Target permanent you control gains protection from white until end of turn.
        Ability ability1 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(
                new ProtectionAbility(filter), Constants.Duration.EndOfTurn), new ManaCostsImpl("{1}{W}"));
        ability1.addTarget(new TargetControlledPermanent());
        this.addAbility(ability1);
        // {1}: Target spell or permanent becomes white until end of turn.
        Ability ability2 = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new SetCardColorTargetEffect(
                ObjectColor.WHITE, Constants.Duration.EndOfTurn),new ManaCostsImpl("{1}"));
        ability2.addTarget(new TargetSpellOrPermanent());
        this.addAbility(ability2);

    }

    public EightAndAHalfTale(final EightAndAHalfTale card) {
        super(card);
    }

    @Override
    public EightAndAHalfTale copy() {
        return new EightAndAHalfTale(this);
    }
}
