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
package mage.abilities.keyword;

import java.util.ArrayList;
import java.util.UUID;
import mage.Constants;
import mage.Constants.Outcome;
import mage.Constants.Zone;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ReturnFromExileForSourceEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.Card;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

/*
 * @author LevelX2
 *
 *
 * 702.70. Champion
 *
 * 702.70a Champion represents two triggered abilities. "Champion an [object]" means
 * "When this permanent enters the battlefield, sacrifice it unless you exile another
 * [object] you control" and "When this permanent leaves the battlefield, return the
 * exiled card to the battlefield under its owner's control."
 *
 * 702.70b The two abilities represented by champion are linked. See rule 607, "Linked Abilities." #
 *
 * 702.70c A permanent is "championed" by another permanent if the latter exiles
 * the former as the direct result of a champion ability. #
 *
 */

public class ChampionAbility extends StaticAbility<ChampionAbility> {

    protected String[] subtypes;
    protected String objectDescription;


    public ChampionAbility(Card card, String subtype) {
        this(card, new String[]{subtype});
    }
    
    public ChampionAbility(Card card, String[] subtypes) {
        super(Zone.BATTLEFIELD, null);

        this.subtypes = subtypes;

        StringBuilder sb = new StringBuilder("another ");
        ArrayList<Predicate<MageObject>> subtypesPredicates = new ArrayList<Predicate<MageObject>>();
        int i = 0;
        for (String subtype : this.subtypes) {
            subtypesPredicates.add(new SubtypePredicate(subtype));
            if (i == 0) {
                sb.append(subtype);
            } else {
                sb.append(" or ").append(subtype);
            }
            i++;
        }
        this.objectDescription = sb.toString();
        FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent(objectDescription);
        filter.add(Predicates.or(subtypesPredicates));
        filter.add(new AnotherPredicate());

        // When this permanent enters the battlefield, sacrifice it unless you exile another [object] you control.
        Ability ability1 = new EntersBattlefieldTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ChampionExileCost(filter, new StringBuilder(card.getName()).append(" championed creatures").toString())),false);
        ability1.setRuleVisible(false);
        card.addAbility(ability1);

        // When this permanent leaves the battlefield, return the exiled card to the battlefield under its owner's control.
        Ability ability2 = new LeavesBattlefieldTriggeredAbility(new ReturnFromExileForSourceEffect(Constants.Zone.BATTLEFIELD), false);
        ability2.setRuleVisible(false);
        card.addAbility(ability2);
    }

    public ChampionAbility(final ChampionAbility ability) {
        super(ability);
        this.subtypes = ability.subtypes;
        this.objectDescription = ability.objectDescription;
    }

    @Override
    public ChampionAbility copy() {
        return new ChampionAbility(this);
    }

    @Override
    public String getRule() {
        StringBuilder sb = new StringBuilder("Champion ").append(objectDescription);
        sb.append("<i>(When this enters the battlefield, sacrifice it unless you exile another ");
        sb.append(objectDescription);
        sb.append("you control. When this leaves the battlefield, that card returns to the battlefield.)</i>");
        return sb.toString();
    }
}

class ChampionExileCost extends CostImpl<ChampionExileCost> {

    private String exileZone = null;

    public ChampionExileCost(FilterControlledCreaturePermanent filter, String exileZone) {
        this.addTarget(new TargetControlledCreaturePermanent(1,1,filter, true));
        this.text = "exile " + filter.getMessage() + " you control";
        this.exileZone = exileZone;
    }

    public ChampionExileCost(ChampionExileCost cost) {
        super(cost);
        this.exileZone = cost.exileZone;
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        if (targets.choose(Outcome.Exile, controllerId, sourceId, game)) {
            for (UUID targetId: targets.get(0).getTargets()) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null)
                    return false;
                paid |= permanent.moveToExile(sourceId, exileZone, sourceId, game);
            }
        }
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        return targets.canChoose(controllerId, game);
    }

    @Override
    public ChampionExileCost copy() {
        return new ChampionExileCost(this);
    }
}
