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
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterOpponentsCreaturePermanent;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class JabbaTheHutt extends CardImpl {

    private static final FilterOpponentsCreaturePermanent filter = new FilterOpponentsCreaturePermanent("creature an opponent control with a bounty counter on it");

    static {
        filter.add(new CounterPredicate(CounterType.BOUNTY));
    }

    public JabbaTheHutt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{R}{G}");
        this.supertype.add("Legendary");
        this.subtype.add("Hutt");
        this.subtype.add("Rogue");
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {T}: Put a bounty counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {R},{T}: Create a tapped 4/4 red Hunter creature token. It fights another target creature an opponent control with a bounty counter on it. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new JabbaTheHuttEffect(), new ManaCostsImpl("R"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetOpponentsCreaturePermanent(filter));
        this.addAbility(ability);
    }

    public JabbaTheHutt(final JabbaTheHutt card) {
        super(card);
    }

    @Override
    public JabbaTheHutt copy() {
        return new JabbaTheHutt(this);
    }
}

class HunterToken extends Token {

    public HunterToken() {
        super("Hunter", "4/4 red Hunter creature token", 4, 4);
        this.setOriginalExpansionSetCode("SWS");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add("Hunter");
    }
}

class JabbaTheHuttEffect extends OneShotEffect {

    public JabbaTheHuttEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a tapped 4/4 red Hunter creature token. It fights another target creature an opponent control with a bounty counter on it";
    }

    public JabbaTheHuttEffect(final JabbaTheHuttEffect effect) {
        super(effect);
    }

    @Override
    public JabbaTheHuttEffect copy() {
        return new JabbaTheHuttEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new HunterToken(), 1, true, false);
            effect.apply(game, source);
            Permanent token = game.getPermanent(effect.getLastAddedTokenIds().get(0));
            Permanent opponentCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
            if (token != null && opponentCreature != null) {
                return token.fight(opponentCreature, source, game);
            }
        }
        return false;
    }
}
