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
package mage.sets.mirage;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class PhyrexianDreadnought extends CardImpl<PhyrexianDreadnought> {

    public PhyrexianDreadnought(UUID ownerId) {
        super(ownerId, 280, "Phyrexian Dreadnought", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.expansionSetCode = "MIR";
        this.subtype.add("Dreadnought");

        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Phyrexian Dreadnought enters the battlefield, sacrifice it unless you sacrifice any number of creatures with total power 12 or greater.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new PhyrexianDreadnoughtSacrificeCost())));

    }

    public PhyrexianDreadnought(final PhyrexianDreadnought card) {
        super(card);
    }

    @Override
    public PhyrexianDreadnought copy() {
        return new PhyrexianDreadnought(this);
    }
}

class PhyrexianDreadnoughtSacrificeCost extends CostImpl<PhyrexianDreadnoughtSacrificeCost> {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("any number of creatures with total power 12 or greater");

    static {
        filter.add(new AnotherPredicate());
    }

    public PhyrexianDreadnoughtSacrificeCost() {
        this.addTarget(new TargetControlledCreaturePermanent(0, Integer.MAX_VALUE, filter, true, false));
        this.text = "sacrifice any number of creatures with total power 12 or greater";
    }

    public PhyrexianDreadnoughtSacrificeCost(final PhyrexianDreadnoughtSacrificeCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        int sumPower = 0;
        if (targets.choose(Outcome.Sacrifice, controllerId, sourceId, game)) {
            for (UUID targetId: targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null && permanent.sacrifice(sourceId, game)) {
                    sumPower += permanent.getPower().getValue();
                }
            }
        }
        game.informPlayers(new StringBuilder("Sacrificed creatures with total power of ").append(sumPower).toString());
        paid = sumPower >= 12;
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        int sumPower = 0;
        for (Permanent permanent :game.getBattlefield().getAllActivePermanents(new FilterCreaturePermanent(), controllerId, game)) {
            if (!permanent.getId().equals(sourceId)) {
                sumPower += permanent.getPower().getValue();
            }
        }
        return sumPower >= 12;
    }

    @Override
    public PhyrexianDreadnoughtSacrificeCost copy() {
        return new PhyrexianDreadnoughtSacrificeCost(this);
    }
}
