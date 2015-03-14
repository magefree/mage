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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class DisplayOfDominance extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("blue or black noncreature permanent");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.BLACK)));
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
    }

    public DisplayOfDominance(UUID ownerId) {
        super(ownerId, 182, "Display of Dominance", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "DTK";

        // Choose one -
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(1);

        // Destroy target blue or black noncreature permanent
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));

        // or Permanents you control can't be the targets of blue or black spells your opponents control this turn
        Mode mode = new Mode();
        mode.getEffects().add(new DisplayOfDominanceEffect());
        this.getSpellAbility().getModes().addMode(mode);
    }

    public DisplayOfDominance(final DisplayOfDominance card) {
        super(card);
    }

    @Override
    public DisplayOfDominance copy() {
        return new DisplayOfDominance(this);
    }
}

class DisplayOfDominanceEffect extends ContinuousRuleModifyingEffectImpl {

    public DisplayOfDominanceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "permanents you control can't be the targets of blue or black spells your opponents control this turn";
    }

    public DisplayOfDominanceEffect(final DisplayOfDominanceEffect effect) {
        super(effect);
    }

    @Override
    public DisplayOfDominanceEffect copy() {
        return new DisplayOfDominanceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.TARGET;
    }

    @Override
    public boolean applies(GameEvent event, Ability ability, Game game) {
        MageObject mageObject = game.getObject(event.getSourceId());
        if (game.getPlayer(ability.getControllerId()).hasOpponent(event.getPlayerId(), game) &&
                mageObject instanceof Spell &&
                (mageObject.getColor().isBlack() || mageObject.getColor().isBlue())) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            return permanent != null && permanent.getControllerId().equals(ability.getControllerId());
        }
        return false;
    }
}
