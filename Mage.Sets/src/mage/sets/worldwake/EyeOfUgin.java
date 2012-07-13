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

package mage.sets.worldwake;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.effects.common.search.SearchLibraryRevealPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 * TODO: Implement this better.
 *
 * @author maurer.it_at_gmail.com
 */
public class EyeOfUgin extends CardImpl<EyeOfUgin> {

    private static final FilterCreatureCard filter;

    static {
        filter = new FilterCreatureCard();
        filter.add(new ColorlessPredicate());
    }

    public EyeOfUgin (UUID ownerId) {
        super(ownerId, 136, "Eye of Ugin", Rarity.MYTHIC, new CardType[]{CardType.LAND}, null);
        this.expansionSetCode = "WWK";
        this.supertype.add("Legendary");
        this.subtype.add("Land");

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EyeOfUginCostReductionEffect()));
        Ability searchAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SearchLibraryRevealPutInHandEffect(new TargetCardInLibrary(filter)), new TapSourceCost());
        searchAbility.addCost(new ManaCostsImpl("{7}"));
        this.addAbility(searchAbility);
    }

    public EyeOfUgin (final EyeOfUgin card) {
        super(card);
    }

    @Override
    public EyeOfUgin copy() {
        return new EyeOfUgin(this);
    }
}

class EyeOfUginCostReductionEffect extends CostModificationEffectImpl<EyeOfUginCostReductionEffect> {

    private static final String effectText = "Colorless Eldrazi spells you cast cost {2} less to cast";

    EyeOfUginCostReductionEffect ( ) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = effectText;
    }

    EyeOfUginCostReductionEffect(EyeOfUginCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility)abilityToModify;
        int previousCost = spellAbility.getManaCostsToPay().convertedManaCost();
        int adjustedCost = 0;
        if ( (previousCost - 2) > 0 ) {
            adjustedCost = previousCost - 2;
        }
        spellAbility.getManaCostsToPay().load("{" + adjustedCost + "}");

        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ( abilityToModify instanceof SpellAbility ) {
            Card sourceCard = game.getCard(((SpellAbility)abilityToModify).getSourceId());
            if ( sourceCard != null && sourceCard.hasSubtype("Eldrazi") && sourceCard.getOwnerId().equals(source.getControllerId()) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public EyeOfUginCostReductionEffect copy() {
        return new EyeOfUginCostReductionEffect(this);
    }

}
