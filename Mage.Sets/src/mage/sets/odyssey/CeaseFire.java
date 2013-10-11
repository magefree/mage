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
package mage.sets.odyssey;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth (Steel Golem), cbt33
 */
public class CeaseFire extends CardImpl<CeaseFire> {

    public CeaseFire(UUID ownerId) {
        super(ownerId, 14, "Cease-Fire", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{W}");
        this.expansionSetCode = "ODY";

        this.color.setWhite(true);

        // Target player can't cast creature spells this turn.
        this.getSpellAbility().addEffect(new CeaseFireEffect());
        this.getSpellAbility().addTarget(new TargetPlayer(true));
        
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(1));
    }

    public CeaseFire(final CeaseFire card) {
        super(card);
    }

    @Override
    public CeaseFire copy() {
        return new CeaseFire(this);
    }
}

class CeaseFireEffect extends ReplacementEffectImpl<CeaseFireEffect> {

    private static final FilterSpell filter = new FilterSpell();
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public CeaseFireEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't cast creature spells.";
    }

    public CeaseFireEffect(final CeaseFireEffect effect) {
        super(effect);
    }

    @Override
    public CeaseFireEffect copy() {
        return new CeaseFireEffect(this);
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
        if (event.getType() == GameEvent.EventType.CAST_SPELL && event.getPlayerId().equals(source.getFirstTarget())) {
            MageObject object = game.getObject(event.getSourceId());
            if (filter.match((Spell) object, game)) {
                return true;
            }
        }
        return false;
    }

}
