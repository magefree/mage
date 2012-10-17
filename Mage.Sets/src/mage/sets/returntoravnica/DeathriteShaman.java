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
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AddManaOfAnyColorEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author LevelX2
 */
public class DeathriteShaman extends CardImpl<DeathriteShaman> {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from a graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public DeathriteShaman(UUID ownerId) {
        super(ownerId, 213, "Deathrite Shaman", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B/G}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Elf");
        this.subtype.add("Shaman");
        this.color.setBlack(true);
        this.color.setGreen(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {T}: Exile target land card from a graveyard. Add one mana of any color to your mana pool.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ExileTargetEffect(), new TapSourceCost());
        ability.addEffect(new AddManaOfAnyColorEffect());
        ability.addChoice(new ChoiceColor());
        ability.addTarget(new TargetCardInGraveyard(new FilterLandCard("land card from a graveyard")));
        this.addAbility(ability);

        // {B}, {T}: Exile target instant or sorcery card from a graveyard. Each opponent loses 2 life.
        ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{B}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new LoseLifeOpponentsEffect(2));
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // {G}, {T}: Exile target creature card from a graveyard. You gain 2 life.
        ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{G}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetCardInGraveyard(new FilterCreatureCard("creature card from a graveyard")));
        this.addAbility(ability);
    }

    public DeathriteShaman(final DeathriteShaman card) {
        super(card);
    }

    @Override
    public DeathriteShaman copy() {
        return new DeathriteShaman(this);
    }
}