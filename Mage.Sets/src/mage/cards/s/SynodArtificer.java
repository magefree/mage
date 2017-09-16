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
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetAdjustment;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class SynodArtificer extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("noncreature artifacts");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public SynodArtificer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        //TODO: Make ability properly copiable
        // {X}, {tap}: Tap X target noncreature artifacts.
        Effect tapEffect = new TapTargetEffect();
        tapEffect.setText("Tap X target noncreature artifacts.");
        Ability tapAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, tapEffect, new ManaCostsImpl("{X}"));
        tapAbility.addCost(new TapSourceCost());
        tapAbility.setTargetAdjustment(TargetAdjustment.XCOST);
        tapAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(tapAbility);

        // {X}, {tap}: Untap X target noncreature artifacts.
        Effect untapEffect = new UntapTargetEffect();
        untapEffect.setText("Untap X target noncreature artifacts.");
        Ability untapAbility = new SimpleActivatedAbility(Zone.BATTLEFIELD, untapEffect, new ManaCostsImpl("{X}"));
        untapAbility.addCost(new TapSourceCost());
        untapAbility.setTargetAdjustment(TargetAdjustment.XCOST);
        untapAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(untapAbility);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getTargetAdjustment() == TargetAdjustment.XCOST) {
            FilterPermanent filter2 = ((TargetPermanent) ability.getTargets().get(0)).getFilter();
            ability.getTargets().clear();
            ability.addTarget(new TargetPermanent(ability.getManaCostsToPay().getX(), filter2));
        }
    }

    public SynodArtificer(final SynodArtificer card) {
        super(card);
    }

    @Override
    public SynodArtificer copy() {
        return new SynodArtificer(this);
    }
}
