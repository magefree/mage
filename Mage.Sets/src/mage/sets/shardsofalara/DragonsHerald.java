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
package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public class DragonsHerald extends CardImpl<DragonsHerald> {

    private static final FilterCard filter = new FilterCard("card named Hellkite Overlord");
    private static final FilterControlledCreaturePermanent filterBlack = new FilterControlledCreaturePermanent("a black creature");
    private static final FilterControlledCreaturePermanent filterRed = new FilterControlledCreaturePermanent("a red creature");
    private static final FilterControlledCreaturePermanent filterGreen = new FilterControlledCreaturePermanent("a green creature");

    static {
        filter.add(new NamePredicate("Hellkite Overlord"));
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public DragonsHerald(UUID ownerId) {
        super(ownerId, 98, "Dragon's Herald", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Goblin");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{R}, {tap}, Sacrifice a black creature, a red creature, and a green creature:
        // Search your library for a card named Hellkite Overlord and put it onto the battlefield. Then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, new FilterCard(filter));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new SearchLibraryPutInPlayEffect(target),
                new ManaCostsImpl("{2}{R}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterBlack, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterRed, false)));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filterGreen, false)));
        this.addAbility(ability);
    }

    public DragonsHerald(final DragonsHerald card) {
        super(card);
    }

    @Override
    public DragonsHerald copy() {
        return new DragonsHerald(this);
    }
}
