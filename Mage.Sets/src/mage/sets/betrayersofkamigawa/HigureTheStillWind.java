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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.UnblockableTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class HigureTheStillWind extends CardImpl<HigureTheStillWind> {

    private static final FilterCard filter = new FilterCard("Ninja card");
    private static final FilterCreaturePermanent filterCreature = new FilterCreaturePermanent("Ninja creature");
    static {
        filter.add((new SubtypePredicate("Ninja")));
        filterCreature.add((new SubtypePredicate("Ninja")));
    }

    public HigureTheStillWind(UUID ownerId) {
        super(ownerId, 37, "Higure, the Still Wind", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Human");
        this.subtype.add("Ninja");
        this.supertype.add("Legendary");
        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Ninjutsu {2}{U}{U} ({2}{U}{U}, Return an unblocked attacker you control to hand: Put this card onto the battlefield from your hand tapped and attacking.)
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{2}{U}{U}")));

        // Whenever Higure, the Still Wind deals combat damage to a player, you may search your library for a Ninja card, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter)), true));

        // {2}: Target Ninja creature is unblockable this turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new UnblockableTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCreaturePermanent(filterCreature));
        this.addAbility(ability);


    }

    public HigureTheStillWind(final HigureTheStillWind card) {
        super(card);
    }

    @Override
    public HigureTheStillWind copy() {
        return new HigureTheStillWind(this);
    }
}