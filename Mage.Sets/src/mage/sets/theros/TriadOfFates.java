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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.ExileTargetForSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class TriadOfFates extends CardImpl<TriadOfFates> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterCounter = new FilterCreaturePermanent("creature that has a fate counter on it");
    static {
        filter.add(new AnotherPredicate());
        filterCounter.add(new CounterPredicate(CounterType.FATE));
    }

    public TriadOfFates(UUID ownerId) {
        super(ownerId, 206, "Triad of Fates", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");
        this.expansionSetCode = "THS";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {1}, {T}: Put a fate counter on another target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.FATE.createInstance()), new ManaCostsImpl("{1}"));
        ability.addCost(new TapSourceCost());
        Target target = new TargetCreaturePermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        // {W}, {T}: Exile target creature that has a fate counter on it, then return it to the battlefield under its owner's control.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetForSourceEffect("Triad of Fates"), new ManaCostsImpl("{W}"));
        target = new TargetCreaturePermanent(filterCounter);
        target.setRequired(true);
        ability.addTarget(target);
        ability.addEffect(new ReturnToBattlefieldUnderOwnerControlTargetEffect());
        this.addAbility(ability);
        // {B}, {T}: Exile target creature that has a fate counter on it. Its controller draws two cards.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ExileTargetEffect(), new ManaCostsImpl("{B}"));
        target = new TargetCreaturePermanent(filterCounter);
        target.setRequired(true);
        ability.addTarget(target);
        ability.addEffect(new DrawCardControllerTargetEffect());
        this.addAbility(ability);

    }

    public TriadOfFates(final TriadOfFates card) {
        super(card);
    }

    @Override
    public TriadOfFates copy() {
        return new TriadOfFates(this);
    }
}

class DrawCardControllerTargetEffect extends OneShotEffect<DrawCardControllerTargetEffect> {

    public DrawCardControllerTargetEffect() {
        super(Outcome.Benefit);
        this.staticText = "Its controller draws two cards";
    }

    public DrawCardControllerTargetEffect(final DrawCardControllerTargetEffect effect) {
        super(effect);
    }

    @Override
    public DrawCardControllerTargetEffect copy() {
        return new DrawCardControllerTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (creature != null) {
            Player controllerOfTarget = game.getPlayer(creature.getControllerId());
            if (controllerOfTarget != null) {
                controllerOfTarget.drawCards(2, game);
            }
        }
        return false;
    }
}
