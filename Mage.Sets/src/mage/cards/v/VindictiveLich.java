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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterOpponent;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author anonymous
 */
public class VindictiveLich extends CardImpl {

    public VindictiveLich(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.ZOMBIE, SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // When Vindictive Lich dies, choose one or more. Each mode must target a different player.
        // *Target opponent sacrifices a creature.
        Ability ability = new DiesTriggeredAbility(new SacrificeEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, "target opponent"));
        ability.getModes().setMinModes(1);
        ability.getModes().setMaxModes(3);
        ability.getModes().setMaxModesFilter(new FilterOpponent("a different player"));

        FilterOpponent filter = new FilterOpponent("opponent (sacrifice)");
        filter.add(new AnotherTargetPredicate(1, true));
        Target target = new TargetOpponent(filter, false);
        target.setTargetTag(1);
        ability.addTarget(target);

        // *Target opponent discards two cards.
        Mode mode = new Mode();
        mode.getEffects().add(new DiscardTargetEffect(2, false));
        filter = new FilterOpponent("opponent (discard)");
        filter.add(new AnotherTargetPredicate(2, true));
        target = new TargetOpponent(filter, false);
        target.setTargetTag(2);
        mode.getTargets().add(target);
        ability.addMode(mode);

        // *Target opponent loses 5 life.
        mode = new Mode();
        mode.getEffects().add(new LoseLifeTargetEffect(2));
        filter = new FilterOpponent("opponent (life loss)");
        filter.add(new AnotherTargetPredicate(3, true));
        target = new TargetOpponent(filter, false);
        target.setTargetTag(3);
        mode.getTargets().add(target);
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public VindictiveLich(final VindictiveLich card) {
        super(card);
    }

    @Override
    public VindictiveLich copy() {
        return new VindictiveLich(this);
    }

}
