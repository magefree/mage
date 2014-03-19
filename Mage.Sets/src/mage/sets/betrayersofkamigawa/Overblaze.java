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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.SpliceOntoArcaneAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import static mage.game.events.GameEvent.EventType.DAMAGE_CREATURE;
import static mage.game.events.GameEvent.EventType.DAMAGE_PLAYER;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class Overblaze extends CardImpl<Overblaze> {

    public Overblaze(UUID ownerId) {
        super(ownerId, 114, "Overblaze", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{R}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Arcane");

        this.color.setRed(true);

        // Each time target permanent would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead.
        this.getSpellAbility().addEffect(new FireServantEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(true));
        // Splice onto Arcane {2}{R}{R}
        this.addAbility(new SpliceOntoArcaneAbility("{2}{R}{R}"));
    }

    public Overblaze(final Overblaze card) {
        super(card);
    }

    @Override
    public Overblaze copy() {
        return new Overblaze(this);
    }
}

class FireServantEffect extends ReplacementEffectImpl<FireServantEffect> {

    public FireServantEffect() {
        super(Duration.EndOfTurn, Outcome.Damage);
        staticText = "Each time target permanent would deal damage to a creature or player this turn, it deals double that damage to that creature or player instead.";
    }

    public FireServantEffect(final FireServantEffect effect) {
        super(effect);
    }

    @Override
    public FireServantEffect copy() {
        return new FireServantEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
                if (event.getSourceId().equals(this.getTargetPointer().getFirst(game, source))) {
                    event.setAmount(event.getAmount() * 2);
                }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

}