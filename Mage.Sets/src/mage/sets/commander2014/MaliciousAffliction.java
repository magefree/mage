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
package mage.sets.commander2014;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.condition.LockedInCondition;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class MaliciousAffliction extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creatures");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public MaliciousAffliction(UUID ownerId) {
        super(ownerId, 25, "Malicious Affliction", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{B}{B}");
        this.expansionSetCode = "C14";


        // Morbid - When you cast Malicious Affliction, if a creature died this turn, you may copy Malicious Affliction and may choose a new target for the copy.
        Ability ability = new ConditionalTriggeredAbility(
                new CastSourceTriggeredAbility(new CopySourceSpellEffect(), true),
                new LockedInCondition(MorbidCondition.getInstance()),
                "<i>Morbid</i> - When you cast {this}, if a creature died this turn, you may copy {this} and may choose a new target for the copy");
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Destroy target nonblack creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public MaliciousAffliction(final MaliciousAffliction card) {
        super(card);
    }

    @Override
    public MaliciousAffliction copy() {
        return new MaliciousAffliction(this);
    }
}

class CopySourceSpellEffect extends OneShotEffect {

    final String rule = "copy {this} and may choose a new target for the copy";

    public CopySourceSpellEffect() {
        super(Outcome.Benefit);
        staticText = rule;
    }

    public CopySourceSpellEffect(final CopySourceSpellEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Spell spell = game.getStack().getSpell(source.getSourceId());
            if (spell != null) {
                Spell spellCopy = spell.copySpell();
                spellCopy.setCopiedSpell(true);
                spellCopy.setControllerId(source.getControllerId());
                game.getStack().push(spellCopy);
                spellCopy.chooseNewTargets(game, controller.getId());
                String activateMessage = spellCopy.getActivatedMessage(game);
                if (activateMessage.startsWith(" casts ")) {
                    activateMessage = activateMessage.substring(6);
                }
                game.informPlayers(controller.getLogName() + " copies " + activateMessage);
                return true;
            }
        }
        return false;
    }

    @Override
    public CopySourceSpellEffect copy() {
        return new CopySourceSpellEffect(this);
    }
}