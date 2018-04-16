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
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SagaChapter;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.stack.Spell;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class TheMirariConjecture extends CardImpl {

    public TheMirariConjecture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        this.subtype.add(SubType.SAGA);

        // <i>(As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)</i>
        SagaAbility sagaAbility = new SagaAbility(this, SagaChapter.CHAPTER_III);
        // I — Return target instant card from your graveyard to your hand.
        FilterCard filterInstantCard = new FilterCard("instant card from your graveyard");
        filterInstantCard.add(new CardTypePredicate(CardType.INSTANT));
        Ability ability = sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filterInstantCard));
        // II — Return target sorcery card from your graveyard to your hand.
        FilterCard filterSorceryCard = new FilterCard("sorcery card from your graveyard");
        filterSorceryCard.add(new CardTypePredicate(CardType.SORCERY));
        ability = sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new ReturnFromGraveyardToHandTargetEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filterSorceryCard));
        // III — Until end of turn, whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, new CreateDelayedTriggeredAbilityEffect(new TheMirariConjectureDelayedTriggeredAbility()));
        this.addAbility(sagaAbility);

    }

    public TheMirariConjecture(final TheMirariConjecture card) {
        super(card);
    }

    @Override
    public TheMirariConjecture copy() {
        return new TheMirariConjecture(this);
    }
}

class TheMirariConjectureDelayedTriggeredAbility extends DelayedTriggeredAbility {

    public TheMirariConjectureDelayedTriggeredAbility() {
        super(new CopyTargetSpellEffect(), Duration.EndOfTurn, false);
    }

    public TheMirariConjectureDelayedTriggeredAbility(final TheMirariConjectureDelayedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TheMirariConjectureDelayedTriggeredAbility copy() {
        return new TheMirariConjectureDelayedTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null
                    && (spell.isInstant() || spell.isSorcery())) {
                this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Until end of turn, whenever you cast an instant or sorcery spell, copy it. You may choose new targets for the copy.";
    }
}
