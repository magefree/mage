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
package mage.sets.commander2014;

import java.util.HashSet;
import java.util.Iterator;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterBasicLandCard;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public class MyriadLandscape extends CardImpl {

    private static final FilterBasicLandCard filter = new FilterBasicLandCard();

    public MyriadLandscape(UUID ownerId) {
        super(ownerId, 61, "Myriad Landscape", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "C14";

        // Myriad Landscape enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {tap}: Add {C} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {tap}, Sacrifice Myriad Landscape: Search your library for up to two basic land cards that share a land type, put them onto the battlefield tapped, then shuffle your library.
        Effect effect = new SearchLibraryPutInPlayEffect(new TargetCardInLibrarySharingLandType(0, 2, filter), true);
        effect.setText("Search your library for up to two basic land cards that share a land type, put them onto the battlefield tapped, then shuffle your library");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);


    }

    public MyriadLandscape(final MyriadLandscape card) {
        super(card);
    }

    @Override
    public MyriadLandscape copy() {
        return new MyriadLandscape(this);
    }
}

class TargetCardInLibrarySharingLandType extends TargetCardInLibrary {

    public TargetCardInLibrarySharingLandType(int minNumTargets, int maxNumTargets, FilterCard filter) {
        super(minNumTargets, maxNumTargets, filter);
    }

    public TargetCardInLibrarySharingLandType(final TargetCardInLibrarySharingLandType target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        if (super.canTarget(id, cards, game)) {
            if (!getTargets().isEmpty()) {
                // check if new target shares a Land Type
                HashSet<String> landTypes = null;
                for (UUID landId: getTargets()) {
                    Card landCard = game.getCard(landId);
                    if (landCard != null) {
                        if (landTypes == null) {
                            landTypes = new HashSet<>();
                            landTypes.addAll(landCard.getSubtype());
                        } else {
                            for (Iterator<String> iterator = landTypes.iterator(); iterator.hasNext();) {
                                String next = iterator.next();
                                if (!landCard.getSubtype().contains(next)) {
                                    iterator.remove();
                                }
                            }
                        }
                    }
                }
                Card card = game.getCard(id);
                if (card != null && landTypes != null) {
                    for (Iterator<String> iterator = landTypes.iterator(); iterator.hasNext();) {
                        String next = iterator.next();
                        if (card.getSubtype().contains(next)) {
                            return true;
                        }
                    }
                }
            } else {
                // first target
                return true;
            }
        }
        return false;
    }
   

    @Override
    public TargetCardInLibrarySharingLandType copy() {
        return new TargetCardInLibrarySharingLandType(this);
    }

}
