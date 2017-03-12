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
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

/**
 *
 * @author spjspj
 */
public class EyeOfTheStorm extends CardImpl {

    public EyeOfTheStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{U}{U}");

        // Whenever a player casts an instant or sorcery card, exile it. Then that player copies each instant or sorcery card exiled with Eye of the Storm. For each copy, the player may cast the copy without paying its mana cost.
        this.addAbility(new EyeOfTheStormAbility());
    }

    public EyeOfTheStorm(final EyeOfTheStorm card) {
        super(card);
    }

    @Override
    public EyeOfTheStorm copy() {
        return new EyeOfTheStorm(this);
    }
}

class EyeOfTheStormAbility extends TriggeredAbilityImpl {

    public EyeOfTheStormAbility() {
        super(Zone.BATTLEFIELD, new EyeOfTheStormEffect1(), false);
    }

    public EyeOfTheStormAbility(final EyeOfTheStormAbility ability) {
        super(ability);
    }

    @Override
    public EyeOfTheStormAbility copy() {
        return new EyeOfTheStormAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null
                && !spell.isCopy()
                && spell.getCard() != null
                && !spell.getCard().isCopy()
                && (spell.isInstant() || spell.isSorcery())) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(event.getTargetId()));
            }
            return true;
        }
        return false;
    }

}

class EyeOfTheStormEffect1 extends OneShotEffect {

    private static final FilterInstantOrSorceryCard instantOrSorceryfilter = new FilterInstantOrSorceryCard();

    private static final FilterSpell filter = new FilterSpell("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public EyeOfTheStormEffect1() {
        super(Outcome.Neutral);
        staticText = "Whenever a player casts an instant or sorcery card, exile it. "
                + "Then that player copies each instant or sorcery card exiled with {this}. "
                + "For each copy, the player may cast the copy without paying its mana cost";
    }

    public EyeOfTheStormEffect1(final EyeOfTheStormEffect1 effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        boolean noLongerOnStack = false;// spell was exiled already by another effect, for example NivMagus Elemental
        if (spell == null) {
            spell = ((Spell) game.getLastKnownInformation(targetPointer.getFirst(game, source), Zone.STACK));
            noLongerOnStack = true;
        }
        Permanent eyeOfTheStorm = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (spell != null && eyeOfTheStorm != null) {
            Player spellController = game.getPlayer(spell.getControllerId());
            Card card = spell.getCard();
            if (spellController == null 
                    || card == null 
                    || !instantOrSorceryfilter.match(card, game)) {
                return false;
            }
            if (!noLongerOnStack) {// the spell is still on the stack, so exile it
                UUID exileZoneId = CardUtil.getExileZoneId(game, source.getSourceId(), eyeOfTheStorm.getZoneChangeCounter(game));
                spellController.moveCardsToExile(spell, source, game, true, exileZoneId, eyeOfTheStorm.getIdName());
            }

            eyeOfTheStorm.imprint(card.getId(), game);// technically, using the imprint functionality here is not correct.

            if (eyeOfTheStorm.getImprinted() != null 
                    && !eyeOfTheStorm.getImprinted().isEmpty()) {
                CardsImpl copiedCards = new CardsImpl();
                for (UUID uuid : eyeOfTheStorm.getImprinted()) {
                    card = game.getCard(uuid);

                    // Check if owner of card is still in game
                    if (card != null 
                            && game.getPlayer(card.getOwnerId()) != null) {
                        if (card.isSplitCard()) {
                            copiedCards.add(((SplitCard) card).getLeftHalfCard());
                            copiedCards.add(((SplitCard) card).getRightHalfCard());
                        } else {
                            copiedCards.add(card);
                        }
                    }
                }

                boolean continueCasting = true;
                while (continueCasting) {
                    continueCasting = copiedCards.size() > 1 && spellController.chooseUse(outcome, "Cast one of the copied cards without paying its mana cost?", source, game);

                    Card cardToCopy;
                    if (copiedCards.size() == 1) {
                        cardToCopy = copiedCards.getCards(game).iterator().next();
                    } else {
                        TargetCard target = new TargetCard(1, Zone.EXILED, new FilterCard("card to copy"));
                        spellController.choose(Outcome.Copy, copiedCards, target, game);
                        cardToCopy = copiedCards.get(target.getFirstTarget(), game);
                        copiedCards.remove(cardToCopy);
                    }
                    if (cardToCopy != null) {
                        Card copy = game.copyCard(cardToCopy, source, source.getControllerId());
                        if (spellController.chooseUse(outcome, "Cast the copied card without paying mana cost?", source, game)) {
                            spellController.cast(copy.getSpellAbility(), game, true);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public EyeOfTheStormEffect1 copy() {
        return new EyeOfTheStormEffect1(this);
    }
}
