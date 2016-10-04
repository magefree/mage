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
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author Quercitron
 */
public class LyzoldaTheBloodWitch extends CardImpl {

    private static final FilterPermanent redFilter = new FilterPermanent();
    private static final FilterPermanent blackFilter = new FilterPermanent();
    
    static {
        redFilter.add(new ColorPredicate(ObjectColor.RED));
        blackFilter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    public LyzoldaTheBloodWitch(UUID ownerId) {
        super(ownerId, 117, "Lyzolda, the Blood Witch", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");
        this.expansionSetCode = "DIS";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Cleric");

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {2}, Sacrifice a creature: Lyzolda, the Blood Witch deals 2 damage to target creature or player if the sacrificed creature was red. Draw a card if the sacrificed creature was black.
        Effect effect = new ConditionalOneShotEffect(
                new DamageTargetEffect(2),
                new SacrificedWasCondition(redFilter),
                "{source} deals 2 damage to target creature or player if the sacrificed creature was red");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl("{2}"));
        effect = new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1),
                new SacrificedWasCondition(blackFilter),
                "Draw a card if the sacrificed creature was black");
        ability.addEffect(effect);
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        ability.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability);
    }

    public LyzoldaTheBloodWitch(final LyzoldaTheBloodWitch card) {
        super(card);
    }

    @Override
    public LyzoldaTheBloodWitch copy() {
        return new LyzoldaTheBloodWitch(this);
    }
}

class SacrificedWasCondition implements Condition {

    private final FilterPermanent filter;
    
    public SacrificedWasCondition(final FilterPermanent filter) {
        this.filter = filter;
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                UUID targetId = cost.getTargets().getFirstTarget();
                Permanent permanent = game.getPermanentOrLKIBattlefield(targetId);
                if (filter.match(permanent, game)) {
                    return true;
                }
            }
        }
        return false;
    }
    
}
