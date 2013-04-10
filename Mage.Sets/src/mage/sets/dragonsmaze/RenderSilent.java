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

package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.MulticoloredPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */


public class RenderSilent extends CardImpl<RenderSilent> {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("multicolored permanent");
    static {
        filter.add(new MulticoloredPredicate());
    }

    public RenderSilent(UUID ownerId) {
        super(ownerId, 96, "Render Silent", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{W}{U}{U}");
        this.expansionSetCode = "DGM";

        this.color.setWhite(true);
        this.color.setBlue(true);

        // Counter target spell. Its controller can't cast spells this turn.
        this.getSpellAbility().addEffect(new RenderSilentCounterEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new RenderSilentEffect());
    }

    public RenderSilent(final RenderSilent card) {
        super(card);
    }

    @Override
    public RenderSilent copy() {
        return new RenderSilent(this);
    }
}

class RenderSilentCounterEffect extends OneShotEffect<RenderSilentCounterEffect> {

    public RenderSilentCounterEffect() {
        super(Outcome.Detriment);
    }

    public RenderSilentCounterEffect(final RenderSilentCounterEffect effect) {
        super(effect);
    }

    @Override
    public RenderSilentCounterEffect copy() {
        return new RenderSilentCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        if (spell != null) {
            source.getEffects().get(1).setTargetPointer(new FixedTarget(spell.getControllerId()));
            return game.getStack().counter(source.getFirstTarget(), source.getSourceId(), game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target " + mode.getTargets().get(0).getTargetName();
    }

}

class RenderSilentEffect extends ReplacementEffectImpl<RenderSilentEffect> {

    public RenderSilentEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.Benefit);
        staticText = "Its controller can't cast spells this turn";
    }

    public RenderSilentEffect(final RenderSilentEffect effect) {
        super(effect);
    }

    @Override
    public RenderSilentEffect copy() {
        return new RenderSilentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL ) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null && player.getId().equals(event.getPlayerId())) {
                return true;
            }
        }
        return false;
    }

}
