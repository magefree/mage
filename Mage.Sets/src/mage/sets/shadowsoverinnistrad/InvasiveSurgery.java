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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class InvasiveSurgery extends CardImpl {

    private final static FilterSpell filter = new FilterSpell("sorcery spell");

    static {
        filter.add(new CardTypePredicate(CardType.SORCERY));
    }

    public InvasiveSurgery(UUID ownerId) {
        super(ownerId, 68, "Invasive Surgery", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "SOI";

        // Counter target sorcery spell.
        // <i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, search the graveyard, hand, and library of that spell's controller for any number of cards with the same name as that spell, exile those cards, then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new InvasiveSurgeryEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));

    }

    public InvasiveSurgery(final InvasiveSurgery card) {
        super(card);
    }

    @Override
    public InvasiveSurgery copy() {
        return new InvasiveSurgery(this);
    }
}

class InvasiveSurgeryEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    public InvasiveSurgeryEffect() {
        super(true, "that spell's controller", "all cards with the same name as that spell");
    }

    public InvasiveSurgeryEffect(final InvasiveSurgeryEffect effect) {
        super(effect);
    }

    @Override
    public InvasiveSurgeryEffect copy() {
        return new InvasiveSurgeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        String cardName = "";
        UUID spellController = null;
        if (source.getTargets().get(0) instanceof TargetSpell) {
            UUID objectId = source.getFirstTarget();
            StackObject stackObject = game.getStack().getStackObject(objectId);
            if (stackObject != null) {
                MageObject targetObject = game.getObject(stackObject.getSourceId());
                if (targetObject instanceof Card) {
                    cardName = targetObject.getName();
                }
                spellController = stackObject.getControllerId();
                game.getStack().counter(objectId, source.getSourceId(), game);
            }
        }

        // Check the Delirium condition
        if (!DeliriumCondition.getInstance().apply(game, source)) {
            return true;
        }
        return this.applySearchAndExile(game, source, cardName, spellController);
    }

    @Override
    public String getText(Mode mode) {
        return "Counter target sorcery spell.<br><br>"
                + "<i>Delirium</i> &mdash; If there are four or more card types among cards in your graveyard, "
                + "search the graveyard, hand, and library of that spell's controller for any number of cards "
                + "with the same name as that spell, exile those cards, then that player shuffles his or her library";
    }
}
