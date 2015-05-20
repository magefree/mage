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
package mage.sets.morningtide;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksCreatureYouControlTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class RageForger extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other Shaman creature you control");
    private static final FilterControlledCreaturePermanent filterAttack = new FilterControlledCreaturePermanent("creature you control with a +1/+1 counter on it");
    
    static {
        filter.add(new SubtypePredicate("Shaman"));
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new AnotherPredicate());
        filterAttack.add(new CounterPredicate(CounterType.P1P1));
    }
    
    public RageForger(UUID ownerId) {
        super(ownerId, 97, "Rage Forger", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Elemental");
        this.subtype.add("Shaman");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Rage Forger enters the battlefield, put a +1/+1 counter on each other Shaman creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter), false));
        // Whenever a creature you control with a +1/+1 counter on it attacks, you may have that creature deal 1 damage to target player.
        Ability ability = new AttacksCreatureYouControlTriggeredAbility(new RageForgerDamageEffect(), true, filterAttack, true);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
        
    }

    public RageForger(final RageForger card) {
        super(card);
    }

    @Override
    public RageForger copy() {
        return new RageForger(this);
    }
}

class RageForgerDamageEffect extends OneShotEffect {
    
    public RageForgerDamageEffect() {
        super(Outcome.Benefit);
        this.staticText = "you may have that creature deal 1 damage to target player";
    }
    
    public RageForgerDamageEffect(final RageForgerDamageEffect effect) {
        super(effect);
    }
    
    @Override
    public RageForgerDamageEffect copy() {
        return new RageForgerDamageEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Permanent attackingCreature = game.getPermanentOrLKIBattlefield(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null && attackingCreature != null) {
            targetPlayer.damage(1, attackingCreature.getId(), game, false, true);
            return true;
        }
        return false;
    }
}
