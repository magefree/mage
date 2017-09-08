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
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.PlanswalkerEntersWithLoyalityCountersAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX2
 */
public class RalZarek extends CardImpl {

    private static final FilterPermanent secondFilter = new FilterPermanent("another target permanent");

    static {
        secondFilter.add(new AnotherTargetPredicate(2));
    }

    public RalZarek(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{U}{R}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.RAL);

        this.addAbility(new PlanswalkerEntersWithLoyalityCountersAbility(4));

        // +1: Tap target permanent, then untap another target permanent.
        LoyaltyAbility ability1 = new LoyaltyAbility(new TapTargetEffect(), 1);
        TargetPermanent firstTarget = new TargetPermanent();
        firstTarget.setTargetTag(1);
        ability1.addTarget(firstTarget);
        Effect effect = new UntapTargetEffect();
        effect.setText(", then untap another target permanent");
        effect.setTargetPointer(new SecondTargetPointer());
        ability1.addEffect(effect);
        TargetPermanent secondTarget = new TargetPermanent(secondFilter);
        secondTarget.setTargetTag(2);
        ability1.addTarget(secondTarget);
        this.addAbility(ability1);

        // -2: Ral Zarek deals 3 damage to target creature or player.
        LoyaltyAbility ability2 = new LoyaltyAbility(new DamageTargetEffect(3), -2);
        ability2.addTarget(new TargetCreatureOrPlayer());
        this.addAbility(ability2);

        // -7: Flip five coins. Take an extra turn after this one for each coin that comes up heads.
        this.addAbility(new LoyaltyAbility(new RalZarekExtraTurnsEffect(), -7));

    }

    public RalZarek(final RalZarek card) {
        super(card);
    }

    @Override
    public RalZarek copy() {
        return new RalZarek(this);
    }
}

class RalZarekExtraTurnsEffect extends OneShotEffect {

    public RalZarekExtraTurnsEffect() {
        super(Outcome.ExtraTurn);
        this.staticText = "Flip five coins. Take an extra turn after this one for each coin that comes up heads";
    }

    public RalZarekExtraTurnsEffect(final RalZarekExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public RalZarekExtraTurnsEffect copy() {
        return new RalZarekExtraTurnsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (int i = 0; i < 5; i++) {
                if (controller.flipCoin(game)) {
                    game.getState().getTurnMods().add(new TurnMod(source.getControllerId(), false));
                }
            }
            return true;
        }
        return false;
    }
}
