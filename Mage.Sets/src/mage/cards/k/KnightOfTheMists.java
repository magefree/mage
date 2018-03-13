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
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlankingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class KnightOfTheMists extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Knight");

    static {
        filter.add(new SubtypePredicate(SubType.KNIGHT));
    }

    public KnightOfTheMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flanking
        this.addAbility(new FlankingAbility());

        // When Knight of the Mists enters the battlefield, you may pay {U}. If you don't, destroy target Knight and it can't be regenerated.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KnightOfTheMistsEffect());
        ability.addTarget(new TargetCreaturePermanent(filter));
    }

    public KnightOfTheMists(final KnightOfTheMists card) {
        super(card);
    }

    @Override
    public KnightOfTheMists copy() {
        return new KnightOfTheMists(this);
    }
}

class KnightOfTheMistsEffect extends OneShotEffect {

    KnightOfTheMistsEffect() {
        super(Outcome.Neutral);
        this.staticText = "When {this} enters the battlefield, you may pay {U}. If you don't, destroy target Knight and it can't be regenerated.";
    }

    KnightOfTheMistsEffect(final KnightOfTheMistsEffect effect) {
        super(effect);
    }

    @Override
    public KnightOfTheMistsEffect copy() {
        return new KnightOfTheMistsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getSourceId());
        if (player == null) {
            return false;
        }
        Cost cost = new ManaCostsImpl("{U}");
        if (!(cost.canPay(source, source.getSourceId(), player.getId(), game)
                && player.chooseUse(outcome, "Pay {U}?", source, game)
                && cost.pay(source, game, source.getSourceId(), player.getId(), false))) {
            return new DestroyTargetEffect(true).apply(game, source);
        }
        return true;
    }
}
