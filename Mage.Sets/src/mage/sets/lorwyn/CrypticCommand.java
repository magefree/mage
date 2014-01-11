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

import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author jonubuu
 */
public class CrypticCommand extends CardImpl<CrypticCommand> {

    public CrypticCommand(UUID ownerId) {
        super(ownerId, 56, "Cryptic Command", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");
        this.expansionSetCode = "LRW";

        this.color.setBlue(true);

        // Choose two -
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        // Counter target spell;
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        // or return target permanent to its owner's hand;
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnToHandTargetEffect());
        mode.getTargets().add(new TargetPermanent());
        this.getSpellAbility().getModes().addMode(mode);
        // or tap all creatures your opponents control;
        mode = new Mode();
        mode.getEffects().add(new CrypticCommandEffect());
        this.getSpellAbility().getModes().addMode(mode);
        // or draw a card.
        mode = new Mode();
        mode.getEffects().add(new DrawCardControllerEffect(1));
        this.getSpellAbility().getModes().addMode(mode);
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
      filter.add(new ControllerPredicate(TargetController.NOT_YOU));
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
