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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SpellAbilityType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class MinamosMeddling extends CardImpl {

    public MinamosMeddling(UUID ownerId) {
        super(ownerId, 42, "Minamo's Meddling", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}{U}");
        this.expansionSetCode = "BOK";

        this.color.setBlue(true);

        // Counter target spell. That spell's controller reveals his or her hand, then discards each card with the same name as a card spliced onto that spell.
        this.getSpellAbility().addTarget(new TargetSpell(new FilterSpell()));
        this.getSpellAbility().addEffect(new MinamosMeddlingCounterTargetEffect());
    }

    public MinamosMeddling(final MinamosMeddling card) {
        super(card);
    }

    @Override
    public MinamosMeddling copy() {
        return new MinamosMeddling(this);
    }
}

class MinamosMeddlingCounterTargetEffect extends OneShotEffect {

    public MinamosMeddlingCounterTargetEffect() {
        super(Outcome.Benefit);
        staticText = "Counter target spell. That spell's controller reveals his or her hand, then discards each card with the same name as a card spliced onto that spell";
    }

    public MinamosMeddlingCounterTargetEffect(final MinamosMeddlingCounterTargetEffect effect) {
        super(effect);
    }

    @Override
    public MinamosMeddlingCounterTargetEffect copy() {
        return new MinamosMeddlingCounterTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            for (UUID targetId : getTargetPointer().getTargets(game, source) ) {
                Spell spell = game.getStack().getSpell(targetId);
                if (spell != null) {
                    game.getStack().counter(targetId, source.getSourceId(), game);
                    Player spellController = game.getPlayer(spell.getControllerId());
                    if (spellController != null) {
                        spellController.revealCards(sourceObject.getName(), spellController.getHand(), game);
                        Cards cardsToDiscard = new CardsImpl();
                        for (SpellAbility spellAbility : spell.getSpellAbilities()) {
                            if (spellAbility.getSpellAbilityType().equals(SpellAbilityType.SPLICE)) {
                                for (Card card: spellController.getHand().getCards(game)) {
                                    if (card.getName().equals(spellAbility.getCardName()) && !cardsToDiscard.contains(card.getId())) {
                                        cardsToDiscard.add(card);
                                    }
                                }
                            }
                        }
                        if (!cardsToDiscard.isEmpty()) {
                            for (Card card :cardsToDiscard.getCards(game)) {
                                spellController.discard(card, source, game);
                            }
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }

}
