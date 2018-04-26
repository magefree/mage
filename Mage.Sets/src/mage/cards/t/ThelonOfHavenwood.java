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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author emerald000
 */
public class ThelonOfHavenwood extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("a Fungus card from a graveyard");
    private static final FilterPermanent filterPermanent = new FilterPermanent("Fungus on the battlefield");
    static {
        filterCard.add(new SubtypePredicate(SubType.FUNGUS));
        filterPermanent.add(new SubtypePredicate(SubType.FUNGUS));
    }

    public ThelonOfHavenwood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Each Fungus creature gets +1/+1 for each spore counter on it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ThelonOfHavenwoodBoostEffect()));

        // {B}{G}, Exile a Fungus card from a graveyard: Put a spore counter on each Fungus on the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.SPORE.createInstance(), filterPermanent), new ManaCostsImpl<>("{B}{G}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInASingleGraveyard(1, 1, filterCard)));
        this.addAbility(ability);
    }

    public ThelonOfHavenwood(final ThelonOfHavenwood card) {
        super(card);
    }

    @Override
    public ThelonOfHavenwood copy() {
        return new ThelonOfHavenwood(this);
    }
}

class ThelonOfHavenwoodBoostEffect extends ContinuousEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Fungus creature");
    static {
        filter.add(new SubtypePredicate(SubType.FUNGUS));
    }

    ThelonOfHavenwoodBoostEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.ModifyPT_7c, Outcome.BoostCreature);
        staticText = "Each Fungus creature gets +1/+1 for each spore counter on it";
    }

    ThelonOfHavenwoodBoostEffect(final ThelonOfHavenwoodBoostEffect effect) {
        super(effect);
    }

    @Override
    public ThelonOfHavenwoodBoostEffect copy() {
        return new ThelonOfHavenwoodBoostEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            int numCounters = creature.getCounters(game).getCount(CounterType.SPORE);
            if (numCounters > 0) {
                creature.addPower(numCounters);
                creature.addToughness(numCounters);
            }
        }
        return true;
    }
}
