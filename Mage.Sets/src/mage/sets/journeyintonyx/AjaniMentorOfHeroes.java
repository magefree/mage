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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public class AjaniMentorOfHeroes extends CardImpl<AjaniMentorOfHeroes> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you control");
    private static final FilterCreatureCard filterCard = new FilterCreatureCard("an Aura, creature, or planeswalker card");
    
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filterCard.add(Predicates.or(
                new SubtypePredicate("Aura"),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER)));
    }
    
    
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 4));
    }    
    
    public AjaniMentorOfHeroes(UUID ownerId) {
        super(ownerId, 145, "Ajani, Mentor of Heroes", Rarity.MYTHIC, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{W}");
        this.expansionSetCode = "JOU";
        this.subtype.add("Ajani");

        this.color.setGreen(true);
        this.color.setWhite(true);
        
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(4)), false));

        // +1: Distribute three +1/+1 counters among one, two, or three target creatures you control
        Ability ability = new LoyaltyAbility(new AjaniMentorOfHeroesAddCountersEffect(), 1);
        ability.addTarget(new TargetCreaturePermanentAmount(3, filter));
        this.addAbility(ability);
        
        // +1: Look at the top four cards of your library. You may reveal an Aura, creature, or planeswalker card from among them and put that card into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new LoyaltyAbility(new LookLibraryAndPickControllerEffect(4,1, filterCard,true, false, Zone.HAND, true), 1));
        
        // -8: You gain 100 life.
        this.addAbility(new LoyaltyAbility(new GainLifeEffect(100), -8));
    }

    public AjaniMentorOfHeroes(final AjaniMentorOfHeroes card) {
        super(card);
    }

    @Override
    public AjaniMentorOfHeroes copy() {
        return new AjaniMentorOfHeroes(this);
    }
}

class AjaniMentorOfHeroesAddCountersEffect extends OneShotEffect<AjaniMentorOfHeroesAddCountersEffect> {

    public AjaniMentorOfHeroesAddCountersEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Distribute three +1/+1 counters among one, two, or three target creatures you control";
    }

    public AjaniMentorOfHeroesAddCountersEffect(final AjaniMentorOfHeroesAddCountersEffect effect) {
        super(effect);
    }

    @Override
    public AjaniMentorOfHeroesAddCountersEffect copy() {
        return new AjaniMentorOfHeroesAddCountersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && source.getTargets().size() > 0) {
            Target multiTarget = source.getTargets().get(0);
            for (UUID target: multiTarget.getTargets()) {
                Permanent permanent = game.getPermanent(target);
                if (permanent != null) {
                    permanent.addCounters(CounterType.P1P1.createInstance(multiTarget.getTargetAmount(target)), game);
                    game.informPlayers(new StringBuilder(controller.getName()).append(" puts ").append(multiTarget.getTargetAmount(target)).append(" ").append(CounterType.P1P1.getName().toLowerCase()).append(" counter on ").append(permanent.getName()).toString());
                }
            }
            return true;
        }
        return false;
    }
}
