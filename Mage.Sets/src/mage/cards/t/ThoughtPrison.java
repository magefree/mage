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

import java.util.List;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author spjspj
 */
public class ThoughtPrison extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell cast");

    public ThoughtPrison(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // Imprint - When Thought Prison enters the battlefield, you may have target player reveal his or her hand. If you do, choose a nonland card from it and exile that card.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new ThoughtPrisonImprintEffect(), true, "<i>Imprint - </i>");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Whenever a player casts a spell that shares a color or converted mana cost with the exiled card, Thought Prison deals 2 damage to that player.
        this.addAbility(new ThoughtPrisonTriggeredAbility());
    }

    public ThoughtPrison(final ThoughtPrison card) {
        super(card);
    }

    @Override
    public ThoughtPrison copy() {
        return new ThoughtPrison(this);
    }
}

class ThoughtPrisonImprintEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public ThoughtPrisonImprintEffect() {
        super(Outcome.Benefit);
        staticText = "exile a nonland card from target player's hand";
    }

    public ThoughtPrisonImprintEffect(ThoughtPrisonImprintEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(source.getFirstTarget());

        if (player != null && targetPlayer != null) {
            targetPlayer.revealCards("Thought Prison ", targetPlayer.getHand(), game);

            TargetCard target = new TargetCard(1, Zone.HAND, filter);
            if (player.choose(Outcome.Benefit, targetPlayer.getHand(), target, game)) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = targetPlayer.getHand().get(targetId, game);
                    if (card != null) {
                        card.moveToExile(source.getSourceId(), "Thought Prison", source.getSourceId(), game);
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.imprint(card.getId(), game);
                            permanent.addInfo("imprint", new StringBuilder("[Exiled card - ").append(card.getName()).append(']').toString(), game);
                        }
                        return true;
                    }
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public ThoughtPrisonImprintEffect copy() {
        return new ThoughtPrisonImprintEffect(this);
    }
}

class ThoughtPrisonTriggeredAbility extends TriggeredAbilityImpl {

    public ThoughtPrisonTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ThoughtPrisonDamageEffect(), false);
    }

    public ThoughtPrisonTriggeredAbility(final ThoughtPrisonTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ThoughtPrisonTriggeredAbility copy() {
        return new ThoughtPrisonTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = (Spell) game.getObject(event.getTargetId());
        Permanent sourcePermanent = game.getPermanent(this.getSourceId());

        if (spell != null && spell instanceof Spell) {
            if (sourcePermanent == null) {
                sourcePermanent = (Permanent) game.getLastKnownInformation(event.getSourceId(), Zone.BATTLEFIELD);
            }
            if (sourcePermanent != null && sourcePermanent.getImprinted() != null && !sourcePermanent.getImprinted().isEmpty()) {
                Card imprintedCard = game.getCard(sourcePermanent.getImprinted().get(0));
                if (imprintedCard != null && game.getState().getZone(imprintedCard.getId()).equals(Zone.EXILED)) {
                    // Check if spell's color matches the imprinted card
                    ObjectColor spellColor = spell.getColor(game);
                    ObjectColor imprintedColor = imprintedCard.getColor(game);
                    boolean matches = false;

                    if (spellColor.shares(imprintedColor)) {
                        matches = true;
                    }
                    // Check if spell's CMC matches the imprinted card
                    int cmc = spell.getConvertedManaCost();
                    int imprintedCmc = imprintedCard.getConvertedManaCost();
                    if (cmc == imprintedCmc) {
                        matches = true;
                    }

                    if (matches) {
                        for (Effect effect : this.getEffects()) {
                            effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                        }
                        return matches;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a player casts a spell that shares a color or converted mana cost with the exiled card, " + super.getRule();
    }
}

class ThoughtPrisonDamageEffect extends OneShotEffect {

    public ThoughtPrisonDamageEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to that player";
    }

    public ThoughtPrisonDamageEffect(final ThoughtPrisonDamageEffect effect) {
        super(effect);
    }

    @Override
    public ThoughtPrisonDamageEffect copy() {
        return new ThoughtPrisonDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            targetPlayer.damage(2, source.getSourceId(), game, false, true);
            return true;
        }
        return false;
    }
}
