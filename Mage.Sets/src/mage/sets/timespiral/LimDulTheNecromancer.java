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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.RegenerateTargetEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public class LimDulTheNecromancer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    private static final FilterPermanent filter2 = new FilterPermanent("Zombie");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter2.add(new SubtypePredicate("Zombie"));
    }

    public LimDulTheNecromancer(UUID ownerId) {
        super(ownerId, 114, "Lim-Dul the Necromancer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{B}{B}");
        this.expansionSetCode = "TSP";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever a creature an opponent controls dies, you may pay {1}{B}. If you do, return that card to the battlefield under your control. If it's a creature, it's a Zombie in addition to its other creature types.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(new LimDulTheNecromancerEffect(), new ManaCostsImpl("{1}{B}")), true, filter, true));

        // {1}{B}: Regenerate target Zombie.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateTargetEffect(), new ManaCostsImpl("{1}{B}"));
        ability2.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability2);

    }

    public LimDulTheNecromancer(final LimDulTheNecromancer card) {
        super(card);
    }

    @Override
    public LimDulTheNecromancer copy() {
        return new LimDulTheNecromancer(this);
    }
}

class LimDulTheNecromancerEffect extends OneShotEffect {

    public LimDulTheNecromancerEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "return that card to the battlefield under your control. If it's a creature, it's a Zombie in addition to its other creature types";
    }

    public LimDulTheNecromancerEffect(final LimDulTheNecromancerEffect effect) {
        super(effect);
    }

    @Override
    public LimDulTheNecromancerEffect copy() {
        return new LimDulTheNecromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            Zone currentZone = game.getState().getZone(card.getId());
            if (card.putOntoBattlefield(game, currentZone, source.getSourceId(), source.getControllerId())
                    && card.getCardType().contains(CardType.CREATURE)) {
                Permanent creature = game.getPermanent(card.getId());
                ContinuousEffect effect = new AddCardSubTypeTargetEffect("Zombie", Duration.WhileOnBattlefield);
                effect.setTargetPointer(new FixedTarget(creature.getId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}
