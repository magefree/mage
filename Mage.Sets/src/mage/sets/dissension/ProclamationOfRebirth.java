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
package mage.sets.dissension;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author Plopman
 */
public class ProclamationOfRebirth extends CardImpl<ProclamationOfRebirth> {

    private static final FilterCreatureCard filter1 = new FilterCreatureCard("creature card with converted mana cost {1} or less from your graveyard");
    private static final FilterCreatureCard filter3 = new FilterCreatureCard("creature cards with converted mana cost {1} or less from your graveyard");
    static {
        filter1.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 2));
        filter3.add(new ConvertedManaCostPredicate(Filter.ComparisonType.LessThan, 2));
    }
    public ProclamationOfRebirth(UUID ownerId) {
        super(ownerId, 15, "Proclamation of Rebirth", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{2}{W}");
        this.expansionSetCode = "DIS";

        this.color.setWhite(true);

        // Return up to three target creature cards with converted mana cost 1 or less from your graveyard to the battlefield.
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,3,filter3));
        this.getSpellAbility().addEffect(new ReturnFromGraveyardToBattlefieldTargetEffect());
        // Forecast - {5}{W}, Reveal Proclamation of Rebirth from your hand: Return target creature card with converted mana cost 1 or less from your graveyard to the battlefield.
        ForecastAbility ability = new ForecastAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl("{5}{W}"));
        ability.addTarget(new TargetCardInYourGraveyard(filter1));
        this.addAbility(ability);
    }

    public ProclamationOfRebirth(final ProclamationOfRebirth card) {
        super(card);
    }

    @Override
    public ProclamationOfRebirth copy() {
        return new ProclamationOfRebirth(this);
    }
}
