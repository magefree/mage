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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DetainTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author LevelX2
 */
public class NewPrahvGuildmage extends CardImpl<NewPrahvGuildmage> {

    private static final FilterNonlandPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent control");
 
    static {
        filter.add(new ControllerPredicate(Constants.TargetController.OPPONENT));
    }
    
    public NewPrahvGuildmage(UUID ownerId) {
        super(ownerId, 181, "New Prahv Guildmage", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}{U}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        
        this.color.setBlue(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {W}{U}: Target creature gains flying until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{W}{U}"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
        // {3}{W}{U}: Detain target nonland permanent an opponent controls. 
        // (Until your next turn, that permanent can't attack or block and its activated abilities can't be activated.)
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DetainTargetEffect(), new ManaCostsImpl("{3}{W}{U}"));
        TargetNonlandPermanent target = new TargetNonlandPermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    public NewPrahvGuildmage(final NewPrahvGuildmage card) {
        super(card);
    }

    @Override
    public NewPrahvGuildmage copy() {
        return new NewPrahvGuildmage(this);
    }
}
