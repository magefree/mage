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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jonubuu
 */
public class CrypticCommand extends CardImpl<CrypticCommand> {

    public CrypticCommand(UUID ownerId) {
        super(ownerId, 56, "Cryptic Command", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");
        this.expansionSetCode = "LRW";

        this.color.setBlue(true);

        // Choose two - Counter target spell; or return target permanent to its owner's hand; or tap all creatures your opponents control; or draw a card.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addEffect(new CounterSecondTargetEffect());

        Mode mode1 = new Mode();
        mode1.getTargets().add(new TargetSpell());
        mode1.getEffects().add(new CounterTargetEffect());
        mode1.getEffects().add(new CrypticCommandEffect());
        this.getSpellAbility().addMode(mode1);

        Mode mode2 = new Mode();
        mode2.getTargets().add(new TargetSpell());
        mode2.getEffects().add(new CounterTargetEffect());
        mode2.getEffects().add(new DrawCardControllerEffect(1));
        this.getSpellAbility().addMode(mode2);

        Mode mode3 = new Mode();
        mode3.getTargets().add(new TargetCreaturePermanent());
        mode3.getEffects().add(new ReturnToHandTargetEffect());
        mode3.getEffects().add(new CrypticCommandEffect());
        this.getSpellAbility().addMode(mode3);

        Mode mode4 = new Mode();
        mode4.getTargets().add(new TargetCreaturePermanent());
        mode4.getEffects().add(new ReturnToHandTargetEffect());
        mode4.getEffects().add(new DrawCardControllerEffect(1));
        this.getSpellAbility().addMode(mode4);

        Mode mode5 = new Mode();
        mode5.getTargets().add(new TargetPlayer());
        mode5.getEffects().add(new CrypticCommandEffect());
        mode5.getEffects().add(new DrawCardControllerEffect(1));
        this.getSpellAbility().addMode(mode5);
    }

    public CrypticCommand(final CrypticCommand card) {
        super(card);
    }

    @Override
    public CrypticCommand copy() {
        return new CrypticCommand(this);
    }
}

class CrypticCommandEffect extends OneShotEffect<CrypticCommandEffect> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");
    static {
      filter.add(new ControllerPredicate(Constants.TargetController.NOT_YOU));
    }

    public CrypticCommandEffect() {
        super(Outcome.Tap);
        staticText = "tap all creatures your opponents control";
    }

    public CrypticCommandEffect(final CrypticCommandEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Permanent creature : game.getBattlefield().getActivePermanents(filter, player.getId(), source.getSourceId(), game)) {
            creature.tap(game);
        }
        return true;
    }

    @Override
    public CrypticCommandEffect copy() {
        return new CrypticCommandEffect(this);
    }
}

class CounterSecondTargetEffect extends OneShotEffect<CounterSecondTargetEffect> {

    public CounterSecondTargetEffect() {
        super(Outcome.Detriment);
        this.staticText = "counter target spell";
    }

    public CounterSecondTargetEffect(final CounterSecondTargetEffect effect) {
        super(effect);
    }

    @Override
    public CounterSecondTargetEffect copy() {
        return new CounterSecondTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game.getStack().counter(source.getTargets().get(1).getFirstTarget(), source.getSourceId(), game);
    }
}
